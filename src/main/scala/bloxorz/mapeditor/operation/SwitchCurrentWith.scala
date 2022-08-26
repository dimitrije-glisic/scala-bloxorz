package bloxorz.mapeditor.operation

import bloxorz.common.Board
import bloxorz.mapeditor.{Cursor, EditingState}

object SwitchCurrentWith {
  def apply(uniqueSymbol: Char): SwitchCurrentWith = {
    new SwitchCurrentWith(uniqueSymbol)
  }
}

class SwitchCurrentWith(val uniqueSymbol: Char) extends Operation {
  override def operation(editingState: EditingState): EditingState = {
    val (uniqueSymRow, uniqueSymCol) = Board.getFirstPosition(editingState.board.matrix, uniqueSymbol)

    val newBoard = editingState.board.clone()
    newBoard.matrix.take(editingState.cursor.row + 1).last(editingState.cursor.col) = uniqueSymbol
    newBoard.matrix.take(uniqueSymRow + 1).last(uniqueSymCol) = 'o'

    new EditingState(newBoard, new Cursor(editingState.cursor.row, editingState.cursor.col))
  }
}
