package bloxorz

import bloxorz.CommandUtil.isHorizontal
import bloxorz.Constants.{BLOXORZ_FLAT, BLOXORZ_UP, COMMAND_DOWN}

object RollDownCommand extends Command {
  override def execute(bloxorz: Bloxorz): Bloxorz = {
    if (bloxorz.position == BLOXORZ_UP) {
      val coord_1 = (bloxorz.coord_one._1 + 1, bloxorz.coord_one._2)
      val coord_2 = (bloxorz.coord_one._1 + 2, bloxorz.coord_one._2)
      new Bloxorz(BLOXORZ_FLAT, coord_1, coord_2)
    } else {
      var coord_1 = (bloxorz.coord_one._1 + 2, bloxorz.coord_one._2)
      var coord_2 = (-1, -1)
      val nextPos = nextPosition(bloxorz)
      if (nextPos == BLOXORZ_FLAT) {
        coord_1 = (bloxorz.coord_one._1 + 1, bloxorz.coord_one._2)
        coord_2 = (bloxorz.coord_two._1 + 1, bloxorz.coord_two._2)
      }
      new Bloxorz(nextPos, coord_1, coord_2)
    }
  }

  def nextPosition(bloxorz: Bloxorz): String = {
    if (isHorizontal(bloxorz)) {
      BLOXORZ_FLAT
    } else {
      BLOXORZ_UP
    }
  }
}
