package bloxorz.common

object UserInputUtil {
  def getMove: Char = {
    print("Choose command: 'd', 'u', 'l', 'r':")
    scala.io.StdIn.readChar()
  }

}
