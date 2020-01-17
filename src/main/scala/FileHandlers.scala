import java.io.{File, FileNotFoundException}

import scala.io.Source

object FileHandlers {
  def cat(filename: String, failureMsg: String = "File Not Found: ", logName: Boolean = true): String = {
    var str: String = ""
    try {

      for (line <- Source.fromFile(filename).getLines) {
        str = str + line
      }
      return str
    } catch {
      case x: FileNotFoundException =>
        str = str + failureMsg
        if (logName) {
          str = str + filename
        }
        return str
    }
  }

  def catArr(filename: String, failureMsg: String = "File Not Found: "): Array[String] = {
    var arr = Array[String]()
    try {

      for (line <- Source.fromFile(filename).getLines) {
        arr = arr :+ line
      }
      return arr
    } catch {
      case x: FileNotFoundException => {
        arr = arr :+ (failureMsg + filename)
        return arr
      }
    }
  }

  def dirList(dir_i: String): Array[String] = {
    val filearr = gpiodir.list
    //filearr.foreach(println(_))
    //println("list end!")
    return filearr
  }

  val gpiodir = new File(GeneralConfig.MYPATH)
}
