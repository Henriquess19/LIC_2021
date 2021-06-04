import isel.leic.utils.Time

object KBD {
    const val NONE = 0
    private const val ACKMask = 0x80
    private const val DValMask = 0x10 //0x80 -> 0x10 is for simulation purposes
    private const val KeyValue = 0x0F
    private const val SERIAL_INTERFACE = false
    private val KEYBOARD= charArrayOf('1', '4', '7','*','2','5','8','0','3','6','9','#')

    fun init() {
        HAL.clrBits(ACKMask)
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
        if (HAL.isBit(DValMask)) {
            x=KEYBOARD[HAL.readBits(KeyValue)]
            HAL.setBits(ACKMask)
            while (HAL.isBit(DValMask)){} /*Waiting for Dval to be 0*/
            HAL.clrBits(ACKMask)
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
