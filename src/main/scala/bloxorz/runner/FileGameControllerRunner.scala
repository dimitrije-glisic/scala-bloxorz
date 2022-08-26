package bloxorz.runner

import bloxorz.gamecontrol.GameStatus.{GAME_STATUS_LOSS, GAME_STATUS_WIN}

import scala.io.Source
import scala.util.{Success, Try, Using}

object FileGameControllerRunner {

  def run(): Unit = {
    val gameController = GameControllerRunner.init()
    getMoves.foreach(move => {
      println(s"Move: $move")
      gameController.playMove(move)
      gameController.printGame()
      val (status, message) = (gameController.gameStatus.status, gameController.gameStatus.message)
      if (status == GAME_STATUS_WIN || status == GAME_STATUS_LOSS) {
        println(s"End of game: ($status): $message")
        return
      }
    })
  }

  def getMoves: List[Char] = {
    readLinesFromTextFile("src/test/resources/moves-sequence") match {
      case Success(lines) => lines filter (_.nonEmpty) map (_.charAt(0)) filter isValidMove
    }
  }

  def readLinesFromTextFile(filename: String): Try[List[String]] = {
    Using(Source.fromFile(filename)) { reader => reader.getLines().toList }
  }

  def isValidMove(c: Char): Boolean = {
    c == 'u' || c == 'd' || c == 'l' || c == 'r'
  }

}
