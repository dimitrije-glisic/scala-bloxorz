package bloxorz

import bloxorz.Constants.{DASH, REGULAR_VALUE}

class MapEditor(val board: Board) {

  var cursor: Cursor = new Cursor(0, 0)

  def runCommand(command: Char): Unit = {

    if (command == 'l') {
      cursor = moveLeft(cursor)
    }

    if (command == 'r') {
      cursor = moveRight(cursor)
    }

    if (command == 'u') {
      cursor = moveUp(cursor)
    }

    if (command == 'd') {
      cursor = moveDown(cursor)
    }

    if (command == '-') {
      removeRegularField(cursor)
    }

    if (command == '+') {
      addRegularField(cursor)
    }
  }

  def moveLeft(cursor: Cursor): Cursor = {
    new Cursor(cursor.row, cursor.col - 1)
  }

  def moveRight(cursor: Cursor): Cursor = {
    new Cursor(cursor.row, cursor.col + 1)
  }

  def moveUp(cursor: Cursor): Cursor = {
    new Cursor(cursor.row - 1, cursor.col)
  }

  def moveDown(cursor: Cursor): Cursor = {
    new Cursor(cursor.row + 1, cursor.col)
  }

  def removeRegularField(cursor: Cursor): Unit = {
    if (canBeReplacedWithDash(cursor)) {
      print(s"\nYou are about to remove $REGULAR_VALUE. Continue? (y/n): ")
      if (scala.io.StdIn.readChar() == 'y') {
        this.board.matrix.take(cursor.row + 1).last(cursor.col) = '-'
        println(s"$REGULAR_VALUE Removed")
      } else {
        println(s"$REGULAR_VALUE Preserved")
      }
    }
  }

  def addRegularField(cursor: Cursor): Unit = {
    if (canBeReplacedWithRegular(cursor)) {
      println(s"\nYou are about to add $REGULAR_VALUE. Continue? (y/n): ")
      if (scala.io.StdIn.readChar() == 'y') {
        this.board.matrix.take(cursor.row + 1).last(cursor.col) = REGULAR_VALUE
        println(s"$REGULAR_VALUE Added")
      } else {
        println(s"$REGULAR_VALUE not Added")
      }
    }
  }

  def canBeReplacedWithDash(cursor: Cursor): Boolean = {
    board.getFieldValue(cursor) == Constants.REGULAR_VALUE && isOnTheEdge(cursor)
  }

  def canBeReplacedWithRegular(cursor: Cursor): Boolean = {
    board.getFieldValue(cursor) == Constants.DASH && isOnTheEdge(cursor)
  }

  def isOnTheEdge(cursor: Cursor): Boolean = {
    val (i, j) = (cursor.row, cursor.col)
    val left = if (j - 1 > 0) board.getFieldValue(new Cursor(i, j - 1)) else DASH
    val right = if (j < board.matrix_n - 1) board.getFieldValue(new Cursor(i, j + 1)) else DASH
    val above = if (i - 1 > 0) board.getFieldValue(new Cursor(i - 1, j)) else DASH
    val below = if (i + 1 < board.matrix_m) board.getFieldValue(new Cursor(i + 1, j)) else DASH
    thereIsOneDash(left, right, above, below) && thereIsOneNonDash(left, right, above, below)
  }

  def thereIsOneDash(left: Char, right: Char, above: Char, below: Char): Boolean = {
    left == DASH || right == DASH || above == DASH || below == DASH
  }

  def thereIsOneNonDash(left: Char, right: Char, above: Char, below: Char): Boolean = {
    left != DASH || right != DASH || above != DASH || below != DASH
  }

  def printGame(): Unit = {
    board.matrix.zipWithIndex.foreach(t => printRow(t))
  }

  def printRow(t: (Array[Char], Int)): Unit = {
    val (line, row) = t
    val _row: Array[Char] = Array.copyOf(line, line.length)
    if (row == cursor.row) {
      _row.update(cursor.col, '*')
    }
    println(_row.mkString)
  }

}
