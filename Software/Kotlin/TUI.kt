import isel.leic.utils.Time
import kotlin.math.pow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object TUI {
    private const val LCD_COLUMNS = 16

    fun key(l:Int, vis :Boolean):Int{
        var s = -1.0
        var i =0

        do {
            val x = KBD.waitKey(5000)
            if ((s == -1.0 && x == '*') || x == KBD.NONE.toChar()) return -1
            if (x == '*') {
                lineClear(1)
                if (l==4) writeleft("PIN:",1)       /* If l=4 -> Pin, if l=3 -> User */
                else writeleft("USER:",1)
                s=-1.0
                i=0
            } else {

                if (i ==0)s+=1.0
                if(x in ('0'..'9')) {
                    s += (x - '0') * ((10.0).pow(l - i - 1)) //
                    i++
                    if (!vis) {
                        LCD.write('*')
                    } else {
                        LCD.write(x)
                    }
                }
            }
        }while (i<l)

        return s.toInt()
    }


    fun writeleft(s:String,line:Int){
        LCD.cursor(line,0)
        LCD.write(s)
    }

    fun writecenter(s:String,line:Int){
        val size = (LCD_COLUMNS-s.length)/2.toInt()
        LCD.cursor(line,size)
        LCD.write(s)
    }

    fun writeright(s:String,line:Int){
        val size = LCD_COLUMNS-s.length
        LCD.cursor(line,size)
        LCD.write(s)
    }

    fun time():String{
        val time = LocalDateTime.now()
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return time.format(format)
    }
     fun lineClear(line:Int){                           /* Function to prevent clear all the screen but just one line*/
        writeleft("                ",line)
    }


}
fun main(){

    HAL.init()
    KBD.init()
    LCD.init()

    TUI.key(4,false)
    TUI.key(4,true)

    TUI.writecenter("Ricardo",0)
    TUI.writeright("Ricardo",1)
    TUI.writeleft("PASS:" + TUI.key(4,false),1)
}
