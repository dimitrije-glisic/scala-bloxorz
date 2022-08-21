package bloxorz.runner

import bloxorz.common.UserInputUtil.getMove
import bloxorz.mapeditor.MapEditor
import bloxorz.common.{Board, BoardMaker}

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
