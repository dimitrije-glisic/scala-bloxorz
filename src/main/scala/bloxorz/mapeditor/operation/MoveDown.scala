package bloxorz.mapeditor.operation

import bloxorz.mapeditor.{Cursor, EditingState}


object MoveDown extends Operation {
  override def operation(editingState: EditingState): EditingState = {
    val board = editingState.board
    val cursor = editingState.cursor

    val newRowVal = if (cursor.row + 1 < board.matrix_m) cursor.row + 1 else cursor.row
    new EditingState(board.clone(), new Cursor(newRowVal, cursor.col))
  }

}