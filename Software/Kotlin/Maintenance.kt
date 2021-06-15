import java.io.Console
import java.io.File

object Maintenance {

    /** Perceber botão foi ativado **/

    fun init(){
        LCD.clear()
        TUI.writecenter("Out Of Service",0)
        TUI.writecenter("*_*",1)
    }

    private fun systemModem(){
        println("What operation you want to proceed? \n 1 - Add User \n 2 - Remove User \n 3 - User List \n 4 - ShutDown ")
        when(readLine()!!){
            "1" -> addUser()
            "2" -> removeUser()
            "3" -> userList()
            "4" -> shutDown()
            else -> notFound()
        }
    }

    private fun notFound(){
        println("Operation Not Found \n ")
        systemModem()
    }

    private fun addUser(){
        /** Primeiro user livre da lista **/
        println("User name:")
        val name = readLine()!!
        if (name.length<=16){
            /** Pedir a pass **/
        }else{
            println("Please choose one UserName with maxium 16 chars")
            /** Pedir usar name again **/
        }
    }

    private fun removeUser(){
        println("Insert UserID")
        val userId = readLine()!!.toInt()
        val user = FileAcess.getUser(userId)
        if (user != null){
            println(user.name + ",Is this the User to remove? Yes or No")
            val confirmation = readLine()!!
            if (("Y" in confirmation) || "y" in confirmation) {
                /** REMOVE USER FROM LIST **/
            }

        }else{
            println("User Not Found \n")
        }

        println("1 - Remove Another User \n 2 - Choose Another Operation")
        val option = readLine()!!.toInt()
        if (option == 1) removeUser()
        else systemModem()
    }

    fun userList(){
        File("USERS.txt").forEachLine { println(it.split(';')) }
    }

    private fun shutDown(){
        /** ??? **/
    }



}
fun main(){
    LCD.init()
    Maintenance.init()
    Maintenance.userList()
}