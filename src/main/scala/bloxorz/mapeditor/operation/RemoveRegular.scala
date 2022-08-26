package bloxorz.mapeditor.operation

import bloxorz.common.Constants.{DASH, REGULAR_VALUE}
import bloxorz.common.{Board, Constants}
import bloxorz.config.ConfigConstants
import bloxorz.mapeditor.operation.CommonRule.isOnTheEdge
import bloxorz.mapeditor.{Cursor, EditingState}

object RemoveRegular extends Operation {

  override def operation(editingState: EditingState): EditingState = {
    if (canBeReplacedWithDash(editingState)) {
      if (ConfigConstants.SAFE_MODE_ENABLED) {
        changeStateSafely(editingState)
      } else {
        changeState(editingState)
      }
    } else {
      editingState
    }
  }

  def canBeReplacedWithDash(editingState: EditingState): Boolean = {
    Board.getFieldValue(editingState.board.matrix, editingState.cursor) == Constants.REGULAR_VALUE && isOnTheEdge(editingState)
  }

  def changeStateSafely(editingState: EditingState): EditingState = {
    print(s"\nYou are about to remove $REGULAR_VALUE. Continue? (y/n): ")
    if (scala.io.StdIn.readChar() == 'y') {
      println(s"$REGULAR_VALUE Removed")
      changeState(editingState)
    } else {
      println(s"$REGULAR_VALUE Preserved")
      editingState
    }
  }

  def changeState(editingState: EditingState): EditingState = {
    val newBoard = editingState.board.clone()
    newBoard.matrix.take(editingState.cursor.row + 1).last(editingState.cursor.col) = DASH
    println(s"$REGULAR_VALUE Removed")
    new EditingState(newBoard, new Cursor(editingState.cursor.row, editingState.cursor.col))
  }

}
