import bloxorz.{Bloxorz, Board, BoardMaker, Controller}

import scala.annotation.tailrec

object Application {

  @tailrec
  def playGame(controller:Controller): Unit = {
    val move = getMove()
    if(move == 'q') {
      println("Bye, bye...:)")
      return
    }
    controller.playMove(move)
    controller.printGame()
    playGame(controller)
  }

  def main(args: Array[String]): Unit = {
    println("Hello from Bloxorz Applications")
    val matrix = BoardMaker.createBoard("src/main/resources/bloxorzmap.txt")

    val controller: Controller = new Controller(
      new Board(matrix),
      new Bloxorz("UP", getInitPosition(matrix), (-1, -1))
    )

    controller.printGame()
    playGame(controller)

  }

  def getInitPosition(matrix: List[Array[Char]]): (Int, Int) = {
    var result = (-1, -1)
    for ((row, i) <- matrix.view.zipWithIndex if row.contains('S')) result = (i, row.indexOf('S'))
    result
  }

  def getMove(): Char = {
    print("Choose command: 'd', 'u', 'l', 'r':")
    scala.io.StdIn.readChar()
  }

}