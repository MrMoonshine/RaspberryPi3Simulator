import java.awt.{Color, Image}

import javax.imageio.ImageIO
import javax.swing.{BorderFactory, Icon, ImageIcon, JButton, JLabel}

import scala.swing.Color

class UiGpio(pin: GPIO) {
  val mode = pin.mode
  var butt = new JButton("hello")
  var myLabel = new JLabel(pin.name + pin.mode)

  lazy val r: Int = GeneralConfig.ledColor.getRed
  lazy val b: Int = GeneralConfig.ledColor.getBlue
  lazy val g: Int = GeneralConfig.ledColor.getGreen

  var a: Int = 128
  var bt: String = pin.name


  if (pin.mode != "in") {
    setLabel()
    myLabel.setBorder(BorderFactory.createLineBorder(GeneralConfig.PCB_COLOR, 12))

    myLabel.setOpaque(true)
    myLabel.setForeground(Color.WHITE)
  } else {
    try {
      butt.setText("GPIO" + pin.getID)
      val img: Icon = new ImageIcon("/home/david/IdeaProjects/FakePiV2/assets/button.png")
      butt.setIcon(img)
    } catch {
      case _: Throwable => {
        bt = bt + "Button"
        butt.setText(bt)
      }
    }
    butt.setBorder(BorderFactory.createLineBorder(GeneralConfig.PCB_COLOR, 24))
  }

  def setLabel(): Unit = {
    if (pin.mode == "out") {
      myLabel.setBackground(java.awt.Color.YELLOW)
    } else if (pin.mode == "pwm") {

      a = ((pin.dutycycle.asInstanceOf[Float] / 100.0) * 255.0).asInstanceOf[Int]

      myLabel.setBackground(new Color(r, g, b, a))
    } else {
      myLabel.setBackground(java.awt.Color.MAGENTA)
    }
  }


}
