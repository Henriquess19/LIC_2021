import java.io.*
import java.util.*
import kotlin.math.min

object LogFile {

    /** Estamos com 1 mês de atraso na data **/

    fun entryUser(worker:Ut,time:Long){
        val calendar = calendarLog(time)
        val user = "-> ${worker.user}:${worker.name}"
        val all = calendar + user
        fileUpdate(all)
    }

    fun fileUpdate(text:String){
        val file = FileInputStream("LOG.txt")
        val textfinal = FileOutputStream("LOG.txt")
        file.transferTo(textfinal)
        //add text
    }


    fun awayUser(worker:Ut,time:Long){
        val calendar = calendarLog(time)
        val user = "<- ${worker.user}:${worker.name}"
        val all = calendar + user
        fileUpdate(all)
    }

    fun calendarLog(time:Long):String{
        val calendar=Calendar.getInstance()
        calendar.setTimeInMillis(time)

        val ampm = calendar.get(Calendar.AM_PM)
        val hour = timeAmPm(ampm,calendar.get(Calendar.HOUR))
        val minute= calendar.get(Calendar.MINUTE)

        val day = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        return "$day/$month/$year $hour:$minute "
    }

    fun calendarAway(time: Long):String{
        val calendar=Calendar.getInstance()
        calendar.setTimeInMillis(time)

        val dayWeek = intToDay(calendar.get(Calendar.DAY_OF_WEEK))
        val ampm = calendar.get(Calendar.AM_PM)
        val hour = timeAmPm(ampm,calendar.get(Calendar.HOUR))
        val minute= calendar.get(Calendar.MINUTE)

        return "$dayWeek.$hour:$minute "
    }

    fun timeAmPm(ampm:Int,time: Int):Int{
        return if (ampm == 1 )  time + 12
        else time
    }

    fun intToDay(day:Int):String{
        return when(day){
            1 -> "Sun"
            2 -> "Mon"
            3 -> "Tue"
            4 -> "Wed"
            5 -> "Thu"
            6 -> "Fri"
            else -> "Sat"
        }
    }


}