import java.io.{BufferedWriter, File, FileNotFoundException, FileWriter}

import javax.swing._

import scala.io.Source

class Led() {
  var state: Int = -1
  var label = new JLabel("LED", SwingConstants.CENTER)
  label.setForeground(java.awt.Color.WHITE)
  label.setOpaque(true)
  label.setBorder(BorderFactory.createLineBorder(java.awt.Color.WHITE, 12))

  def update(index: Int = -1, path: String = ""): Unit = {
    if (index != -1) {
      val filename: String = path + "/leds/LED" + index + ".txt"
      //println("My path is: " + filename);
      try {
        for (line <- Source.fromFile(filename).getLines) {
          //println(line)
          if (line == "0") {
            state = 0;
          } else if (line == "1") {
            state = 1;
          }
        }
      }
      catch {
        case x: FileNotFoundException => {
          if (!(path == "")) {
            new java.io.File(path + "/leds").mkdirs
            new java.io.File(filename).createNewFile
            val bw = new BufferedWriter(new FileWriter(new File(filename)))
            bw.write("0")
            bw.close

          }
        }
      }
    }

    if (state == 1) {
      label.setBackground(java.awt.Color.RED)
    } else if (state == 0) {
      label.setBackground(java.awt.Color.BLACK)
    } else {
      label.setBackground(java.awt.Color.BLUE) //Warning for error
    }

  }
}
