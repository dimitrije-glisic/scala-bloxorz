package bloxorz.gamecontrol

import bloxorz.common.Constants._
import bloxorz.gamecontrol.GameStatus._
import bloxorz.common.Board
import bloxorz.gamecontrol.command.{RollDownCommand, RollLeftCommand, RollRightCommand, RollUpCommand}

class GameController(val board: Board, var bloxorz: Bloxorz) {

  var gameStatus: GameStatus = new GameStatus(GAME_STATUS_IN_PROGRESS, GAME_STATUS_IN_PROGRESS_MESSAGE)

  def playMove(move: Char): Unit = {
    if (gameStatus.status != GAME_STATUS_IN_PROGRESS)
      throw new IllegalStateException(s"You cannot play the move - Illegal Game State: ${gameStatus.status} / ${gameStatus.message}")

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
    if(move == 'q'){
      //do nothing
    }

    this.gameStatus = updateGameStatus()
  }

  def updateGameStatus(): GameStatus = {
    val fieldOne = board.matrix.take(bloxorz.coord_one._1 + 1).last(bloxorz.coord_one._2)
    val fieldTwo = if (bloxorz.coord_two._1 != -1) board.matrix.take(bloxorz.coord_two._1 + 1).last(bloxorz.coord_two._2) else REGULAR_VALUE

    var status = GAME_STATUS_IN_PROGRESS
    var message = GAME_STATUS_IN_PROGRESS_MESSAGE

    if (fieldOne == DASH || fieldTwo == DASH) {
      status = GAME_STATUS_LOSS
      message = GAME_STATUS_LOSS_MESSAGE_OUT
    }

    if (bloxorz.position == BLOXORZ_UP && fieldOne == DOT) {
      status = GAME_STATUS_LOSS
      message = GAME_STATUS_LOSS_MESSAGE_DOT_FIELD
    }

    if (bloxorz.position == BLOXORZ_UP && fieldOne == TERMINATION) {
      status = GAME_STATUS_WIN
      message = GAME_STATUS_WIN_MESSAGE
    }

    new GameStatus(status, message)
  }

  def printGame(): Unit = {
    println("\n")
    board.matrix.zipWithIndex.foreach(t => printWithBloxorz(t))
    println("\n")
  }

  def printWithBloxorz(t: (Array[Char], Int)): Unit = {
    val (line, row) = t
    val _row: Array[Char] = Array.copyOf(line, line.length)
    if (row == bloxorz.coord_one._1) {
      _row.update(bloxorz.coord_one._2, BLOXORZ_MARKER)
    }
    if (row == bloxorz.coord_two._1) {
      _row.update(bloxorz.coord_two._2, BLOXORZ_MARKER)
    }
    println(_row.mkString)
  }

}

