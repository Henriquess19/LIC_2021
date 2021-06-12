import isel.leic.utils.Time
import kotlin.math.pow

object KeyReceiver {

    private const val TX_CLK = 0x80
    private const val TXD = 0x40
    private const val NUMB_ITERATION = 6
    private val KEY_ITERATION = (1..4)

    /**
    TXclk -> Output 6
    TXd-> Input 6
     **/

    fun init(){
        HAL.clrBits(TX_CLK)
    }

    fun rcv():Int {
        var count = 0
        var s = -1.0
        if(!HAL.isBit(TXD)) {
            s=0.0
            while (count <= NUMB_ITERATION) {
                HAL.setBits(TX_CLK)
                Time.sleep(5)
                HAL.clrBits(TX_CLK)
                Time.sleep(5)
                val x = HAL.readBits(TXD)
                if (count in KEY_ITERATION) {
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
