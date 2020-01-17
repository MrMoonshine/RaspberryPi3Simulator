import java.io.{BufferedWriter, File, FileNotFoundException, FileWriter, PrintWriter}

import javax.swing.JButton

class Buttons {

  var butt = new JButton("button")
  var state = 0;

  def update(index: Int = -1, path: String = ""): Unit = {
    if (index == -1) {
      return
    }

    val filename: String = path + "/buttons/BUTTON" + index + ".txt"
    //println("My path is: " + filename);
    try {
      val writer = new PrintWriter(new File(filename))
      writer.write(state)
      writer.close()
    }
    catch {
      case x: FileNotFoundException => {
        if (!(path == "")) {
          new java.io.File(path + "/buttons").mkdirs;
          new java.io.File(filename).createNewFile
          val bw = new BufferedWriter(new FileWriter(new File(filename)))
          bw.write(state.toString)
          bw.close
        }
      }
    }
  }
}
