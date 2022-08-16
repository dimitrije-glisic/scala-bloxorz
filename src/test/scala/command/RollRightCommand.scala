package command

import bloxorz.Bloxorz
import bloxorz.Constants.{BLOXORZ_FLAT, BLOXORZ_UP}
import bloxorz.command.RollRightCommand
import org.scalatest.funspec.AnyFunSpec

class RollRightCommand extends AnyFunSpec {

  val coord1: (Int, Int) = (3, 3)
  val coord2: (Int, Int) = (-1, -1)

  describe("Bloxorz is in the UP position") {
    val bloxorzUp = new Bloxorz(BLOXORZ_UP, coord1, coord2)
    it("should move to flat position") {
      assert(RollRightCommand.execute(bloxorzUp).position == BLOXORZ_FLAT)
    }
    it("should not change row value of field1") {
      assert(RollRightCommand.execute(bloxorzUp).coord_one._1 == coord1._1)
    }
    it("should increase column value of field1 by 1") {
      assert(RollRightCommand.execute(bloxorzUp).coord_one._2 == coord1._2 + 1)
    }
    it("should set row value of field2 to that of field1") {
      assert(RollRightCommand.execute(bloxorzUp).coord_two._1 == coord1._1)
    }
    it("should set column value of field2 to the previous row value of field1 increased by 2") {
      assert(RollRightCommand.execute(bloxorzUp).coord_two._2 == coord1._2 + 2)
    }
  }

  describe("Bloxorz is in the FLAT position") {
    describe("Bloxorz is currently horizontally FLAT") {
      val coord2 = (coord1._1, coord1._2 + 1)
      val bloxorzHorizontallyFlat = new Bloxorz(BLOXORZ_FLAT, coord1, coord2)
      it("should move to UP position") {
        assert(RollRightCommand.execute(bloxorzHorizontallyFlat).position == BLOXORZ_UP)
      }
      it("should increase field1 column value by 2") {
        assert(RollRightCommand.execute(bloxorzHorizontallyFlat).coord_one._2 == coord1._2 + 2)
      }
      it("should preserve field1 row value") {
        assert(RollRightCommand.execute(bloxorzHorizontallyFlat).coord_one._1 == coord1._1)
      }
      it("should set field2 coordinates to (-1,-1) since only field1 'touches' the board now") {
        assert(RollRightCommand.execute(bloxorzHorizontallyFlat).coord_two._1 == -1)
        assert(RollRightCommand.execute(bloxorzHorizontallyFlat).coord_two._2 == -1)
      }
    }

    describe("Bloxorz is currently vertically FLAT") {
      val coord2 = (coord1._1 + 1, coord1._2)
      val bloxorzVerticallyFlat = new Bloxorz(BLOXORZ_FLAT, coord1, coord2)
      it("should stay in FLAT position") {
        assert(RollRightCommand.execute(bloxorzVerticallyFlat).position == BLOXORZ_FLAT)
      }
      it("should increase column values for both field1 and field2") {
        assert(RollRightCommand.execute(bloxorzVerticallyFlat).coord_one._2 == coord1._2 + 1)
        assert(RollRightCommand.execute(bloxorzVerticallyFlat).coord_two._2 == coord2._2 + 1)
      }
      it("should preserve row values for both field1 and field2") {
        assert(RollRightCommand.execute(bloxorzVerticallyFlat).coord_one._1 == coord1._1)
        assert(RollRightCommand.execute(bloxorzVerticallyFlat).coord_two._1 == coord2._1)
      }
    }
  }

}
