import java.io.*
import java.net.*

object Client{
    internal val port = 8080
    internal val ip = "127.0.0.1"

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val socket = Socket(ip, port)
        println("SOCKET = $socket")
        //illustration des capacites bidirectionnelles du flux
        val sisr = BufferedReader(InputStreamReader(socket.getInputStream()))
        val sisw = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())),true)
        var str = "Bonjour "
        for (i in 0..9){
            sisw.println(str + i) //envoi d'un message
            str = sisr.readLine() //lecture de la reponse
            println(str)
        }
        println("END")
        sisw.println("END") //message de fermeture
        sisr.close()
        sisw.close()
        socket.close()
    }
}