object main {


  def main(args: Array[String]): Unit = {
    println("Fake Raspberry is starting...")
    GeneralConfig.refresh()
    //GeneralConfig.frameCreator()
    println("i have " + GeneralConfig.myGPIOs.length + " GPIOs")

  }
}