package mapeditor

import bloxorz.common.Constants.{DASH, REGULAR_VALUE}
import bloxorz.mapeditor.{Cursor, EditingState, MapEditor}
import bloxorz.common.{Board, BoardMaker}
import bloxorz.mapeditor.operation.RemoveRegular
import org.scalatest.funspec.AnyFunSpec


class MapEditorTest extends AnyFunSpec {

  val matrix: List[Array[Char]] = BoardMaker.createMapMatrix("src/test/resources/map")
  val board = new Board(matrix)

  describe("Removing Regular field") {
    it("Should NOT remove Regular field which is NOT on the edge") {
      val cursor = new Cursor(2, 3)
      val before = Board.getFieldValue(matrix, cursor)
      val editingState = new EditingState(board, cursor)
      val changedEditingState = RemoveRegular.operation(editingState)
      assert(Board.getFieldValue(changedEditingState.board.matrix, changedEditingState.cursor) == before)
    }
  }

  describe("Adding Regular field") {
    describe("Current field is not appropriate/on-the-edge") {
      it("Should NOT replace current field with a Regular one") {
        val cursor = new Cursor(1, 7)
        val before = Board.getFieldValue(matrix, cursor)
        val editingState = new EditingState(board, cursor)
        val changedEditingState = RemoveRegular.operation(editingState)
        assert(Board.getFieldValue(changedEditingState.board.matrix, changedEditingState.cursor) == before)
      }
    }
  }

}
