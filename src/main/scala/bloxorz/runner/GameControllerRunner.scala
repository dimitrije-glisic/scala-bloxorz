package bloxorz.runner

import bloxorz.common.Constants.BLOXORZ_UP
import bloxorz.common.UserInputUtil.getMove
import bloxorz.gamecontrol.{Bloxorz, GameController}
import bloxorz.common.{Board, BoardMaker, MapPicker}
import bloxorz.gamecontrol.GameStatus.{GAME_STATUS_LOSS, GAME_STATUS_WIN}

import scala.annotation.tailrec

object GameControllerRunner {
  val SEPARATION_LINES = "------------------"
  val NEW_GAME_BANNER = s"\n\n$SEPARATION_LINES\nNEW GAME\n$SEPARATION_LINES\n\n"

  @tailrec
  def run(): Unit = {
    val controller = init()
    playGame(controller)
    print("Play again/Start menu? (p/s): ")
    if (scala.io.StdIn.readChar() == 'p') {
      print(NEW_GAME_BANNER)
      run()
    }
  }

  def init(): GameController = {
    val matrix = BoardMaker.createMapMatrix(MapPicker.choseMapOfNAvailable(3))
    val controller: GameController = new GameController(new Board(matrix), new Bloxorz(BLOXORZ_UP, Board.getInitPosition(matrix), (-1, -1)))
    println("Game started.")
    controller.printGame()
    controller
  }

  @tailrec
  def playGame(controller: GameController): Unit = {
    val move: Char = getMove
    if (move == 'q') {
      println("Are you sure you want to end this game? (y/n): ")
      if (scala.io.StdIn.readChar() == 'y') {
        return
      }
    }
    controller.playMove(move)
    val (status, message) = (controller.gameStatus.status, controller.gameStatus.message)
    if (status == GAME_STATUS_WIN || status == GAME_STATUS_LOSS) {
      println(s"End of game: ($status): $message")
      return
    }
    controller.printGame()
    playGame(controller)
  }


}
