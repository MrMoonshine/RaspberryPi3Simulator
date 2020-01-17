import javax.swing.JFileChooser

class DirSelector {
  var dir: String = "";
  var datSel = new JFileChooser();

  datSel.setDialogTitle("Choose file:")
  datSel.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
  datSel.setAcceptAllFileFilterUsed(false)

  if (datSel.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    //println("getCurrentDirectory(): " + datSel.getCurrentDirectory())
    dir = datSel.getSelectedFile().toString()
    println("getSelectedFile() : " + dir)
  } else {
    println("No Selection")
  }
}
