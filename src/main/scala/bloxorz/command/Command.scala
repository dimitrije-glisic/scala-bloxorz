package bloxorz.command

import bloxorz.Bloxorz

trait Command {

  def execute(bloxorz: Bloxorz): Bloxorz

}
