import isel.leic.utils.Time
import kotlin.math.pow

object KeyReceiver {
    /**
        TXclk -> Output 6

        TXd -> Input 6
     **/
    fun init(){
        HAL.clrBits(0x40)
    }

    fun rcv():Int{
        var count =0
        var s=0.0
        while (count <=5){
            HAL.setBits(0x40)
            Time.sleep(5)
            HAL.clrBits(0x40)
            Time.sleep(5)
            val x = HAL.readBits(0x40)
            if (count in 1..4){
                if (x > 0)  s +=((10.0).pow(count-1))
            }
            count++
        }
        return s.toInt()
    }
}
