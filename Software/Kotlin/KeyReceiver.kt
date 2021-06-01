import isel.leic.utils.Time
import kotlin.math.pow

object KeyReceiver {
    /**
        TXclk -> Output 6
        TXd
        -> Input 6
     **/
    fun init(){
        HAL.clrBits(0x40)
    }

    fun rcv():Int {
        var count = 0
        var s = -1.0
        if(!HAL.isBit(0x40)) {
            s=0.0
            while (count <= 6) {
                HAL.setBits(0x40)
                Time.sleep(50)
                HAL.clrBits(0x40)
                Time.sleep(50)
                val x = HAL.readBits(0x40)
                if (count in 1..4) {
                    if (x > 0) s += ((2.0).pow(count -1))
                }
                count++
            }
        }
        return s.toInt()
    }
}

fun main(){
    KeyReceiver.init()
    while (true){
        println(KeyReceiver.rcv())
        Time.sleep(250)
    }
}
