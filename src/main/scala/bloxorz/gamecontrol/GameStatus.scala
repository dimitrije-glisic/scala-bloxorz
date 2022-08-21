package bloxorz.gamecontrol

object GameStatus {
  val GAME_STATUS_IN_PROGRESS = "IN_PROGRESS"
  val GAME_STATUS_LOSS = "LOSS"
  val GAME_STATUS_WIN = "WIN"

  val GAME_STATUS_IN_PROGRESS_MESSAGE = "Game is in progress"
  val GAME_STATUS_LOSS_MESSAGE_OUT = "Bloxorz is out of board"
  val GAME_STATUS_LOSS_MESSAGE_DOT_FIELD = "Bloxorz placed on '.' field"
  val GAME_STATUS_WIN_MESSAGE = "You won!!! :)"
}

class GameStatus(val status: String, val message: String) {
}
