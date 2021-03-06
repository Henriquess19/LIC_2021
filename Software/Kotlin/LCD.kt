import isel.leic.utils.Time

object LCD {

    private const val LINES = 2
    private const val COLS = 16 // Display dimension
    private const val ENABLE = 0x20
    private const val RS = 0x10
    private const val LCD_DATA = 0x0F
    private const val LCD_LINE = 0x40   //If wanted to write at the second line just need to add 0x40
    private const val DISPLAY_CLEAR = 0x01
    private const val CURSOR_CMD = 0x80
    private const val DISPLAY_OFF = 0x08 //D -> 0  C&B -> Dont Care

    private fun writeNibble(rs: Boolean, data: Int) {
        //  RS -> UsbPort.i4
        if (rs){
            HAL.setBits(RS)
        }else{
            HAL.clrBits(RS)
        }

        //  EnableOn -> i5
        HAL.setBits(ENABLE)

        //Data
        HAL.writeBits(LCD_DATA,data)

        //  EnableOff -> i5
        HAL.clrBits(ENABLE)
        Time.sleep(2)
    }


    private fun writeByte(rs: Boolean, data: Int) {
        writeNibble(rs,data.shr(4)) // /16 == ShiftRight 4 times
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
        Time.sleep(80)

        writeNibble(false,0x03)

        Time.sleep(5)

        writeNibble(false,0x03)

        Time.sleep(1)

        writeNibble(false,0x03)

        writeNibble(false,0x02)
        //N foi 1 porque quer-se escrever em duas linhas, o de F = 0  para se obter o tamanho de 5 por 8 em cada caracter
        writeCMD(0x28)
        writeCMD(0x08)
        writeCMD(0x01)
        //de I/D =1 porque quer-se que o cursor incremente ao escrever e o de S=0 para não haver um Shift no display logo na sua inicialização
        writeCMD(0x06)
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
        val x = column + (line* LCD_LINE)
        writeCMD(x+ CURSOR_CMD)
    }

    fun clear() {
        writeCMD(DISPLAY_CLEAR)
    }

    fun off(){
        writeCMD(DISPLAY_OFF)
    }
}

fun main(){
    LCD.init()
    while (true) {
        val y = KBD.waitKey(500)
        if (y != KBD.NONE.toChar() && y != '*')LCD.write(y)
        if (y == '*')LCD.clear()
    }
    while (true){
        val x = readLine()!!
        LCD.clear()
        LCD.write(x)
    }
}
