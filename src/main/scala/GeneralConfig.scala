import java.awt.Color
import java.awt._
import java.awt.event.{ActionEvent, ActionListener}

import javax.swing.{JButton, JFrame, JLabel, WindowConstants}

import scala.util.Random

object GeneralConfig {
  val OUT: String = "out"
  val IN: String = "in"
  val PWM: String = "pwm"

  val TEMPLIN = "/sys/class/thermal/thermal_zone0/temp"
  val TEMPWIN = "This feature isn't working in Windows :("

  var MYPATH = "/home/david/Documents/RaspberryPi/ThreadBlinky/gpio/"
  var MYPATHTEST = "/home/david/Documents/RaspberryPi/ThreadBlinky/"
  var myGPIOs: Array[GPIO] = Array[GPIO]()
  var iouiarr: Array[UiGpio] = Array[UiGpio]()

  lazy val colrandomizer = Random
  lazy val colbutt = new JButton("Randomize Color")
  var ledColor: Color = java.awt.Color.RED
  val PCB_COLOR: Color = new Color(40, 111, 29, 255)

  var MainFrame = new JFrame
  var dirLabel = new JLabel

  private def genGrid(a: Int, myDivider: Int = 4): GridLayout = {
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

  private def updateLabels(): Unit = {
    for (j <- iouiarr.indices) {
      if (iouiarr(j).mode != "in") {
        iouiarr(j).setLabel
      }
    }
  }

  private def getSettingsButton(): JLabel = {
    val gridLabel: JLabel = new JLabel()
    val dirbutt = new JButton("Choose Directory")
    val genbutt = new JButton("Generate from JSON")

    gridLabel.setBackground(GeneralConfig.PCB_COLOR)
    gridLabel.setOpaque(true)
    gridLabel.setLayout(new GridLayout(3, 1))

    gridLabel.add(dirbutt)
    gridLabel.add(colbutt)
    gridLabel.add(genbutt)

    //Select Directory
    dirbutt.addActionListener(new ActionListener {
      override def actionPerformed(actionEvent: ActionEvent): Unit = {
        val seldir = new DirSelector();
        GeneralConfig.MYPATH = seldir.dir + "/"
        dirLabel.setText(GeneralConfig.MYPATH)
      }
    })

    //Create files
    genbutt.addActionListener(new ActionListener {
      override def actionPerformed(actionEvent: ActionEvent): Unit = {
        println("W I P")
      }
    })

    gridLabel
  }

  def frameCreator(): Unit = {
    refresh
    MainFrame = new JFrame()
    MainFrame.setBackground(GeneralConfig.PCB_COLOR)
    myGPIOs.sortBy(_.mode)
    MainFrame.setLayout(genGrid(myGPIOs.length))
    for (j <- iouiarr.indices) {
      if (iouiarr(j).mode == "in") {
        MainFrame.add(iouiarr(j).butt)
      } else {
        MainFrame.add(iouiarr(j).myLabel)
      }
    }

    dirLabel = new JLabel(GeneralConfig.MYPATH)
    dirLabel.setBackground(GeneralConfig.PCB_COLOR)
    dirLabel.setOpaque(true)
    dirLabel.setForeground(Color.WHITE)

    val gridLabel = getSettingsButton()
    MainFrame.add(gridLabel)

    //Randomize Led Color
    colbutt.addActionListener(new ActionListener {
      override def actionPerformed(actionEvent: ActionEvent): Unit = {
        println("changing colors")
        ledColor = new Color(colrandomizer.nextInt(255), colrandomizer.nextInt(255), colrandomizer.nextInt(255), 255)
        updateLabels()
      }
    })

    MainFrame.add(dirLabel)
    MainFrame.setSize(800, 300)
    MainFrame.setTitle("I'm a Raspberry-Pi! trust me!")
    MainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    MainFrame.setVisible(true)
  }
}
