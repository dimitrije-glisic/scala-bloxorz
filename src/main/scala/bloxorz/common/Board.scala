package bloxorz.common

import bloxorz.common.Constants.START
import bloxorz.mapeditor.Cursor

object Board{
  def apply(matrix: List[Array[Char]]): Board = {
    new Board(matrix)
  }

  def getInitPosition(matrix: List[Array[Char]]): (Int, Int) = {
    var result = (-1, -1)
    for ((row, i) <- matrix.view.zipWithIndex if row.contains(START)) result = (i, row.indexOf(START))
    result
  }

  def getFieldValue(matrix: List[Array[Char]], cursor: Cursor):Char = {
    matrix.take(cursor.row + 1).last(cursor.col)
  }
}

class Board(var matrix: List[Array[Char]]) {

  val matrix_m:Int = {
    matrix.length
  }

  val matrix_n:Int = {
    matrix.take(1).last.length
  }

  def print(): Unit = {
    println()
    matrix.zipWithIndex.foreach(t => println(t._1.mkString))
    println()
  }

}