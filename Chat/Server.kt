import java.io.*
import java.net.*

object Server{

    internal val port = 8080

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val s = ServerSocket(port)
        val soc = s.accept()
        println("SOCKET $s")
        println("SOCKET $soc")
        // BufferedReader permet de lire par ligne
        val sisr = BufferedReader(InputStreamReader(soc.getInputStream()))
        // Un PrintWriter possede toutes les operations print classiques.
        // En mode auto-flush, le tampon est vide (flush) a l'appel de println.
        val sisw = PrintWriter(BufferedWriter(OutputStreamWriter(soc.getOutputStream())),true)
        while (true){
            val str = sisr.readLine() //lecture du message
            if (str == "END") break
            println("ECHO = $str") //trace locale
            sisw.println(str) //renvoi d'un echo
        }
        sisr.close()
        sisw.close()
        soc.close()
    }
}