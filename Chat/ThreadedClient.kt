import java.io.*
import java.net.*
import kotlin.system.exitProcess

object ThreadedClient{
    internal var port = 8080
    internal var ip = "127.0.0.1"
    internal var pseudo = "#idChat" + (Math.random()*1000).toInt()
    internal var stop = false

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isNotEmpty()){
            ip = args[0]
            port = Integer.parseInt(args[1])
            pseudo = args[2]
        }
        val socket = Socket(ip,port)
        println("SOCKET = $socket")
        val sisr = BufferedReader(InputStreamReader(socket.getInputStream()))
        val sisw = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())),true)
        sisw.println(pseudo)

        val seizure = ManageSeizure(sisw)
        seizure.start()

        var str:String
        while (!stop){
            str = sisr.readLine()
            println(str)
        }

        println("END")
        //sisw.println("END")
        sisr.close()
        sisw.close()
        socket.close()
    }
}

internal class ManageSeizure(pw:PrintWriter):Thread(){
    private val keyboardInput:BufferedReader = BufferedReader(InputStreamReader(System.`in`))
    private val pw:PrintWriter = pw

    override fun run() {
        var str:String = ""
        try {
            while (str != "END"){
                str = keyboardInput.readLine()
                if (str.isNotEmpty()){
                    pw.println(str)
                }
            }
        }catch (e:IOException){e.printStackTrace()}
        ThreadedClient.stop = true
    }
}