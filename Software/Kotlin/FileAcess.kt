import java.io.File

object FileAcess {

    fun read(): Array<String?> {
        val list = File("USERS.txt").inputStream()
        val lineList = arrayOfNulls<String>(1000)
        list.bufferedReader().useLines { lines -> lines.forEach { lineList[it.split(';')[0].toInt()] = it } }
        return lineList
    }


    fun getUser(indiceUser: Int): Ut? {
        var listUser = read()
        if (listUser[indiceUser] == null || indiceUser == -1){
            return null
        }
        var entry = 0L
        val user = listUser[indiceUser]!!.split(';')
        if (user.lastIndex != listUser[indiceUser]!!.lastIndex) entry = user[user.lastIndex].toLong()
        return Ut(user[0].toInt(),user[1].toInt(),listUser[indiceUser]!!.split(';')[2],user[3].toInt(),entry,0)
        TODO("METER GENÉRICO")
    }

}
fun main(){
    println(FileAcess.getUser(0))
}