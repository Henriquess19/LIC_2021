import isel.leic.utils.Time
import kotlin.math.pow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object TUI {
    const val LCD_COLUMNS = 16

    fun key(l:Int, vis :Boolean):Int{
        var s = 0.0
        var i =0

        do {
            val x = KBD.waitKey(5000)
            if ((s == 0.0 && x == '*') || x == KBD.NONE.toChar()) return -1
            if (x == '*') {
                LCD.clear()
                i=0
            } else {
                if (!vis) {
                    LCD.write('*')
                } else {
                    LCD.write(x)
                }

                s += (x - '0') * ((10.0).pow(l - i - 1))
                i++
            }
        }while (i<l)

        return s.toInt()
    }

    fun writeleft(s:String,line:Int){
        LCD.cursor(line,0)
        LCD.write(s)
    }

    fun writecenter(s:String,line:Int){
        var size = s.length
        size = (LCD_COLUMNS-s.length*1.5-1).toInt()
        LCD.cursor(line,size)
        LCD.write(s)
    }

    fun writeright(s:String,line:Int){
        var size = s.length
        size = LCD_COLUMNS-s.length
        LCD.cursor(line,size)
        LCD.write(s)
    }

    fun time():String{
        val time = LocalDateTime.now()
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return time.format(format)
    }


}
fun main(){

    HAL.init()
    KBD.init()
    LCD.init()

    /*TUI.key(4,false)*/
    /*println(TUI.key(4,true))*/

    /*TUI.writecenter("Ricardo",0)*/
    /*TUI.writeright("Ricardo")*/

    var user: Int? = null
    var pass: Int? = null

    val client:Ut = Ut(0,0,"Almeida")


    while (user != client.user) {
        TUI.writeleft(TUI.time(), 0)
        TUI.writeleft("USER:", 1)
        user = TUI.key(3, true)
        if (user == -1 || user != client.user.toInt()) {
            LCD.clear()
            TUI.writeleft(TUI.time(), 0)
            TUI.writeleft("USER NOT FOUND", 1)
            Time.sleep(1000)
            LCD.clear()
        }
    }

    LCD.clear()

    while (pass != client.pass) {
        TUI.writeleft(TUI.time(), 0)
        TUI.writeleft("PASS:", 1)
        pass = TUI.key(4, false)
        if (pass == -1 || pass != client.pass.toInt() ) {
            LCD.clear()
            TUI.writeleft(TUI.time(), 0)
            TUI.writeleft("PASS ERROR", 1)
            Time.sleep(1000)
            LCD.clear()
        }
    }

    LCD.clear()
    TUI.writecenter("WELCOME",0)
    TUI.writecenter(client.name,1)

    Door.open(14)
    Time.sleep(3000)
    Door.close(10)

    //TUI.writeleft("PASS:" + TUI.key(4,false),1)
}
