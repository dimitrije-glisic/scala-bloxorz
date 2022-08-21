package bloxorz.gamecontrol.command

import bloxorz.gamecontrol.Bloxorz

trait Command {

  def execute(bloxorz: Bloxorz): Bloxorz

}
