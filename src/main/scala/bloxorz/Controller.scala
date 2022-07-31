package bloxorz

class Controller(val board: Board, var bloxorz: Bloxorz) {
  def playMove(move: Char): Unit = {
    if (move == 'u') {
      this.bloxorz = moveUp()
    }
    if (move == 'r') {
      this.bloxorz = moveRight()
    }
  }

  def moveUp(): Bloxorz = {
    if (bloxorz.position == "UP") {
      val coord_1 = (bloxorz.coord_one._1 - 1, bloxorz.coord_one._2)
      val coord_2 = (bloxorz.coord_one._1 - 2, bloxorz.coord_one._2)
      if (fieldOK(coord_1) && fieldOK(coord_2)) {
        new Bloxorz("FLAT", this.bloxorz.coord_one, coord_2)
      } else {
        println("Cannot play that move")
        this.bloxorz
      }
    } else {
      val coord_1 = (bloxorz.coord_one._1 - 1, bloxorz.coord_one._2)
      val coord_2 = (bloxorz.coord_two._1 - 1, bloxorz.coord_two._2)
      if (fieldOK(coord_1) && fieldOK(coord_2)) {
        new Bloxorz(position = "FLAT", coord_1, coord_2)
      } else {
        println("Cannot play that move")
        this.bloxorz
      }
    }
  }

  def moveRight(): Bloxorz = {
    if (bloxorz.position == "UP") {
      val coord_1 = (bloxorz.coord_one._1, bloxorz.coord_one._2 + 1)
      val coord_2 = (bloxorz.coord_one._1, bloxorz.coord_one._1 + 2)
      if (fieldOK(coord_1) && fieldOK(coord_2)) {
        new Bloxorz("FLAT", coord_1, coord_2)
      } else {
        println("Cannot play that move")
        this.bloxorz
      }
    } else {
      val coord_1 = (bloxorz.coord_one._1, bloxorz.coord_two._2 + 1)
      if (fieldOK(coord_1)) {
        new Bloxorz(position = "UP", coord_1, (-1, -1))
      } else {
        println("Cannot play that move")
        this.bloxorz
      }
    }
  }

  def fieldOK(coord_2: (Int, Int)): Boolean = {
    if (board.matrix.take(coord_2._1 + 1).last(coord_2._2) == 'o') {
      return true
    }
    false
  }

  def printGame(): Unit = {
    board.matrix.zipWithIndex.foreach(t => printWithBloxorz(t))
  }

  def printWithBloxorz(t: (Array[Char], Int)): Unit = {
    val (row, index) = t
    val _row: Array[Char] = Array.copyOf(row, row.length)
    if (index == bloxorz.coord_one._1) {
      _row.update(bloxorz.coord_one._2, '#')
    }
    if (index == bloxorz.coord_two._1) {
      _row.update(bloxorz.coord_two._2, '#')
    }
    println(_row.mkString)
  }

}
