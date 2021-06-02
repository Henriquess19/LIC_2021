import isel.leic.utils.Time
import kotlin.math.pow

object KeyReceiver {

    private const val TXclk = 0x40
    private const val TXd = 0x40
    private const val NumbIterations = 6
    private val KeyIterations = (1..4)

    /**
        TXclk -> Output 6
        TXd-> Input 6
     **/

    fun init(){
        HAL.clrBits(TXclk)
    }

    fun rcv():Int {
        var count = 0
        var s = -1.0
        if(!HAL.isBit(TXd)) {
            s=0.0
            while (count <= NumbIterations) {
                HAL.setBits(TXclk)
                Time.sleep(5)
                HAL.clrBits(TXclk)
                Time.sleep(5)
                val x = HAL.readBits(TXd)
                if (count in KeyIterations) {
                    if (x > 0) s += ((2.0).pow(count -1))   /* Recreation on just one number of the key value */
                }
                count++
            }
        }
        return s.toInt()               /* If s = -1 the higher code will understand like incorrect value */
    }
}

fun main(){
    KeyReceiver.init()
    while (true){
        println(KeyReceiver.rcv())
        Time.sleep(250)
    }
}

