package bloxorz

class Board(var matrix: List[Array[Char]]) {

  val matrix_m:Int = {
    matrix.length
  }

  val matrix_n:Int = {
    matrix.take(1).length
  }

  def getFieldValue(cursor: Cursor):Char = {
    matrix.take(cursor.row + 1).last(cursor.col)
  }


}