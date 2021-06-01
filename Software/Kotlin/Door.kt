

object Door{
    const val WrMask = 0x40
    //const val WrMask = 0x40
    fun init(){
        HAL.clrBits(WrMask)      

    }

/**
 * V0~3 -> 0x0F
 * OpenClose -> 0x10
 */

    fun open(speed:Int){
        var spd=speed
        if (speed > 15) spd=15

        val x = 0x10 + spd
        HAL.writeBits(0x1F,x)  /*Dout Mask*/
        HAL.setBits(WrMask)           /*Wr Mask*/

        while (!HAL.isBit(0x20)/*Busy Mask*/){}
        HAL.clrBits(WrMask)
    }

    fun close(speed: Int){
        var spd=speed
        if (speed > 15) spd=15

        val x = 0x00 + spd
        HAL.writeBits(0x1F,x)   /*Dout Mask*/
        HAL.setBits(WrMask)            /*Wr Mask*/

        while (!HAL.isBit(0x20)/*Busy Mask*/){} /*W8ing for the busy signal*/
        HAL.clrBits(WrMask)
    }


    fun isFinished():Boolean{
        while (HAL.isBit(0x20)/*Busy Mask*/) {
            return false
        }
        return true
    }
}


fun main(){
    Door.init()

    while (true) {
        val x = (-100..100).random()
        println(x)
        if (x <= 0) {
            Door.close(12)
        } else {
            Door.open(12)
        }
        while (!Door.isFinished()){}
    }
}
