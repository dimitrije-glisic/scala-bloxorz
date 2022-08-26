package bloxorz.mapeditor.operation

import bloxorz.common.Board
import bloxorz.common.Constants.{START, TERMINATION}
import bloxorz.mapeditor.{Cursor, EditingState}


object Invert extends Operation {
  override def operation(editingState: EditingState): EditingState = {
    val (startSymRow, startSymCol) = Board.getFirstPosition(editingState.board.matrix, START)
    val (termSymRow, termSymCol) = Board.getFirstPosition(editingState.board.matrix, TERMINATION)

    val newBoard = editingState.board.clone()
    newBoard.matrix.take(startSymRow + 1).last(startSymCol) = TERMINATION
    newBoard.matrix.take(termSymRow + 1).last(termSymCol) = START

    new EditingState(newBoard, new Cursor(editingState.cursor.row, editingState.cursor.col))
  }
}
