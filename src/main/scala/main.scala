object main {


  def main(args: Array[String]): Unit = {
    println("Fake Raspberry is starting...")
    GeneralConfig.refresh
    println("i have " + GeneralConfig.myGPIOs.length + " GPIOs")
  }
}