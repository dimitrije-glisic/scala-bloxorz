package bloxorz

import scala.io.Source
import scala.util.{Success, Try, Using}

object BoardMaker {

  def createMapMatrix(fileName: String): List[Array[Char]] = {
    readLinesFromTextFile(fileName) match {
      case Success(lines) => lines filter (_.nonEmpty) map (_.toCharArray)
    }
  }


  def readLinesFromTextFile(filename: String): Try[List[String]] = {
    Using(Source.fromFile(filename)) { reader => reader.getLines().toList }
  }
}