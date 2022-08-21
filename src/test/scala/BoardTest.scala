import bloxorz.common.Constants.{START, TERMINATION}
import bloxorz.common.{Board, BoardMaker}
import org.scalatest.funspec.AnyFunSpec

class BoardTest extends AnyFunSpec {
  val matrix: List[Array[Char]] = BoardMaker.createMapMatrix("src/test/resources/map")
  val board: Board = new Board(matrix)

  describe("When Board is properly created") {
    it("should have 'S' in the appropriate position") {
      val (startsym_row, startsym_col) = getFirstOcurrencePosition(matrix, START)
      assert(START == board.matrix.take(startsym_row + 1).last(startsym_col))
    }
    it("should have 'T' in the appropriate position") {
      val (termsym_row, termsym_col) = getFirstOcurrencePosition(matrix, TERMINATION)
      assert(TERMINATION == board.matrix.take(termsym_row + 1).last(termsym_col))
    }
  }

  def getFirstOcurrencePosition(matrix: List[Array[Char]], symbol: Char): (Int, Int) = {
    var result = (-1, -1)
    for ((row, i) <- matrix.view.zipWithIndex if row.contains(symbol)) result = (i, row.indexOf(symbol))
    result
  }

}
