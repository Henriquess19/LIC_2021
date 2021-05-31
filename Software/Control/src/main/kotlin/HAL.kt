import isel.leic.UsbPort
import isel.leic.utils.Time

object HAL {
    var out = 0
    fun init() {
        UsbPort.out(out.inv())
    }

    fun isBit(mask: Int): Boolean {
       return readBits(mask) > 0
    }

    fun readBits(mask: Int):Int {
        val x = UsbPort.`in`().inv()
        return x.and(mask)
    }

    fun writeBits(mask: Int, value: Int) {
        out = mask.inv().and(out)
        out = value.or(out)
        UsbPort.out(out.inv())
    }

    fun setBits(mask: Int){
        out = mask.or(out)
        UsbPort.out(out.inv())
    }

    fun clrBits(mask:Int){
        out = mask.inv().and(out)
        UsbPort.out(out.inv())
    }
}

fun main(){
    HAL.init()
    while (true){
        HAL.setBits(0xFF)
        //HAL.clrBits(0X01)
        HAL.writeBits(0x3C,0X04)
        //HAL.isBit(0X80)
        Time.sleep(1000)
    }
}
