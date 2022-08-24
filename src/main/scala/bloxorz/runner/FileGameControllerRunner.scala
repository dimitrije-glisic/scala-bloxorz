package bloxorz.runner

import bloxorz.common.Constants.BLOXORZ_UP
import bloxorz.common.{Board, BoardMaker, MapPicker}
import bloxorz.gamecontrol.GameStatus.{GAME_STATUS_LOSS, GAME_STATUS_WIN}
import bloxorz.gamecontrol.{Bloxorz, GameController}

import scala.io.Source
import scala.util.{Success, Try, Using}

object FileGameControllerRunner {

  def run(): Unit = {
    val gameController = init()
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

  def init(): GameController = {
    val matrix = BoardMaker.createMapMatrix(MapPicker.choseMapOfNAvailable(3))
    val controller: GameController = new GameController(new Board(matrix), new Bloxorz(BLOXORZ_UP, Board.getInitPosition(matrix), (-1, -1)))
    println("Game started.")
    controller.printGame()
    controller
  }


  def getMoves: List[Char] = {
    readLinesFromTextFile("src/main/resources/moves-sequence2") match {
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
