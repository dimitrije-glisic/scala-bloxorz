package bloxorz

import bloxorz.UserInputUtil.getMove

import scala.annotation.tailrec

object MapEditorRunner {

  def run(): Unit = {
    val matrix = BoardMaker.createMapMatrix("src/main/resources/bloxorzmap")
    editMap(new MapEditor(new Board(matrix)))
  }

  @tailrec
  def editMap(mapEditor: MapEditor): Unit = {
    val move: Char = getMove
    if (move == 'q') {
      println("Editing finished")
      return
    }
    mapEditor.runCommand(move)
    mapEditor.printGame()
    editMap(mapEditor)
  }

}
