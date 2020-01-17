import java.awt.Color
import java.awt._

import javax.swing.{JFrame, JLabel, WindowConstants}

object GeneralConfig {
  val OUT: String = "out"
  val IN: String = "in"
  val PWM: String = "pwm"

  val TEMPLIN = "/sys/class/thermal/thermal_zone0/temp"
  val TEMPWIN = "This feature isn't working in Windows :("

  val MYPATH = "/home/david/Documents/RaspberryPi/ThreadBlinky/gpio/"
  val MYDEFAULTPATH = "/home/david/Documents/RaspberryPi/ThreadBlinky/"

  var myGPIOs: Array[GPIO] = Array[GPIO]()
  var iouiarr: Array[UiGpio] = Array[UiGpio]()

  var ledColor: Color = java.awt.Color.GREEN

  var MainFrame = new JFrame

  private def genGrid(a: Int): GridLayout = {
    val myDivider: Int = 4
    var rows: Int = 0

    rows = rows + a / myDivider

    if (a % myDivider != 0) {
      rows = rows + 1
    }

    new GridLayout(rows, myDivider)
  }

  def refresh(): Unit = {
    val gpionames = FileHandlers.dirList(MYPATH) //check what GPIOs should be Used
    for (j <- gpionames.indices) {
      myGPIOs = myGPIOs :+ new GPIO(gpionames(j))
      iouiarr = iouiarr :+ new UiGpio(myGPIOs(j))
    }
  }

  def frameCreator(): Unit = {
    refresh
    MainFrame = new JFrame()
    MainFrame.setBackground(java.awt.Color.BLACK)
    MainFrame.setLayout(genGrid(myGPIOs.length))
    for (j <- iouiarr.indices) {
      MainFrame.add(iouiarr(j).myLabel)
    }
    MainFrame.setSize(400, 500)
    MainFrame.setTitle("Oida")
    MainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    MainFrame.setVisible(true)
  }
}
