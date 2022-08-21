package mapeditor

import bloxorz.common.Constants.{DASH, REGULAR_VALUE}
import bloxorz.mapeditor.{Cursor, MapEditor}
import bloxorz.common.{Board, BoardMaker}
import org.scalatest.funspec.AnyFunSpec


class MapEditorTest extends AnyFunSpec {

  val matrix: List[Array[Char]] = BoardMaker.createMapMatrix("src/test/resources/bloxorzmap")
  val board = new Board(matrix)
  val mapEditor = new MapEditor(board)

  describe("Removing Regular field") {
    it("Should NOT remove Regular field which is NOT on the edge") {
      val cursor = new Cursor(2, 3)
      val before = mapEditor.board.getFieldValue(cursor)
      mapEditor.removeRegularField(cursor)
      assert(mapEditor.board.getFieldValue(cursor) == before)
    }
  }

  describe("Adding Regular field") {
    describe("Current field is not appropriate/on-the-edge") {
      it("Should NOT replace current field with a Regular one") {
        val cursor = new Cursor(1, 7)
        val before = mapEditor.board.getFieldValue(cursor)
        assert(before == DASH)
        mapEditor.addRegularField(cursor)
        assert(mapEditor.board.getFieldValue(cursor) == before)
      }
    }
  }

}
