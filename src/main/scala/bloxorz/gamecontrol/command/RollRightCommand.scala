package bloxorz.gamecontrol.command

import bloxorz.common.Constants.{BLOXORZ_FLAT, BLOXORZ_UP}
import CommandUtil.isHorizontal
import bloxorz.gamecontrol.Bloxorz

object RollRightCommand extends Command {
  override def execute(bloxorz: Bloxorz): Bloxorz = {
    if (bloxorz.position == BLOXORZ_UP) {
      val coord_1 = (bloxorz.coord_one._1, bloxorz.coord_one._2 + 1)
      val coord_2 = (bloxorz.coord_one._1, bloxorz.coord_one._2 + 2)
      new Bloxorz(BLOXORZ_FLAT, coord_1, coord_2)
    } else {
      var coord_1 = (bloxorz.coord_one._1, bloxorz.coord_one._2 + 2)
      var coord_2 = (-1, -1)
      val nextPos = nextPosition(bloxorz)
      if (nextPos == BLOXORZ_FLAT) {
        coord_1 = (bloxorz.coord_one._1, bloxorz.coord_two._2 + 1)
        coord_2 = (bloxorz.coord_two._1, bloxorz.coord_two._2 + 1)
      }
      new Bloxorz(nextPos, coord_1, coord_2)
    }
  }

  def nextPosition(bloxorz: Bloxorz): String = {
    if (isHorizontal(bloxorz)) {
      BLOXORZ_UP
    } else {
      BLOXORZ_FLAT
    }
  }
}
