import FileAcess.getUser
import isel.leic.utils.Time
import java.util.concurrent.TimeUnit

data class Ut(val user:Int ,val pass:Int,val name:String, var acumulateTime:Long, var entryTime:Long)

object APP {
    private const val DOOR_OPEN_VELOCITY= 11
    private const val DOOR_CLOSE_VELOCITY= 11

    fun user(writeLine:Int):Ut {
        TUI.writeleft("USER:", writeLine)
        val userNumb = TUI.key(3, true)
        var user:Ut? = null
        if (getUser(userNumb)!= null) {
                 user = getUser(userNumb)!!
        }
        else {
            lineClear(writeLine)
            TUI.writeleft("USER NOT FOUND", writeLine)
            Time.sleep(1000)
            lineClear(writeLine)
            user(writeLine)
        }
        return user!!
    }

    fun pass(tentNumb:Int, writeLine: Int){
        val user = user(writeLine)
        lineClear(writeLine)
        for (i in 1..tentNumb) {
            TUI.writeleft("PASS:", writeLine)
            val code = TUI.key(4, false)
            if (code == user.pass) {
                return doorAction(user)
            }
            lineClear(writeLine)
            TUI.writeleft("PASS ERROR", writeLine)
            Time.sleep(1000)
            lineClear(writeLine)
        }
        return wrongPass(tentNumb, writeLine)
    }

    fun wrongPass(tentNumb:Int, writeLine: Int){
        return pass(tentNumb, writeLine)
    }

    fun doorAction(worker:Ut){
            LCD.clear()
            if(worker.entryTime > 0L) awayDoor(worker)
            else entryDoor(worker)
    }

    fun moveDoor(){
        Door.open(DOOR_OPEN_VELOCITY)
        Time.sleep(3000)
        //Door.close(DOOR_CLOSE_VELOCITY)
    }

    fun entryDoor(worker:Ut){
        worker.entryTime = Time.getTimeInMillis()
        TUI.writecenter("Welcome", 0)
        TUI.writecenter(worker.name, 1)
        LogFile.entryUser(worker,worker.entryTime)
        /**Update User**/
        moveDoor()
        resetUserTime(worker)
    }

    fun awayDoor(worker: Ut){
        val awayTime = Time.getTimeInMillis()
        worker.acumulateTime = awayTime - worker.entryTime
        /*TUI.writeleft(millisToHours(worker.entryTime),0)
        TUI.writeright(millisToHours(awayTime),0)*/
        TUI.writecenter(worker.name,0)
        TUI.writecenter(millisToHours(worker.acumulateTime),1)
        LogFile.awayUser(worker,worker.entryTime)
        /**Update User**/
        moveDoor()
        resetUserTime(worker)
    }

    fun millisToHours(millis:Long):String{
        var time = millis
        val hour = (time/(60*60*1000))%24
        time -= hour * (60 * 60 * 1000)
        val minutes = (time/(60*1000))%60
        time -= minutes*(60*1000)
        val seconds = (time/1000)%60
        return ("${hour}h:${minutes}m:${seconds}s")
    }

    fun resetUserTime(worker: Ut){
        worker.entryTime=0L
    }

    fun lineClear(x:Int){                           /* Function to prevent clear all the screen but just one line*/
        TUI.writeleft("                ",x)
    }

    fun restart(tentNumb:Int, writeLine: Int){
        Time.sleep(5000)
        LCD.clear()
        Time.sleep(5000)
        appPlay(tentNumb, writeLine)
    }

    fun appPlay (tentNumb:Int, writeLine: Int){
        TUI.writeright(TUI.time(),0)
        pass(tentNumb,writeLine)
        restart(tentNumb,writeLine)
    }
}

fun main() {
    HAL.init()
    KBD.init()
    LCD.init()


    APP.appPlay(3,1)
}
