import java.awt.GridLayout
import java.awt.event.{ActionEvent, ActionListener}
import java.lang._
import javax.swing._
import javax.swing.event.{AncestorEvent, AncestorListener, ChangeEvent, ChangeListener}

class MainFrame {

  val buttTre = new Thread {
    override def run {
      Thread.sleep(500)
      for (y <- butt.indices) {
        println(y + " LOW")
        butt(y).state = 0;
      }
      println("finished")
      return
    }
  }

  private def myAction(index: Int): Unit = {
    //println("Button " + index + " has been Pressed!")
    butt(index).state = 1;

    if (!buttTre.isAlive) {
      buttTre.run
    }
  }


  var ledDelay: Int = 500;
  //Grid layout
  var glay = new GridLayout(3, 4)
  var settingsButtons = new GridLayout(3, 2)
  var gridLabel = new JLabel()
  var dirLabel = new JLabel()
  var vHostL = new JLabel()
  var delLabel = new JLabel()


  //Buttons and LEDs
  var butt = Array(new Buttons(), new Buttons(), new Buttons());
  var dirbutt = new JButton("Choose Directory")
  var updater = new JSlider(0, 100, 2000, 500)
  var imgHostname = new JTextField("W.I.P")
  var leds = Array(new Led(), new Led(), new Led(), new Led())

  delLabel.setText("Update Speed: " + ledDelay.toString + "ms")
  //Main frame
  var f = new JFrame("I'm a Raspberry Pi! Trust me!")
  f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  //f.getContentPane.setBackground(java.awt.Color.BLACK)
  f.setSize(800, 300)
  f.setLayout(glay)

  //Print Hostname after hitting Enter
  imgHostname.addActionListener(new ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      vHostL.setText(imgHostname.getText())
    }
  })

  //Select Directory
  dirbutt.addActionListener(new ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      var seldir = new DirSelector();
      dirLabel.setText(seldir.dir)
    }
  })

  //set led styles
  for (x <- leds.indices) {
    leds(x).label.setText("LED" + x)
    leds(x).update()
    f.add(leds(x).label)
  }

  //set button styles
  for (x <- butt.indices) {
    butt(x).butt.setText("Button " + x)
    butt(x).butt.addActionListener(new ActionListener {
      override def actionPerformed(actionEvent: ActionEvent): Unit = {
        myAction(x)
      }
    })

    //butt(x).setBorder(BorderFactory.createLineBorder(java.awt.Color.GREEN,12))
    f.add(butt(x).butt)
  }

  updater.addChangeListener(new ChangeListener {
    override def stateChanged(changeEvent: ChangeEvent): Unit = {
      //println("State Changed to: " + updater.getValue)
      ledDelay = updater.getValue
      delLabel.setText("Update Speed: " + ledDelay.toString + "ms")
    }
  })


  f.add(delLabel)

  val text: String = "Fake Raspberry";
  //f.add(new JLabel("<html><div style='border: 3px solid green; color:white;'><h3>"+text+"</h3></div></html>"))

  //init settings buttons
  gridLabel.setLayout(settingsButtons)

  gridLabel.add(new JLabel("Hostname"))
  gridLabel.add(imgHostname)

  gridLabel.add(new JLabel("Update Speed"))
  gridLabel.add(updater)

  gridLabel.add(new JLabel("Change Directory"))
  gridLabel.add(dirbutt)

  gridLabel.setOpaque(true)
  f.add(gridLabel)
  f.add(dirLabel)
  f.add(vHostL)

  //Finally make it visible
  f.setVisible(true)

  //Update Buttons
  buttTre.start

  //Update LEDS
  val t = new java.util.Timer()
  val task = new java.util.TimerTask {
    def run(): Unit = {
      for (y <- leds.indices) {
        leds(y).update(y, dirLabel.getText)
      }
      for (x <- butt.indices) {
        butt(x).update(x, dirLabel.getText);
      }
    }
  }
  t.schedule(task, 0, ledDelay)
  //task.cancel()

}
