package bloxorz.gamecontrol.command

import bloxorz.gamecontrol.Bloxorz

case object CommandUtil {

  def isHorizontal(bloxorz: Bloxorz): Boolean = {
    bloxorz.coord_one._1 == bloxorz.coord_two._1
  }

}
