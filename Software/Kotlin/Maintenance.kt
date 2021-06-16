import java.io.Console
import java.io.File

object Maintenance {

    fun init(){
        LCD.clear()
        TUI.writecenter("Out Of Service",0)
        TUI.writecenter("*_*",1)
        systemModem()
    }

    private fun systemModem(){
        println("What operation you want to proceed? \n 1 - Add User \n 2 - Remove User \n 3 - User List \n 4 - ShutDown ")
        when(readLine()!!){
            "1" -> addUser()
            "2" -> removeUser()
            "3" -> userList()
            "4" -> shutDown()
            else -> operationNotFound()
        }
    }

    private fun operationNotFound(){
        println("Operation Not Found \n ")
        restartOperations()
    }

    private fun addUser(){
        val name = name()
        val pass = pass()
        val add= Users.addUser(pass,name)

        if (add != null) println("You user is: $add")
        else println("Sorry, userList is full xOxO")
        restartOperations()
    }

    private fun name():String{
        print("UserName(Máx 16 Chars): ")
        val name = readLine()!!
        if (name.length>16){
            println("Please choose one UserName with maxium 16 chars")
            name()
        }
        return name
    }

    private fun pass():Int{
        print("UserPass(3 Chars): ")
        val pass = readLine()!!
        if (pass.length !=3){
            println("Please choose one UserPass with 3 chars")
            pass()
        }
        return pass.toInt()
    }

    fun removeUser(){
        println("Insert UserID")
        val userId = readLine()!!.toInt()
        val user = Users.getUser(userId)
        if (user != null){
            println(user.name + ",Is this the User to remove? Yes or No")
            val confirmation = readLine()!!
            if ("Y" in confirmation  || "y" in confirmation) {
                Users.removeUser(userId)
            }

        }else{
            println("User Not Found \n")
        }
        restartOperations()
    }

    private fun userList(){
        Users.listUser().filterNotNull().forEach{println(it)}
        restartOperations()
    }

    fun shutDown(){
        LCD.off()
        Users.updateList()
    }

    private fun restartOperations(){
        APP.mode()
    }

}
fun main(){
   // LCD.init()
    //Maintenance.init()
    //Maintenance.userList()
    Users.init()
    Maintenance.removeUser()
    Maintenance.shutDown()

}