package bloxorz.mapeditor.operation

import bloxorz.mapeditor.{Cursor, EditingState}


object MoveRight extends Operation {
  override def operation(editingState: EditingState): EditingState = {
    val board = editingState.board
    val cursor = editingState.cursor

    val newColVal = if (cursor.col + 1 < board.matrix_n) cursor.col + 1 else cursor.col
    new EditingState(board.clone(), new Cursor(editingState.cursor.row, newColVal))
  }

}