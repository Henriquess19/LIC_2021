import isel.leic.utils.Time

object LCD { 

    private const val LINES = 2
    private const val COLS = 16 // Display dimension
    const val Enable = 0x20
    const val RS = 0x10
    const val LCDData = 0x0F
    const val LCDLine = 0x40   //If wanted to write at the second line just need to add 0x40
    const val DisplayClear = 0x01
    const val CursorCMD = 0x80
    
    private fun writeNibble(rs: Boolean, data: Int) {
        //  RS -> UsbPort.i4
        if (rs){
            HAL.setBits(RS)
        }else{
            HAL.clrBits(RS)
        }

        //  EnableOn -> i5
        HAL.setBits(Enable)

        //Data
        HAL.writeBits(LCDData,data) 

        //  EnableOff -> i5
        HAL.clrBits(Enable)
        Time.sleep(2)
    }


    private fun writeByte(rs: Boolean, data: Int) {
        writeNibble(rs,data/16) // /16 == ShiftRight 4 times
        Time.sleep(2)
        writeNibble(rs,data)
    }

    private fun writeCMD(data: Int) {
        writeByte(false,data)
    }

    private fun writeDATA(data: Int) {
        writeByte(true,data)
    }


    fun init() {

        /**
         * All the "fly" variables, like 5 or 0x08.. It's for the LCD configuration
         * They're times and commands got in the manual
         */

        val temp1 = Time.getTimeInMillis() + 80
        while (Time.getTimeInMillis() <= temp1){}

        writeNibble(false,0x03)

        val temp2 = Time.getTimeInMillis() + 5
        while (Time.getTimeInMillis() <= temp2){}

        writeNibble(false,0x03)

        val temp3 = Time.getTimeInMillis() + 1
        while (Time.getTimeInMillis() <= temp3){}

        writeNibble(false,0x03)

        writeNibble(false,0x02)
        writeCMD(0x28) // N=1 & F= 0
        writeCMD(0x08)
        writeCMD(0x01)
        writeCMD(0x06) // I/D=1 & S=0
        writeCMD(0x0F)
    }

    // Char write at the position.
    fun write(c: Char) {
        writeDATA(c.toInt())
    }
    // String write at the position.
    fun write(text: String) {
        text.forEach { write(it) }
    }

    fun cursor(line: Int, column: Int) {
        val x = column + (line* LCDLine)
        writeCMD(x+ CursorCMD)
    }

    fun clear() {
        writeCMD(DisplayClear)
    }
}

fun main(){
    LCD.init()
    val tui = TUI.key(4,false)
    println(tui)

    /*while (true) {
        val y = KBD.waitKey(500)
        if (y != KBD.NONE.toChar() && y != '*')LCD.write(y)
        if (y == '*')LCD.clear()
    }*/

    /*while (true){
        val x = readLine()!!
        LCD.clear()
        LCD.write(x)
    }*/

    //LCD.write("Hey")
}
