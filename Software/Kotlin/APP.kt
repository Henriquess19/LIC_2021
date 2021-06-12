import FileAcess.getUser
import isel.leic.utils.Time

data class Ut(val user:Int ,val pass:Int,val name:String, var acumulateTime:Int, var jOTA :Long,var entryTime:Long)

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
                return doorMovement(user)
            }
            lineClear(writeLine)
            TUI.writeleft("PASS ERROR", writeLine)
            Time.sleep(1000)
            lineClear(writeLine)
        }
        return restart(tentNumb, writeLine)
    }

    fun restart(tentNumb:Int, writeLine: Int){
        return pass(tentNumb, writeLine)
    }

    fun doorMovement(worker:Ut){
            LCD.clear()
            TUI.writecenter("Welcome", 0)
            TUI.writecenter(worker.name, 1)
            Door.open(DOOR_OPEN_VELOCITY)
            Time.sleep(3000)
            Door.close(DOOR_CLOSE_VELOCITY)
        }

    fun lineClear(x:Int){                           /* Function to prevent clear all the screen but just one line*/
        TUI.writeleft("                ",x)
    }

    fun appPlay (tentNumb:Int, writeLine: Int){
        TUI.writeright(TUI.time(),0)
        pass(tentNumb,writeLine)
    }
}

fun main() {
    HAL.init()
    KBD.init()
    LCD.init()


    APP.appPlay(3,1)
}
