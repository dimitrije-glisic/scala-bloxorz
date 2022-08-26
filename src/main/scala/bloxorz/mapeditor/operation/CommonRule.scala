package bloxorz.mapeditor.operation

import bloxorz.common.Board
import bloxorz.common.Constants.DASH
import bloxorz.mapeditor.{Cursor, EditingState}

object CommonRule {

  def isOnTheEdge(editingState: EditingState): Boolean = {
    val board = editingState.board
    val cursor = editingState.cursor

    val (i, j) = (cursor.row, cursor.col)
    val left = if (j - 1 >= 0) Board.getFieldValue(board.matrix, new Cursor(i, j - 1)) else DASH
    val right = if (j < board.matrix_n - 1) Board.getFieldValue(board.matrix, new Cursor(i, j + 1)) else DASH
    val above = if (i - 1 >= 0) Board.getFieldValue(board.matrix, new Cursor(i - 1, j)) else DASH
    val below = if (i + 1 < board.matrix_m) Board.getFieldValue(board.matrix, new Cursor(i + 1, j)) else DASH
    thereIsOneDash(left, right, above, below) && thereIsOneNonDash(left, right, above, below)
  }

  def thereIsOneDash(left: Char, right: Char, above: Char, below: Char): Boolean = {
    left == DASH || right == DASH || above == DASH || below == DASH
  }

  def thereIsOneNonDash(left: Char, right: Char, above: Char, below: Char): Boolean = {
    left != DASH || right != DASH || above != DASH || below != DASH
  }


}
