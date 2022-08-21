package bloxorz.runner

import scala.annotation.tailrec

object GeneralRunner {
  val menu: String =
    "Start Menu:\n" +
      "1. New game\n" +
      "2. Edit map\n" +
      "3. Play game with moves from a given file\n" +
      "4. Exit\n"

  @tailrec
  def run(): Unit = {
    println(menu)
    print("Your input:")
    val option = scala.io.StdIn.readChar()
    option match {
      case '1' => GameControllerRunner.run()
      case '2' => MapEditorRunner.run()
      case '3' => FileGameControllerRunner.run()
      case '4' => {
        println("Bye bye...hope to see you soon! :)")
        return
      }
    }
    run()

  }
}
