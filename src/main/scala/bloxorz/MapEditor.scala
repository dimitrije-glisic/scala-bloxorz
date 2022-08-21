package bloxorz

import bloxorz.Constants.{DASH, REGULAR_VALUE}


class MapEditor(var board: Board) {

  var cursor: Cursor = new Cursor(0, 0)

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

  def isFirstOrLastInTheRow(i: Int, j: Int): Boolean = {
    val c = board.matrix.take(i + 1).last(j)
    val row = board.matrix.take(i + 1).last

    //indexOf(Char) return first occurrence of a character in an Array
    j == 0 || row.indexOf(c) == j || j == board.matrix_n - 1 || row.lastIndexOf(c) == j
  }

  def isFirstOrLastInTheColumn(i: Int, j: Int): Boolean = {
    val (_, i_first) = board.matrix.zipWithIndex.find(t => t._1(j) != '-').get
    val (_, i_last) = board.matrix.zipWithIndex.findLast(t => t._1(j) != '-').get

    i == 0 || i_first == i || i == board.matrix_m - 1 || i_last == i
  }

  def isOnTheEdge(cursor: Cursor): Boolean = {
    isFirstOrLastInTheRow(cursor.row, cursor.col) || isFirstOrLastInTheColumn(cursor.row, cursor.col)
  }

  def removeOKField(cursor: Cursor): Unit = {
    if (board.getFieldValue(cursor) == Constants.REGULAR_VALUE && isOnTheEdge(cursor)) {
      print(s"\nYou are about to remove $REGULAR_VALUE. Continue? (y/n): ")
      if (scala.io.StdIn.readChar() == 'y') {
        this.board.matrix.take(cursor.row + 1).last(cursor.col) = '-'
        println(s"$REGULAR_VALUE Removed")
      } else {
        println(s"$REGULAR_VALUE Preserved")
      }
    }
  }

  def replaceCurrentFieldWithRegularField(cursor: Cursor): Unit = {
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

  def canBeReplacedWithRegular(cursor: Cursor): Boolean = {
    board.getFieldValue(cursor) == Constants.DASH &&  isEdgeDash(cursor)
  }

  def isEdgeDash(cursor: Cursor): Boolean = {
    isEdgeDashRowVise(cursor.row, cursor.col) || isEdgeDashColumnVise(cursor.row, cursor.col)
  }

  def isEdgeDashRowVise(i: Int, j: Int): Boolean = {
    val row = board.matrix.take(i + 1).last

    val left = if ((j - 1) > 0) row(j - 1) else DASH
    val right = if (j < board.matrix_n - 1) row(j + 1) else DASH

    left != DASH || right != DASH
  }

  def isEdgeDashColumnVise(i: Int, j: Int): Boolean = {
    val above = if (i - 1 > 0) board.getFieldValue(new Cursor(i - 1, j)) else DASH
    val below = if (i + 1 < board.matrix_m) board.getFieldValue(new Cursor(i + 1, j)) else DASH
    above != '-' || below != '-'
  }

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
      removeOKField(cursor)
    }

    if (command == '+') {
      replaceCurrentFieldWithRegularField(cursor)
    }

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
