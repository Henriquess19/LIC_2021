import isel.leic.utils.Time

object KBD {
    const val NONE = 0
    private const val ACK_MASK = 0x80
    private const val DVAL_MASK = 0x10 //0x80 -> 0x10 is for simulation purposes
    private const val KEY_VALUE = 0x0F
    private const val SERIAL_INTERFACE = false
    private val KEYBOARD= charArrayOf('1', '4', '7','*','2','5','8','0','3','6','9','#')

    fun init() {
        HAL.clrBits(ACK_MASK)
    }
    fun getKey():Char{
        if(SERIAL_INTERFACE) return getKeySerial()
        else return getKeyParallel()
    }

    private fun getKeySerial():Char{
        val x =  KeyReceiver.rcv()
        return KEYBOARD[x]
    }

    private fun getKeyParallel():Char {
        var x:Char = NONE.toChar()
        if (HAL.isBit(DVAL_MASK)) {
            x=KEYBOARD[HAL.readBits(KEY_VALUE)]
            HAL.setBits(ACK_MASK)
            while (HAL.isBit(DVAL_MASK)){} /*Waiting for Dval to be 0*/
            HAL.clrBits(ACK_MASK)
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
    HAL.init()
    KBD.init()
    while (true) {
        print(KBD.waitKey(50))
        Time.sleep(50)
    }
}
