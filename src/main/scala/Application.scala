import bloxorz.Constants._
import bloxorz.GameStatus.{GAME_STATUS_LOSS, GAME_STATUS_WIN}
import bloxorz.{Bloxorz, Board, BoardMaker, Controller, MapEditor}

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
    val (status, message) = (controller.gameStatus.status, controller.gameStatus.message)
    if(status == GAME_STATUS_WIN || status == GAME_STATUS_LOSS){
      println(s"End of game ($status): $message")
      return
    }
    controller.printGame()
    playGame(controller)
  }

  def main2(args: Array[String]): Unit = {
    println("Hello from Bloxorz Applications")
    val matrix = BoardMaker.createBoard("src/main/resources/bloxorzmap")

    do {
      val controller: Controller = new Controller(
        new Board(matrix),
        new Bloxorz(BLOXORZ_UP, getInitPosition(matrix), (-1, -1))
      )
      controller.printGame()
      playGame(controller)
      print("Play again? (y/n):")
    } while (scala.io.StdIn.readChar() == 'y')

    println("Bye, bye...:)")

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





  def main(args: Array[String]): Unit = {
    println("Hello from Bloxorz Applications")
    val matrix = BoardMaker.createBoard("src/main/resources/bloxorzmap")

    do {
      val mapEditor: MapEditor = new MapEditor(
        new Board(matrix)
      )
      mapEditor.printGame()
      editMap(mapEditor)
      print("Play again? (y/n):")
    } while (scala.io.StdIn.readChar() == 'y')

    println("Bye, bye...:)")

  }

  @tailrec
  def editMap(mapEditor: MapEditor): Unit = {
    val move = getMove()
    if(move == 'q') {
      println("Bye, bye...:)")
      return
    }
    mapEditor.runCommand(move)
    mapEditor.printGame()
    editMap(mapEditor)
  }



}