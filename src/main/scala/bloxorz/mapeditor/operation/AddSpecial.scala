package bloxorz.mapeditor.operation

import bloxorz.common.Constants.DOT
import bloxorz.common.{Board, Constants}
import bloxorz.config.ConfigConstants
import bloxorz.mapeditor.{Cursor, EditingState}


object AddSpecial extends Operation {
  override def operation(editingState: EditingState): EditingState = {
    if (canBeReplacedWithSpecial(editingState)) {
      if (ConfigConstants.SAFE_MODE_ENABLED) {
        changeStateSafely(editingState)
      } else {
        changeState(editingState)
      }
    } else {
      editingState
    }
  }

  def canBeReplacedWithSpecial(editingState: EditingState): Boolean = {
    Board.getFieldValue(editingState.board.matrix, editingState.cursor) == Constants.REGULAR_VALUE
  }

  def changeStateSafely(editingState: EditingState): EditingState = {
    println(s"\nYou are about to replace current field with a '$DOT'. Continue? (y/n): ")
    if (scala.io.StdIn.readChar() == 'y') {
      println(s"$DOT Added")
      changeState(editingState)
    } else {
      println(s"$DOT not Added")
      editingState
    }
  }

  def changeState(editingState: EditingState): EditingState = {
    val newBoard = editingState.board.clone()
    newBoard.matrix.take(editingState.cursor.row + 1).last(editingState.cursor.col) = DOT
    println(s"$DOT Added")
    new EditingState(newBoard, new Cursor(editingState.cursor.row, editingState.cursor.col))
  }

}
