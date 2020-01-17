import java.io.{FileNotFoundException, OutputStream}
import java.net.{InetAddress, InetSocketAddress, URL}

import com.sun.net.httpserver._

import scala.io.Source

class virtualHost {

  def getContent(filename: String): String = {
    var outstr: String = "";
    try {
      for (line <- Source.fromFile(filename).getLines) {
        outstr += line
      }
    }
    catch {
      case x: FileNotFoundException => {
        println("Exception: File missing")
        outstr = "<h1>404 File not found!</h1>";
      }
    }
    return outstr
  }

  def handler1(t: HttpExchange): Unit = {
    val response: String = getContent("pcb.html")
    t.sendResponseHeaders(200, response.length)

    val os: OutputStream = t.getResponseBody();
    os.write(response.getBytes())
    os.close()
  }

  def virHost(): Unit = {
    /*implicit val system: ActorSystem = ActorSystem("helloworld")
    implicit val executor: ExecutionContext = system.dispatcher
    val materializer: ActorMaterializer = ActorMaterializer()*/


  }


  println("here1")
  virHost()
  println("here 3")

  var ia1 = InetAddress.getLocalHost();
  println(ia1.getCanonicalHostName())

  val myurl: URL = new URL("http://oida.mon/");


  var isa1 = new InetSocketAddress(ia1, 5500)
  if (isa1.isUnresolved) {
    println("Server on Localhost")
    isa1 = new InetSocketAddress("localhost", 5500)
  }

  println(isa1.getAddress)

  var server = HttpServer.create(isa1, 0)
  var context: HttpContext = server.createContext("/")
  context.setHandler(handler1)


  println(server.getAddress().toString)
  server.start
}
