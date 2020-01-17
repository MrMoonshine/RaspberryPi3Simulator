import java.awt.Color

object GeneralConfig {
  val OUT: String = "out"
  val IN: String = "in"
  val PWM: String = "pwm"

  val TEMPLIN = "/sys/class/thermal/thermal_zone0/temp"
  val TEMPWIN = "This feature isn't working in Windows :("

  val MYPATH = "/home/david/Documents/RaspberryPi/ThreadBlinky/gpio/"
  val MYDEFAULTPATH = "/home/david/Documents/RaspberryPi/ThreadBlinky/"

  var myGPIOs: Array[GPIO] = Array[GPIO]()

  var ledColor: Color = java.awt.Color.RED

  def refresh(): Unit = {
    val gpionames = FileHandlers.dirList(MYPATH) //check what GPIOs should be Used
    for (j <- gpionames.indices) {
      myGPIOs = myGPIOs :+ new GPIO(gpionames(j))
    }
  }
}
