object Door{
    private const val OPEN = 0x10
    private const val CLOSE = 0x00
    private const val WR_MASK = 0x40
    private const val BUSY_MASK = 0x20
    private const val D_OUT_MASK = 0x1F
    private const val MAX_SPEED = 15

    fun init(){
        HAL.clrBits(WR_MASK)
    }
    /**
     * V0~3 -> 0x0F
     *
     * OpenClose ->  OPEN = 0x10 & CLOSE = 0x00
     */
    fun open(speed:Int){
        var spd=speed
        if (spd > MAX_SPEED) spd=MAX_SPEED

        val x = OPEN + spd              /*Open action + speed*/
        HAL.writeBits(D_OUT_MASK,x)
        HAL.setBits(WR_MASK)

        while (!HAL.isBit(BUSY_MASK)){} /*Waiting for the busy signal*/
        HAL.clrBits(WR_MASK)
    }

    fun close(speed: Int){
        var spd=speed
        if (spd > MAX_SPEED) spd=MAX_SPEED

        val x = CLOSE + spd             /*Close action + speed*/
        HAL.writeBits(D_OUT_MASK,x)
        HAL.setBits(WR_MASK)

        while (!HAL.isBit(BUSY_MASK)){} /*Waiting for the busy signal*/
        HAL.clrBits(WR_MASK)
    }


    fun isFinished():Boolean{
        while (HAL.isBit(BUSY_MASK)) {
            return false
        }
        return true
    }
}


fun main(){
    Door.init()

    while (true) {
        val x = (-100..100).random()        /* TestCode to give random number so door open or close with the value of x*/
        println(x)
        if (x <= 0) {
            Door.close(12)
        } else {
            Door.open(12)
        }
        while (!Door.isFinished()){}
    }
}
