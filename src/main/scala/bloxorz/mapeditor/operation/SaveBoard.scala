package bloxorz.mapeditor.operation

import bloxorz.common.Board
import bloxorz.common.Constants.GENERATED_MAPS_LOCATION
import bloxorz.mapeditor.EditingState

import java.io.{File, FileWriter}

object SaveBoard extends Operation {
  override def operation(editingState: EditingState): EditingState = {
    print("Do you want to save the new map? (y/n): ")
    if (scala.io.StdIn.readChar() == 'y') {
      print("Enter the name for the new Map: ")
      writeToFile(editingState.board, scala.io.StdIn.readLine())
    }
    editingState
  }

  def writeToFile(board: Board, fileName: String): File = {
    val file = new File(GENERATED_MAPS_LOCATION + fileName)
    file.createNewFile()
    val fileWriter = new FileWriter(file)
    for (t <- board.matrix.zipWithIndex) {
      val (row: Array[Char], index: Int) = (t._1, t._2)
      fileWriter.write(if (index < board.matrix_m - 1) row.appended('\n') else row)
    }
    fileWriter.close()
    file
  }

}
