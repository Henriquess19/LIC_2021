import isel.leic.utils.Time

object LCD { // Escreve no LCD usando a interface a 4 bits.

    private const val LINES = 2
    private const val COLS = 16 // Dimensão do display.



    private fun writeNibble(rs: Boolean, data: Int) {

        //  RS -> i4
        if (rs){
            HAL.setBits(0x10)
        }else{
            HAL.clrBits(0x10)
        }

        //  EnableOn -> i5
        HAL.setBits(0x20)

        //Data
        HAL.writeBits(0x0F,data) //00000011

        //  EnableOff -> i5
        HAL.clrBits(0x20)
    }


    private fun writeByte(rs: Boolean, data: Int) {
        writeNibble(rs,data/16) // /16 == ShiftRight 4x
        writeNibble(rs,data)
    }

    private fun writeCMD(data: Int) {
        writeByte(false,data)
    }

    private fun writeDATA(data: Int) {
        writeByte(true,data)
    }


    fun init() {

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
        writeCMD(0x28) // N=1 e F= 0
        writeCMD(0x08)
        writeCMD(0x01)
        writeCMD(0x07) // I/D=1 e S=1
        writeCMD(0x0F)
    }

    // Escreve um caráter na posição corrente.
    fun write(c: Char) {
        writeDATA(c.toInt())
    }
    // Escreve uma string na posição corrente.
    fun write(text: String) {
        text.forEach { write(it) }
    }

    fun cursor(line: Int, column: Int) {
        val x = column + (line*40)
        writeCMD(x+128)
    }

    fun clear() {
        writeCMD(0x01)
    }
}

fun main(){
    LCD.init()
    while (true) {
        val y = KBD.waitKey(200)
        if (y != KBD.NONE.toChar())LCD.write(y)

    }

    /*while (true){
        val x = readLine()!!
        LCD.clear()
        LCD.write(x)
    }*/
}