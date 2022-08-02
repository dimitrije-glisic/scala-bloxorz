package bloxorz

import bloxorz.Controller._

object Controller {
  val DASH = '–'
  val OK_VALUE = 'o'
  val DOT = '.'
  val START = 'S'
  val TERMINATION = 'T'
  val BLOXORZ_MARKER = '#'

  val BLOXORZ_FLAT = "FLAT"
  val BLOXORZ_UP = "UP"

  val GAME_STATUS_IN_PROGRESS = "IN_PROGRESS"
  val GAME_STATUS_LOSS = "LOSS"
  val GAME_STATUS_WIN = "WIN"

  val COMMAND_UP = 'u'
  val COMMAND_DOWN = 'd'
  val COMMAND_RIGHT = 'r'
  val COMMAND_LEFT = 'l'
}

class Controller(val board: Board, var bloxorz: Bloxorz) {

  def playMove(move: Char): (String, String) = {
    if (move == COMMAND_UP) {
      this.bloxorz = moveUp()
    }
    if (move == COMMAND_DOWN) {
      this.bloxorz = moveDown()
    }
    if (move == COMMAND_RIGHT) {
      this.bloxorz = moveRight()
    }
    if (move == COMMAND_LEFT) {
      this.bloxorz = moveLeft()
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

  def moveUp(): Bloxorz = {
    if (bloxorz.position == BLOXORZ_UP) {
      val coord_1 = (bloxorz.coord_one._1 - 1, bloxorz.coord_one._2)
      val coord_2 = (bloxorz.coord_one._1 - 2, bloxorz.coord_one._2)
      new Bloxorz(BLOXORZ_FLAT, coord_1, coord_2)
    } else {
      var coord_1 = (bloxorz.coord_one._1 - 1, bloxorz.coord_one._2)
      var coord_2 = (-1, -1)
      val nextPos = nextPosition(bloxorz, COMMAND_UP)
      if (nextPos == BLOXORZ_FLAT) {
        coord_1 = (bloxorz.coord_one._1 - 1, bloxorz.coord_one._2)
        coord_2 = (bloxorz.coord_two._1 - 1, bloxorz.coord_two._2)
      }
      new Bloxorz(nextPos, coord_1, coord_2)
    }
  }

  def moveDown(): Bloxorz = {
    if (bloxorz.position == BLOXORZ_UP) {
      val coord_1 = (bloxorz.coord_one._1 + 1, bloxorz.coord_one._2)
      val coord_2 = (bloxorz.coord_one._1 + 2, bloxorz.coord_one._2)
      new Bloxorz(BLOXORZ_FLAT, coord_1, coord_2)
    } else {
      var coord_1 = (bloxorz.coord_one._1 + 2, bloxorz.coord_one._2)
      var coord_2 = (-1, -1)
      val nextPos = nextPosition(bloxorz, COMMAND_DOWN)
      if (nextPos == BLOXORZ_FLAT) {
        coord_1 = (bloxorz.coord_one._1 + 1, bloxorz.coord_one._2)
        coord_2 = (bloxorz.coord_two._1 + 1, bloxorz.coord_two._2)
      }
      new Bloxorz(nextPos, coord_1, coord_2)
    }
  }

  def moveRight(): Bloxorz = {
    if (bloxorz.position == BLOXORZ_UP) {
      val coord_1 = (bloxorz.coord_one._1, bloxorz.coord_one._2 + 1)
      val coord_2 = (bloxorz.coord_one._1, bloxorz.coord_one._2 + 2)
      new Bloxorz(BLOXORZ_FLAT, coord_1, coord_2)
    } else {
      var coord_1 = (bloxorz.coord_one._1, bloxorz.coord_one._2 + 2)
      var coord_2 = (-1, -1)
      val nextPos = nextPosition(bloxorz, COMMAND_RIGHT)
      if (nextPos == BLOXORZ_FLAT) {
        coord_1 = (bloxorz.coord_one._1, bloxorz.coord_two._2 + 1)
        coord_2 = (bloxorz.coord_two._1, bloxorz.coord_two._2 + 1)
      }
      new Bloxorz(nextPos, coord_1, coord_2)
    }
  }

  def moveLeft(): Bloxorz = {
    if (bloxorz.position == BLOXORZ_UP) {
      val coord_1 = (bloxorz.coord_one._1, bloxorz.coord_one._2 - 2)
      val coord_2 = (bloxorz.coord_one._1, bloxorz.coord_one._2 - 1)
      new Bloxorz(BLOXORZ_FLAT, coord_1, coord_2)

    } else {
      var coord_1 = (bloxorz.coord_one._1, bloxorz.coord_one._2 - 1)
      var coord_2 = (-1, -1)
      val nextPos = nextPosition(bloxorz, COMMAND_LEFT)
      if (nextPos == BLOXORZ_FLAT) {
        coord_1 = (bloxorz.coord_one._1, bloxorz.coord_one._2 - 1)
        coord_2 = (bloxorz.coord_two._1, bloxorz.coord_two._2 - 1)
      }
      new Bloxorz(nextPos, coord_1, coord_2)
    }
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


  def horizontal(bloxorz: Bloxorz): Boolean = {
    bloxorz.coord_one._1 == bloxorz.coord_two._1
  }

  def nextPosition(bloxorz: Bloxorz, command: Char): String = {
    if (horizontal(bloxorz)) {
      if (command == COMMAND_LEFT || command == COMMAND_RIGHT) {
        BLOXORZ_UP
      } else {
        BLOXORZ_FLAT
      }
    } else {
      if (command == COMMAND_UP || command == COMMAND_DOWN) {
        BLOXORZ_UP
      } else {
        BLOXORZ_FLAT
      }
    }
  }

}
