package mapeditor

import bloxorz.common.Constants.{DASH, REGULAR_VALUE, START, TERMINATION}
import bloxorz.common.{Board, BoardMaker}
import bloxorz.mapeditor.operation._
import bloxorz.mapeditor.{Cursor, EditingState}
import org.scalatest.funspec.AnyFunSpec

class CompositeOperationTest extends AnyFunSpec {

  val matrix: List[Array[Char]] = BoardMaker.createMapMatrix("src/test/resources/map")
  val board = new Board(matrix)

  describe("Move right twice") {
    val editingState = new EditingState(board, new Cursor(0, 0))
    val composite = new CompositeOperation().add(MoveRight).add(MoveRight)
    val changedEditingState = composite.operation(editingState)
    it("Should increase cursor col by 2") {
      assert(changedEditingState.cursor.col == editingState.cursor.col + 2)
    }
    it("Should keep cursor row unchanged") {
      assert(changedEditingState.cursor.row == editingState.cursor.row)
    }
  }


  describe("Move right twice then down 3 times and left once") {
    val editingState = new EditingState(board, new Cursor(0, 0))
    val composite = new CompositeOperation().add(MoveRight).add(MoveRight).add(MoveDown).add(MoveDown).add(MoveDown).add(MoveLeft)
    val changedEditingState = composite.operation(editingState)
    it("Should increase cursor col by 1") {
      assert(changedEditingState.cursor.col == editingState.cursor.col + 1)
    }
    it("Should increase cursor row by 3") {
      assert(changedEditingState.cursor.row == editingState.cursor.row + 3)
    }
  }


  describe("Move To Edge and Add Regular") {
    val editingState = new EditingState(board, new Cursor(0, 0))
    val composite = new CompositeOperation()
      .add(MoveDown)
      .add(MoveRight).add(MoveRight).add(MoveRight).add(MoveRight).add(MoveRight)
      .add(AddRegular)
    val changedEditingState = composite.operation(editingState)

    it("Should have row = 1 and col = 5") {
      assert(changedEditingState.cursor.row == 1)
      assert(changedEditingState.cursor.col == 5)

    }

    it(s"Should replace $DASH with $REGULAR_VALUE") {
      assert(Board.getFieldValue(editingState.board.matrix, changedEditingState.cursor) == DASH)
      assert(Board.getFieldValue(changedEditingState.board.matrix, changedEditingState.cursor) == REGULAR_VALUE)
    }

  }


  describe("Move To Edge and Remove Regular") {
    val editingState = new EditingState(board, new Cursor(0, 0))
    val composite = new CompositeOperation()
      .add(MoveDown).add(MoveDown).add(MoveDown)
      .add(MoveRight).add(MoveRight).add(MoveRight).add(MoveRight)
      .add(MoveUp)
      .add(MoveLeft).add(MoveLeft)
      .add(MoveRight).add(MoveRight).add(MoveRight)
      .add(RemoveRegular)
    val changedEditingState = composite.operation(editingState)

    it("Should have row = 2 and col = 5") {
      assert(changedEditingState.cursor.row == 2)
      assert(changedEditingState.cursor.col == 5)

    }
    it(s"Should replace $REGULAR_VALUE with $DASH") {
      assert(Board.getFieldValue(editingState.board.matrix, changedEditingState.cursor) == REGULAR_VALUE)
      assert(Board.getFieldValue(changedEditingState.board.matrix, changedEditingState.cursor) == DASH)
    }

  }


  describe("Move to middle and Add Special") {

  }


  describe(s"Move and switch with '$START' symbol") {

  }


  describe(s"Move and switch with '$TERMINATION' symbol") {

  }

}
