import java.io.File

object FileAcess {
    private var listUser: Array<String?> = emptyArray()

    init {
        listUser = read()
    }

    fun read(): Array<String?> {
        val list = File("USERS.txt").inputStream()
        val lineList = arrayOfNulls<String>(1000)
        list.bufferedReader().useLines { lines -> lines.forEach { lineList[it.split(';')[0].toInt()] = it } }
        return lineList
    }


    fun getUser(indiceUser: Int): Ut? {
        if (listUser[indiceUser] == null || indiceUser == -1){
            return null
        }
        var entry = 0L
        val user = listUser[indiceUser]!!.split(';')
        if (user.lastIndex != listUser[indiceUser]!!.lastIndex) entry = user[user.lastIndex].toLong()
        return Ut(user[0].toInt(),user[1].toInt(),listUser[indiceUser]!!.split(';')[2],user[3].toLong(),entry)
        TODO("METER GENÉRICO")
    }

    fun allUser():Array<Ut?>{
        val array:Array<Ut?> = arrayOfNulls<Ut?>(1000)
        var i =0

       listUser.forEach {
        lines -> lines!!.split('j').forEach {
        array[i++] = Ut(it[0].toInt(),it[1].toInt(),listUser[i]!!.split(';')[2],it[3].toLong(),if (it.lastIndex != listUser[i]!!.lastIndex) it[i].toLong() else 0L) } }

     return array
}

}
fun main(){
   FileAcess.allUser()
}