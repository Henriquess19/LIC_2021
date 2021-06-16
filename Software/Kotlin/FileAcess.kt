import java.io.File
import java.io.FileWriter
import java.sql.Time

object FileAcess {

    fun read(file:String): Array<String?> {
        val list = File(file).inputStream()
        val lineList = arrayOfNulls<String>(1000)
        list.bufferedReader().useLines { lines -> lines.forEach { lineList[it.split(';')[0].toInt()] = it } }
        return lineList
    }

    fun write(file:String, list: List<String>,logNUsers:Boolean) {

        val text = FileWriter(file, logNUsers)
        for (i in list.indices) {
            if (logNUsers) text.append("\n${list[i]}")
            else text.append(list[i])
        }
        text.close()
    }
}


fun main(){
    LogFile.awayUser(Ut(0,0,"ei",0,0L),656)
}