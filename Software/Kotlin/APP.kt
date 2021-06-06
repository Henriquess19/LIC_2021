import isel.leic.utils.Time

data class Ut(val user:Int ,val pass:Int,val name:String )


object APP {
    private const val DOOR_OPEN_VELOCITY= 12
    private const val DOOR_CLOSE_VELOCITY= 1

    fun user(worker:Ut, tent:Int?, numeroTentativas:Int, line:Int):Boolean {
        var tries = tent
        for(i in 1..numeroTentativas){
            TUI.writeleft("USER:", line)
            tries = TUI.key(3, true)
            if (tries==worker.user) {
                return true
            }
        lineClear(line)
        TUI.writeleft("USER NOT FOUND", line)
        Time.sleep(1000)
        lineClear(line)
    }
        return false
    }
    fun pass(worker:Ut, tent:Int?, numeroTentativas:Int, line:Int):Boolean {
        var tries = tent
        lineClear(line)
        for (i in 1..numeroTentativas) {
            TUI.writeleft("PASS:", line)
            tries = TUI.key(4, false)
            if (tries == worker.pass) {
                return true
            }
            lineClear(line)
            TUI.writeleft("PASS ERROR", line)
            Time.sleep(1000)
            lineClear(line)
        }
        return false
    }
    fun doorMovement( worker:Ut){
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
}

fun main() {
    HAL.init()
    KBD.init()
    LCD.init()
    TUI.writeleft(TUI.time(), 0)

    val user: Int? = null
    val pass: Int? = null
    val client = Ut(0,0,"Marcelo")

        val condA=APP.user(client,user, 1, 1)
        val condB = APP.pass(client, pass, 3, 1)
        if (condA&&condB) {
                APP.doorMovement( client)
        }
}
