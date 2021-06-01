object Door{
    const val WrMask = 0x40
    const val BusyMask = 0x20
    const val DOutMask = 0x1F
    const val MaxSpeed = 0x0F
    
    fun init(){
        HAL.clrBits(WrMask)
    }

/**
 * V0~3 -> 0x0F
 * 
 * OpenClose ->  OPEN = 0x10 & CLOSE = 0x00
 */

    fun open(speed:Int){
        var spd=speed
        if (spd > MaxSpeed) spd=MaxSpeed

        val x = 0x10 + spd              /*Open action + speed*/
        HAL.writeBits(DOutMask,x)  
        HAL.setBits(WrMask)           

        while (!HAL.isBit(BusyMask)){} /*Waiting for the busy signal*/
        HAL.clrBits(WrMask)
    }

    fun close(speed: Int){
        var spd=speed
        if (spd > MaxSpeed) spd=MaxSpeed

        val x = 0x00 + spd             /*Close action + speed*/
        HAL.writeBits(DOutMask,x)   
        HAL.setBits(WrMask)            

        while (!HAL.isBit(BusyMask)){} /*Waiting for the busy signal*/
        HAL.clrBits(WrMask)
    }


    fun isFinished():Boolean{
        while (HAL.isBit(BusyMask)) {
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
