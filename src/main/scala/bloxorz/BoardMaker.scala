package bloxorz

import scala.io.Source

object BoardMaker {

  def createBoard(fileName: String): List[Array[Char]] = {
    Source.fromFile(fileName).getLines.toList map (_.toCharArray)
  }


}
