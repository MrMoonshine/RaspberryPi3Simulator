import com.typesafe.config._
//https://github.com/lightbend/config#api-example
import scala.util.matching.Regex

class GPIO(name_i: String) {
  //println(name)
  val name = name_i
  var mode: String = "in"
  var state: String = "undefined"
  var dutycycle: Int = 50

  var attributes = FileHandlers.catArr(GeneralConfig.MYPATH + name)
  //attributes.foreach(println(_))
  try {
    mode = attrValue("mode")
    getState
    //println("Successfully created " + name)
  } catch {
    case e: Exception => println("Couldn't create GPIO " + name)
  }

  private def getState(): Unit = {
    if (mode != "pwm") {
      state = attrValue("state")
      dutycycle = 0
    } else if (mode == "pwm") {
      dutycycle = attrValue("dutycycle").toInt
      //println("My DC is: " + dutycycle)
    }
  }

  //This function will return the first (case-insensitive) match of an String Array
  //if nothing is found it throws an Exception
  private def findInArray(arr: Array[String], searchString: String): String = {
    val fpatt: Regex = searchString.toLowerCase.r
    for (j <- arr.indices) {
      fpatt.findFirstMatchIn(arr(j).toLowerCase) match {
        case Some(_) => return arr(j).toLowerCase
        case None => ; //println("No Match")
      }
    }
    throw new Exception("No Matches Found!")
  }

  //This function returns the value of a config string eg. "name=value"
  //string Attribute name
  //returns value
  private def attrValue(stringAttribute: String): String = {
    val str_i = findInArray(attributes, stringAttribute + "=").toLowerCase
    val mypattern: Regex = "([a-z0-9- ]+)=([a-z0-9- ]+)".r

    for (j <- mypattern.findAllMatchIn(str_i)) {
      //println(s"key: ${j.group(1)} value: ${j.group(2)}")
      return j.group(2)
    }
    throw new Exception("Nothing found")
  }

  //This function updates all values (mode is out or pwm)
  def updateGPIO(): Unit = {
    try {
      if (mode == "in") {
        return
      }

      getState
    } catch {
      case e: Exception => ;
    }
  }

  def getID(): String = {
    val ipatt: Regex = "([0-9- ]+)".r

    ipatt.findFirstIn(name).get
  }

}
