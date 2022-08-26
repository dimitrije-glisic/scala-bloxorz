package bloxorz.mapeditor

import bloxorz.common.Constants.{START, TERMINATION}
import bloxorz.mapeditor.operation._


class MapEditor(var editingState: EditingState) {


  def runCommand(command: Char): Unit = {

    if (command == 'l') {
      editingState = MoveLeft.operation(editingState)
    }

    if (command == 'r') {
      editingState = MoveRight.operation(editingState)
    }

    if (command == 'u') {
      editingState = MoveUp.operation(editingState)
    }

    if (command == 'd') {
      editingState = MoveDown.operation(editingState)
    }

    if (command == '-') {
      editingState = RemoveRegular.operation(editingState)
    }

    if (command == '+') {
      editingState = AddRegular.operation(editingState)
    }

    if (command == '.') {
      editingState = AddSpecial.operation(editingState)
    }

    if (command == 'S') {
      editingState = SwitchCurrentWith(START).operation(editingState)
    }

    if (command == 'T') {
      editingState = SwitchCurrentWith(TERMINATION).operation(editingState)
    }

    if(command == 'i') {
      editingState = Invert.operation(editingState)
    }

    if (command == 's') {
      editingState = SaveBoard.operation(editingState)
    }

  }

  def printGame(): Unit = {
    editingState.board.matrix.zipWithIndex.foreach(t => printRow(t))
  }

  def printRow(t: (Array[Char], Int)): Unit = {
    val (line, row) = t
    val _row: Array[Char] = Array.copyOf(line, line.length)
    if (row == editingState.cursor.row) {
      _row.update(editingState.cursor.col, '*')
    }
    println(_row.mkString)
  }

}
