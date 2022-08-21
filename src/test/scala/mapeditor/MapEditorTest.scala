package mapeditor

import bloxorz.Constants.{DASH, REGULAR_VALUE}
import bloxorz.{Board, BoardMaker, Cursor, MapEditor}
import org.scalatest.funspec.AnyFunSpec


class MapEditorTest extends AnyFunSpec {

  val matrix: List[Array[Char]] = BoardMaker.createBoard("src/test/resources/bloxorzmap.txt")
  val board = new Board(matrix)
  val mapEditor = new MapEditor(board)

  describe("Removing Regular field") {
    it("Should NOT remove OK field which is not on the edge") {
      val cursor = new Cursor(2, 3)
      val before = mapEditor.board.getFieldValue(cursor)
      mapEditor.removeOKField(cursor)
      assert(mapEditor.board.getFieldValue(cursor) == before)
    }
  }

  describe("Replacing current field, which is not appropriate/on-the-edge, with a Regular one") {
    it("Should NOT replace current field with a Regular one") {
      val cursor = new Cursor(1, 7)
      val before = mapEditor.board.getFieldValue(cursor)
      assert(before == DASH)
      mapEditor.replaceCurrentFieldWithRegularField(cursor)
      assert(mapEditor.board.getFieldValue(cursor) == before)
    }
  }

}
