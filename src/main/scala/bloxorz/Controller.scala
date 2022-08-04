package bloxorz

import bloxorz.Constants._

class Controller(val board: Board, var bloxorz: Bloxorz) {

  def playMove(move: Char): (String, String) = {
    if (move == COMMAND_UP) {
      this.bloxorz = RollUpCommand.execute(bloxorz)
    }
    if (move == COMMAND_DOWN) {
      this.bloxorz = RollDownCommand.execute(bloxorz)
    }
    if (move == COMMAND_RIGHT) {
      this.bloxorz = RollRightCommand.execute(bloxorz)
    }
    if (move == COMMAND_LEFT) {
      this.bloxorz = RollLeftCommand.execute(bloxorz)
    }
    gameStatus()
  }

  def gameStatus(): (String, String) = {
    val fieldOne = board.matrix.take(bloxorz.coord_one._1 + 1).last(bloxorz.coord_one._2)
    val fieldTwo = if (bloxorz.coord_two._1 != -1) board.matrix.take(bloxorz.coord_two._1 + 1).last(bloxorz.coord_two._2) else OK_VALUE

    var status = GAME_STATUS_IN_PROGRESS
    var message = "OK"

    if (fieldOne == DASH || fieldTwo == DASH) {
      status = GAME_STATUS_LOSS
      message = "Bloxorz is out of board"
    }

    if (bloxorz.position == BLOXORZ_UP && fieldOne == DOT) {
      status = GAME_STATUS_LOSS
      message = "Bloxorz placed on '.' field"
    }

    if (bloxorz.position == BLOXORZ_UP && fieldOne == TERMINATION) {
      status = GAME_STATUS_WIN
      message = "You won!!! :)"
    }

    (status, message)
  }

  def printGame(): Unit = {
    board.matrix.zipWithIndex.foreach(t => printWithBloxorz(t))
  }

  def printWithBloxorz(t: (Array[Char], Int)): Unit = {
    val (row, index) = t
    val _row: Array[Char] = Array.copyOf(row, row.length)
    if (index == bloxorz.coord_one._1) {
      _row.update(bloxorz.coord_one._2, BLOXORZ_MARKER)
    }
    if (index == bloxorz.coord_two._1) {
      _row.update(bloxorz.coord_two._2, BLOXORZ_MARKER)
    }
    println(_row.mkString)
  }

}

