import isel.leic.utils.Time


object KBD {
    const val NONE = 0

    fun init() {
        HAL.clrBits(0x80)
    }

    fun getKey(): Char {
        var x:Char = NONE.toChar()
        if (HAL.isBit(0x80)) {
              x = when (HAL.readBits(0x0F)) {
                0x00 -> '1'
                0x01 -> '4'
                0x02 -> '7'
                0x03 -> '*'
                0x04 -> '2'
                0x05 -> '5'
                0x06 -> '8'
                0x07 -> '0'
                0x08 -> '3'
                0X09 -> '6'
                0X0A -> '9'
                0X0B -> '#'
                else -> NONE.toChar()
            }
            HAL.setBits(0x80)
            while (HAL.isBit(0x80)){}

            HAL.clrBits(0x80)
            return x
        }
        return x
    }

    fun waitKey(timeout: Long): Char {
        val temp = Time.getTimeInMillis() + timeout
        do {
            val x = getKey()
            if (x != NONE.toChar())
                return x
        } while (Time.getTimeInMillis() <= temp)

        return NONE.toChar()
    }
}

fun main(){
    KBD.init()
    while (true) {
        print(KBD.waitKey(500))
        Time.sleep(50)
    }
}