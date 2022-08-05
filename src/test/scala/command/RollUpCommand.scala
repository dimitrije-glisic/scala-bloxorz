package command

import bloxorz.Bloxorz
import bloxorz.Constants.{BLOXORZ_FLAT, BLOXORZ_UP}
import bloxorz.command.RollUpCommand
import org.scalatest.funspec.AnyFunSpec


class RollUpCommand extends AnyFunSpec {

  val coord1: (Int, Int) = (3, 3)
  val bloxorzUP = new Bloxorz(BLOXORZ_UP, (3,3), (-1, -1))

  describe("Bloxorz is in the UP position") {
    it("should move to flat position") {
      assert(RollUpCommand.execute(bloxorzUP).position == BLOXORZ_FLAT)
    }
    it("should not change column value of field1") {
      assert(RollUpCommand.execute(bloxorzUP).coord_one._2 == coord1._2)
    }
    it("should decrease row value of field1 by 2") {
      assert(RollUpCommand.execute(bloxorzUP).coord_one._1 == coord1._1 - 2)
    }
    it("should set column value of field2 to that of field1") {
      assert(RollUpCommand.execute(bloxorzUP).coord_two._2 == coord1._2)
    }
    it("should set row value of field2 to the previous row value of field one decreased by 1") {
      assert(RollUpCommand.execute(bloxorzUP).coord_two._1 == coord1._1 - 1)
    }
  }

}
