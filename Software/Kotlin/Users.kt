data class Ut(val user:Int ,val pass:Int,val name:String, var acumulateTime:Long, var entryTime:Long)

object Users {

    private var userlist= arrayOfNulls<Ut>(1000)

    fun init(){
        listUser()
    }

    private fun toUser(userId:Int, it: List<String>):Ut{
        return Ut(userId,it[1].toInt(),it[2],it[3].toLong(),it[4].toLong())
    }

    fun listUser(): Array<Ut?> {
        val newList = userlist

        var i = 0
        FileAcess.read("USERS.txt").forEach { line ->
            if (line != null){
                newList[i] = toUser(i, line.split(';'))
                i++
            }
        }
        return newList
    }

    fun removeUser(userId:Int){
        val userList = listUser()
        userList[userId] = null
    }

    fun addUser(userPass:Int, userName:String):Ut?{
        for(i in 0..999){
            if (userlist[i] == null) {
                userlist[i] = Ut(i,userPass,userName,0,0L)
                return userlist[i]!!
            }
        }
        return null
    }

    fun getUser(indiceUser: Int): Ut? {
        val list = userlist
        if (indiceUser == -1) return null
        return list[indiceUser]
    }

    fun updateList(){
        val lista = arrayOfNulls<String>(1000)
        val lastList = userlist
        for (i in lastList.indices) {
            val user = lastList[i]
            if (user != null) {
                lista[i]= "${i};${user.pass};${user.name};${user.acumulateTime};${user.entryTime}\n"
            }
        }
        FileAcess.write("USERS.txt",lista.filterNotNull().toList(),false)
    }

    fun updateUser(userId: Int,userAcumulateTime:Long,userEntryTime:Long){
        val list = userlist
        list[userId]!!.acumulateTime=userAcumulateTime
        list[userId]!!.entryTime=userEntryTime
        updateList()
    }
}

fun main(){
    Users.init()
    Users.addUser(123,"Teste123")
    Users.updateList()

}