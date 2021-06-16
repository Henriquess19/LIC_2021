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

    fun write(file:String, list: List<String>){
        val text = FileWriter(file)
        for (i in list.indices) {
            text.append(list[i])
            /** DAR \n apos cada linha **/
            /** Guardar o logfile e escrever após **/
        }

        text.close()
    }

}


fun main(){
    LogFile.awayUser(Ut(0,0,"ei",0,0L),656)
}