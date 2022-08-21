package command

import bloxorz.common.Constants.{BLOXORZ_FLAT, BLOXORZ_UP}
import bloxorz.gamecontrol.Bloxorz
import bloxorz.gamecontrol.command.RollUpCommand
import org.scalatest.funspec.AnyFunSpec

class RollUpCommand extends AnyFunSpec {

  val coord1: (Int, Int) = (3, 3)
  val coord2: (Int, Int) = (-1, -1)

  describe("Bloxorz is in the UP position") {
    val bloxorzUp = new Bloxorz(BLOXORZ_UP, coord1, coord2)
    it("should move to flat position") {
      assert(RollUpCommand.execute(bloxorzUp).position == BLOXORZ_FLAT)
    }
    it("should not change column value of field1") {
      assert(RollUpCommand.execute(bloxorzUp).coord_one._2 == coord1._2)
    }
    it("should decrease row value of field1 by 2") {
      assert(RollUpCommand.execute(bloxorzUp).coord_one._1 == coord1._1 - 2)
    }
    it("should set column value of field2 to that of field1") {
      assert(RollUpCommand.execute(bloxorzUp).coord_two._2 == coord1._2)
    }
    it("should set row value of field2 to the previous row value of field one decreased by 1") {
      assert(RollUpCommand.execute(bloxorzUp).coord_two._1 == coord1._1 - 1)
    }
  }

  describe("Bloxorz is in the FLAT position") {
    describe("Bloxorz is currently horizontally FLAT") {
      val coord2 = (coord1._1, coord1._2 + 1)
      val bloxorzHorizontallyFlat = new Bloxorz(BLOXORZ_FLAT, coord1, coord2)
      it("should remain in the flat position") {
        assert(RollUpCommand.execute(bloxorzHorizontallyFlat).position == BLOXORZ_FLAT)
      }
      it("should decrease row values for both field1 and field2") {
        assert(RollUpCommand.execute(bloxorzHorizontallyFlat).coord_one._1 == coord1._1 - 1)
        assert(RollUpCommand.execute(bloxorzHorizontallyFlat).coord_two._1 == coord2._1 - 1)
      }
      it("should preserve columns values for both field1 and field2") {
        assert(RollUpCommand.execute(bloxorzHorizontallyFlat).coord_one._2 == coord1._2)
        assert(RollUpCommand.execute(bloxorzHorizontallyFlat).coord_two._2 == coord2._2)
      }
    }

    describe("Bloxorz is currently vertically FLAT") {
      val coord2 = (coord1._1 + 1, coord1._2)
      val bloxorzVerticallyFlat = new Bloxorz(BLOXORZ_FLAT, coord1, coord2)
      it("should move to UP position") {
        assert(RollUpCommand.execute(bloxorzVerticallyFlat).position == BLOXORZ_UP)
      }
      it("should decrease field1 row value by 1") {
        assert(RollUpCommand.execute(bloxorzVerticallyFlat).coord_one._1 == coord1._1 - 1)
      }
      it("should preserve field1 column value") {
        assert(RollUpCommand.execute(bloxorzVerticallyFlat).coord_one._2 == coord1._2)
      }

      it("should set field2 coordinates to (-1,-1) since only field1 'touches' the board now") {
        assert(RollUpCommand.execute(bloxorzVerticallyFlat).coord_two._1 == -1)
        assert(RollUpCommand.execute(bloxorzVerticallyFlat).coord_two._2 == -1)
      }
    }
  }

}
