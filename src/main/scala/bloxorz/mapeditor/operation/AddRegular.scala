package bloxorz.mapeditor.operation

import bloxorz.common.Board
import bloxorz.common.Constants.{DASH, DOT, REGULAR_VALUE}
import bloxorz.config.ConfigConstants
import bloxorz.mapeditor.operation.CommonRule.isOnTheEdge
import bloxorz.mapeditor.{Cursor, EditingState}

object AddRegular extends Operation {

  override def operation(editingState: EditingState): EditingState = {
    if (canBeReplacedWithRegular(editingState)) {
      if (ConfigConstants.SAFE_MODE_ENABLED) {
        changeStateSafely(editingState)
      } else {
        changeState(editingState)
      }
    } else {
      editingState
    }
  }

  def canBeReplacedWithRegular(editingState: EditingState): Boolean = {
    List(DASH, DOT).contains(Board.getFieldValue(editingState.board.matrix, editingState.cursor)) && isOnTheEdge(editingState)
  }

  def changeState(editingState: EditingState): EditingState = {
    val newBoard = editingState.board.clone()
    newBoard.matrix.take(editingState.cursor.row + 1).last(editingState.cursor.col) = REGULAR_VALUE
    println(s"$REGULAR_VALUE Added")
    new EditingState(newBoard, new Cursor(editingState.cursor.row, editingState.cursor.col))
  }

  def changeStateSafely(editingState: EditingState): EditingState = {
    println(s"\nYou are about to replace current field with a '$REGULAR_VALUE'. Continue? (y/n): ")
    if (scala.io.StdIn.readChar() == 'y') {
      changeState(editingState)
    } else {
      println(s"$REGULAR_VALUE not Added")
      editingState
    }
  }

}
