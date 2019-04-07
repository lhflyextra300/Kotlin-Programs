import java.io.*
import java.net.*

object ServerMC{
    internal var port = 8080
    internal val maxClients = 50
    internal var pw = arrayOfNulls<PrintWriter>(maxClients)
    internal var numClient = 0

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isNotEmpty()){
            port = Integer.parseInt(args[0])
        }
        val s = ServerSocket(port)
        println("SOCKET ECOUTE CREE => $s")
        while (numClient < maxClients){
            val soc = s.accept()
            val cc = ConnexionClient(numClient,soc)
            println("NEW CONNECTION - SOCKET => $soc")
            numClient++
            cc.start()
        }
    }
}

internal class ConnexionClient(id:Int, s:Socket):Thread(){
    private var id:Int = id
    private var pseudo:String = ""
    private var stop = false
    private var s:Socket = s
    private var sisr:BufferedReader = BufferedReader(InputStreamReader(s.getInputStream()))
    private var sisw:PrintWriter = PrintWriter(BufferedWriter(OutputStreamWriter(s.getOutputStream())),true)

    override fun run() {

        try {
            ServerMC.pw[id] = sisw
            pseudo = sisr.readLine()

        }catch (e:IOException){e.printStackTrace()}

        try {
            while (true){
                val str:String = sisr.readLine() //lecture du message
                if (str == "END") break
                println("Send by #$id,$pseudo => $str")
                //on envoi a tous
                for (i in 0 until ServerMC.numClient){
                    if (ServerMC.pw[i] != null && i != id && str.isNotEmpty()){
                        ServerMC.pw[i]?.println("$pseudo => $str")
                    }//envoi à tous
                }
            }
            sisr.close()
            sisw.close()
            s.close()
        }catch (e:IOException){e.printStackTrace()}
    }
}