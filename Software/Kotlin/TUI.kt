import kotlin.math.pow

object TUI {
    fun key(l:Int, vis :Boolean):Int{
        var s = 0.0
        var i = 0


        do {
            val x = KBD.waitKey(5000)
            if ((s == 0.0 &&  x == '*')|| x == KBD.NONE.toChar()) return -1
            if (x == '*') {
                LCD.clear()
            }else{
                if (!vis){
                    LCD.write('*')
                } else {
                    LCD.write(x)
                }

                s += (x-'0')* ((10.0).pow(l-i-1))
            }
            i++
        }while (i<l)

        return s.toInt()
    }
}

fun main(){
    LCD.init()
    println(TUI.key(4,false))
    /*println(TUI.key(4,true))*/
}
