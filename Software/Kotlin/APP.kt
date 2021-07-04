import Users.getUser
import com.sun.tools.javac.Main
import isel.leic.utils.Time

object APP {
    private const val DOOR_OPEN_VELOCITY= 11
    private const val DOOR_CLOSE_VELOCITY= 11
    private const val MMASK = 0x80
    private const val TENTNUMB = 3
    private const val WRITEACTIONSLINE = 1


    private fun user():Ut {
        TUI.writeleft("USER:", WRITEACTIONSLINE)
        while (HAL.isBit(KeyReceiver.TXD)){
            if (HAL.readBits(MMASK) != 0) Maintenance.init()
        }
        val userNumb= TUI.key(3, true)
        return if (getUser(userNumb)!= null) {
            getUser(userNumb)!!
        } else {
            TUI.lineClear(WRITEACTIONSLINE)
            TUI.writeleft("USER NOT FOUND", WRITEACTIONSLINE)
            Time.sleep(1000)
            TUI.lineClear(WRITEACTIONSLINE)
            user()
        }
    }

    private fun pass(){
        val user = user()
        TUI.lineClear(WRITEACTIONSLINE)
        for (i in 1..TENTNUMB) {
            TUI.writeleft("PASS:", WRITEACTIONSLINE)
            val code = TUI.key(4, false)
            if (code == user.pass) {
                verifyChangePass(user)
                return doorAction(user)
            }
            TUI.lineClear(WRITEACTIONSLINE)
            TUI.writeleft("PASS ERROR", WRITEACTIONSLINE)
            Time.sleep(1000)
            TUI.lineClear(WRITEACTIONSLINE)
        }
        return wrongPass()
    }

    private fun verifyChangePass(user:Ut) {
        if (KBD.waitKey(2000) == '#') changePass(user)
    }

    private fun changePass(user:Ut){
        LCD.clear()
        TUI.writecenter("NEW PIN",0)
        TUI.writeleft("PIN:",1)
        val code1 = TUI.key(4,false)
        if (code1 == -1) appPlay()

        LCD.clear()
        TUI.writecenter("CONFIRM PIN",0)
        TUI.writeleft("PIN:",1)
        val code2 = TUI.key(4,false)
        if (code2 == -1) appPlay()

        LCD.clear()
        if (code1==code2){
            user.pass=code1
            TUI.writecenter("PIN CONFIRMED",0)
            Time.sleep(1500)
            return
        }
        TUI.writecenter("PIN ERROR",0)
        changePass(user)
        return
    }

    private fun wrongPass(){
        return pass()
    }

    private fun doorAction(worker:Ut){
            LCD.clear()
            if(worker.entryTime > 0L) awayDoor(worker)
            else entryDoor(worker)
    }

    private fun moveDoor(){
        Door.open(DOOR_OPEN_VELOCITY)
        Time.sleep(3000)
        Door.close(DOOR_CLOSE_VELOCITY)
        while (!Door.isFinished()){}
    }

    private fun entryDoor(worker:Ut){
        val entryTime = Time.getTimeInMillis()

        TUI.writecenter("Welcome", 0)
        TUI.writecenter(worker.name, 1)

        moveDoor()

        LogFile.entryUser(worker,entryTime)
        Users.updateUser(worker.user,worker.acumulateTime,entryTime)
    }

    private fun awayDoor(worker: Ut){
        val awayTime = Time.getTimeInMillis()
        val acumulateTime = awayTime - worker.entryTime

        TUI.writeleft(LogFile.calendarAway(worker.entryTime),0)
        TUI.writeleft(LogFile.calendarAway(awayTime),1)
        TUI.writeright(millisToHours(acumulateTime),1)

        Time.sleep(3000)
        LCD.clear()
        TUI.writecenter("Good-Bye",0)
        TUI.writecenter(worker.name,1)
        moveDoor()

        LogFile.awayUser(worker,awayTime)
        Users.updateUser(worker.user,acumulateTime,0L)
    }

    private fun millisToHours(millis:Long):String{
        var time = millis
        val hour = (time/(60*60*1000))
        time -= hour * (60 * 60 * 1000)
        val minutes = (time/(60*1000))
        time -= minutes*(60*1000)
        return ("${hour}:${minutes}")
    }

    private fun restart(){
        Time.sleep(1500)
        LCD.clear()
        mode()
    }

    private fun appPlay (){
        LCD.clear()
        TUI.writeright(TUI.time(),0)
        pass()
        restart()
    }

    fun mode(){
        if (HAL.readBits(MMASK) != 0) Maintenance.init()
        else appPlay()
    }
}

fun main() {
    HAL.init()
    KBD.init()
    LCD.init()
    Users.init()

    APP.mode()
}
