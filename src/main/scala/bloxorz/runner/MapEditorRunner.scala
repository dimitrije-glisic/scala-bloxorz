package bloxorz.runner

import bloxorz.common.UserInputUtil.getMove
import bloxorz.mapeditor.MapEditor
import bloxorz.common.{Board, BoardMaker, MapPicker}

import scala.annotation.tailrec

object MapEditorRunner {

  def run(): Unit = {
    val mapEditor = init()
    editMap(mapEditor)
  }

  def init(): MapEditor = {
    println("Available maps for editing:")
    val board = new Board(BoardMaker.createMapMatrix(MapPicker.choseMapOfNAvailable(3)))
    val mapEditor = new MapEditor(board)
    println("Editing started")
    mapEditor.printGame()
    mapEditor
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
