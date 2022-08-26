package bloxorz.mapeditor.operation

import bloxorz.mapeditor.{Cursor, EditingState}


object MoveLeft extends Operation {
  override def operation(editingState: EditingState): EditingState = {
    val cursor = editingState.cursor
    val newColVal = if (cursor.col - 1 >= 0) cursor.col - 1 else cursor.col
    new EditingState(editingState.board.clone(), new Cursor(editingState.cursor.row, newColVal))
  }

}