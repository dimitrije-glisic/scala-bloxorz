package bloxorz

import bloxorz.Constants.BLOXORZ_UP
import bloxorz.GameStatus.{GAME_STATUS_LOSS, GAME_STATUS_WIN}
import bloxorz.UserInputUtil.getMove

import scala.annotation.tailrec

object GameControllerRunner {
  val SEPARATION_LINES="------------------"
  val NEW_GAME_BANNER = s"\n\n$SEPARATION_LINES\nNEW GAME\n$SEPARATION_LINES\n\n"

  @tailrec
  def run(): Unit = {
    val matrix = BoardMaker.createMapMatrix("src/main/resources/bloxorzmap")
    val controller: Controller = new Controller(new Board(matrix), new Bloxorz(BLOXORZ_UP, Board.getInitPosition(matrix), (-1, -1)))
    controller.printGame()
    playGame(controller)
    print("Play again/Start menu? (p/s): ")
    if(scala.io.StdIn.readChar() == 'p') {
      print(NEW_GAME_BANNER)
      run()
    }
  }

  @tailrec
  def playGame(controller: Controller): Unit = {

    val move: Char = getMove
    if (move == 'q') {
      println("Are you sure you want to end this game? (y/n): ")
      if(scala.io.StdIn.readChar() == 'y') {
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
