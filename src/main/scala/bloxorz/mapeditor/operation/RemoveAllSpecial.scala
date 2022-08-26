package bloxorz.mapeditor.operation

import bloxorz.common.Board
import bloxorz.common.Constants.{DOT, REGULAR_VALUE}
import bloxorz.mapeditor.{Cursor, EditingState}

object RemoveAllSpecial extends Operation {

  override def operation(editingState: EditingState): EditingState = {
    val newBoard = editingState.board.clone()
    for (t1 <- editingState.board.matrix.zipWithIndex) {
      val line = t1._1
      val i = t1._2
      for (t2 <- line.zipWithIndex) {
        val c = t2._1
        val j = t2._2
        if (Board.getFieldValue(editingState.board.matrix, new Cursor(i, j)) == DOT) {
          newBoard.matrix.take(i + 1).last(j) = REGULAR_VALUE
        }
      }
    }
    new EditingState(newBoard, new Cursor(editingState.cursor.row, editingState.cursor.col))
  }

}
