package command

import bloxorz.common.Constants.{BLOXORZ_FLAT, BLOXORZ_UP}
import bloxorz.gamecontrol.Bloxorz
import bloxorz.gamecontrol.command.RollLeftCommand
import org.scalatest.funspec.AnyFunSpec

class RollLeftCommand extends AnyFunSpec {

  val coord1: (Int, Int) = (3, 3)
  val coord2: (Int, Int) = (-1, -1)

  describe("Bloxorz is in the UP position") {
    val bloxorzUp = new Bloxorz(BLOXORZ_UP, coord1, coord2)
    it("should move to flat position") {
      assert(RollLeftCommand.execute(bloxorzUp).position == BLOXORZ_FLAT)
    }
    it("should not change row value of field1") {
      assert(RollLeftCommand.execute(bloxorzUp).coord_one._1 == coord1._1)
    }
    it("should decrease column value of field1 by 2") {
      assert(RollLeftCommand.execute(bloxorzUp).coord_one._2 == coord1._2 - 2)
    }
    it("should set row value of field2 to that of field1") {
      assert(RollLeftCommand.execute(bloxorzUp).coord_two._1 == coord1._1)
    }
    it("should set column value of field2 to the previous row value of field1 decreased by 1") {
      assert(RollLeftCommand.execute(bloxorzUp).coord_two._2 == coord1._2 - 1)
    }
  }

  describe("Bloxorz is in the FLAT position") {
    describe("Bloxorz is currently horizontally FLAT") {
      val coord2 = (coord1._1, coord1._2 + 1)
      val bloxorzHorizontallyFlat = new Bloxorz(BLOXORZ_FLAT, coord1, coord2)
      it("should move to UP position") {
        assert(RollLeftCommand.execute(bloxorzHorizontallyFlat).position == BLOXORZ_UP)
      }
      it("should decrease field1 column value by 1") {
        assert(RollLeftCommand.execute(bloxorzHorizontallyFlat).coord_one._2 == coord1._2 - 1)
      }
      it("should preserve field1 row value") {
        assert(RollLeftCommand.execute(bloxorzHorizontallyFlat).coord_one._1 == coord1._1)
      }
      it("should set field2 coordinates to (-1,-1) since only field1 'touches' the board now") {
        assert(RollLeftCommand.execute(bloxorzHorizontallyFlat).coord_two._1 == -1)
        assert(RollLeftCommand.execute(bloxorzHorizontallyFlat).coord_two._2 == -1)
      }
      
    }

    describe("Bloxorz is currently vertically FLAT") {
      val coord2 = (coord1._1 + 1, coord1._2)
      val bloxorzVerticallyFlat = new Bloxorz(BLOXORZ_FLAT, coord1, coord2)
      it("should stay in FLAT position") {
        assert(RollLeftCommand.execute(bloxorzVerticallyFlat).position == BLOXORZ_FLAT)
      }
      it("should decrease column values for both field1 and field2") {
        assert(RollLeftCommand.execute(bloxorzVerticallyFlat).coord_one._2 == coord1._2 - 1)
        assert(RollLeftCommand.execute(bloxorzVerticallyFlat).coord_two._2 == coord2._2 - 1)
      }
      it("should preserve row values for both field1 and field2") {
        assert(RollLeftCommand.execute(bloxorzVerticallyFlat).coord_one._1 == coord1._1)
        assert(RollLeftCommand.execute(bloxorzVerticallyFlat).coord_two._1 == coord2._1)
      }
    }
  }

}
