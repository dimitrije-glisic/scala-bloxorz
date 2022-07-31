import bloxorz.{Bloxorz, Board, BoardMaker, Controller}

object Application {

  def main(args: Array[String]): Unit = {
    println("Hello from Bloxorz Applications")
    val matrix = BoardMaker.createBoard("src/main/resources/bloxorzmap.txt")

    val controller: Controller = new Controller(
      new Board(matrix),
      new Bloxorz("UP", getInitPosition(matrix), (-1, -1))
    )
    controller.printGame()
    controller.playMove(getMove())
    controller.printGame()
    controller.playMove(getMove())
    controller.printGame()
    controller.playMove(getMove())
    controller.printGame()
    controller.playMove(getMove())
    controller.printGame()

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