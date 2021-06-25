import isel.leic.utils.Time
import java.util.*

object LogFile {
    private var listLog= mutableListOf<String>()


    fun entryUser(worker:Ut,time:Long){
        val calendar = calendarLog(time)
        val user = "-> ${worker.user}:${worker.name}"
        val entry = calendar + user
        listLog += entry
        logUpdate(listLog.toList())
    }

    private fun logUpdate(list: List<String>){
        FileAcess.write("LOG.txt",list,true)
    }


    fun awayUser(worker:Ut,time:Long){
        val calendar = calendarLog(time)
        val user = "<- ${worker.user}:${worker.name}"
        val away = calendar + user
        listLog += away
        logUpdate(listLog.toList())
    }

    fun calendarLog(time:Long):String{
        val calendar=Calendar.getInstance()
        calendar.setTimeInMillis(time)

        val ampm = calendar.get(Calendar.AM_PM)
        val hour = timeAmPm(ampm,calendar.get(Calendar.HOUR))
        val minute= calendar.get(Calendar.MINUTE)

        val day = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH) + 1 /** Because indeces **/
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

    private fun timeAmPm(ampm:Int, time: Int):Int{
        return if (ampm == 1 )  time + 12
        else time
    }

    private fun intToDay(day:Int):String{
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
fun main(){
    println(LogFile.calendarLog(Time.getTimeInMillis()))
    println(LogFile.calendarAway(Time.getTimeInMillis()))
}