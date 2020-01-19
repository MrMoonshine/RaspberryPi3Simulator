import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, Image}

import scala.sys.process._
import javax.imageio.ImageIO
import javax.swing.{BorderFactory, Icon, ImageIcon, JButton, JLabel, SwingConstants}

import scala.swing.Color

class UiGpio(pin: GPIO) {
  val mode = pin.mode
  var butt = new JButton("hello")
  var myLabel = new JLabel(pin.mode.toUpperCase + pin.getID)

  var r: Int = 0
  var b: Int = 0
  var g: Int = 0

  var a: Int = 128
  var bt: String = pin.name


  if (pin.mode != "in") {
    setLabel()
    myLabel.setBorder(BorderFactory.createLineBorder(GeneralConfig.PCB_COLOR, 12, false))
    myLabel.setHorizontalAlignment(SwingConstants.CENTER)
    myLabel.setOpaque(true)
    myLabel.setForeground(Color.WHITE)
  } else {
    try {
      butt.setText("GPIO" + pin.getID)
      val img: Icon = new ImageIcon("/home/david/IdeaProjects/FakePiV2/assets/button.png")
      butt.setIcon(img)
    } catch {
      case _: Throwable =>
        bt = bt + "Button"
        butt.setText(bt)

    }
    butt.setBorder(BorderFactory.createLineBorder(GeneralConfig.PCB_COLOR, 24))
    butt.addActionListener(new ActionListener {
      override def actionPerformed(actionEvent: ActionEvent): Unit = {
        //it will be low because it's a pull-up resistor and i want to be accurate :3
        val cmd = "perl " + GeneralConfig.MYPATHTEST + "writer.pl -w " + pin.getID + " state low"
        println(cmd)
        val cmdout = cmd.!! //test
        println(cmdout)
      }
    })
  }

  def setLabel(): Unit = {
    pin.updateGPIO() //the gpio array must ba updated in a paralell thread
    if (pin.mode == "out") {
      if (pin.state == "high")
        myLabel.setBackground(GeneralConfig.ledColor)
      else
        myLabel.setBackground(Color.BLACK)

    } else if (pin.mode == "pwm") {

      a = ((pin.dutycycle.asInstanceOf[Float] / 100.0) * 255.0).asInstanceOf[Int]

      r = GeneralConfig.ledColor.getRed
      b = GeneralConfig.ledColor.getBlue
      g = GeneralConfig.ledColor.getGreen

      myLabel.setBackground(new Color(r, g, b, a))
      //myLabel.setForeground(java.awt.Color.WHITE)
    } else {
      //shouldn't happen...usually
      myLabel.setBackground(java.awt.Color.MAGENTA)
      myLabel.setForeground(java.awt.Color.RED)
    }
  }


}
