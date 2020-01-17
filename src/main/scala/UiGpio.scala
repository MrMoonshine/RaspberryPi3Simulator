import java.awt.Color

import javax.swing.JLabel

import scala.swing.Color

class UiGpio(pin: GPIO) {
  var myLabel = new JLabel(pin.name + pin.mode)
  myLabel.setOpaque(true)

  if (pin.mode == "out") {
    myLabel.setBackground(java.awt.Color.YELLOW)
  } else if (pin.mode == "pwm") {
    val r = GeneralConfig.ledColor.getRed
    val b = GeneralConfig.ledColor.getBlue
    val g = GeneralConfig.ledColor.getGreen

    val a: Int = ((pin.dutycycle.asInstanceOf[Float] / 100.0) * 255.0).asInstanceOf[Int]

    myLabel.setBackground(new Color(r, g, b, a))
  } else {
    myLabel.setBackground(java.awt.Color.RED)
  }
}
