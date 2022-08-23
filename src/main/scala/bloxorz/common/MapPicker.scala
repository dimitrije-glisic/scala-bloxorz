package bloxorz.common

import bloxorz.common.Constants.MAPS_LOCATION

import java.io.File

object MapPicker {

  def isValidMapName(mapNames: scala.collection.Set[File], input: String): Boolean = {
    mapNames.map(path => path.getName).contains(input)
  }

  def choseMapOfNAvailable(n: Int): String = {
    val nmaps = listNAvailableMaps(n)
    printMaps(nmaps)
    print("Enter the name of map you like: ")

    val input = scala.io.StdIn.readLine()

    if (isValidMapName(nmaps.keySet, input)) {
      nmaps.keySet.filter(file => file.getName == input).mkString
    } else {
      MAPS_LOCATION + "map"
    }
  }

  def printMaps(maps: Map[File, Board]): Unit = {
    maps.foreach(map => {
      val path: File = map._1
      val board: Board = map._2
      println(path.getName)
      board.print()
      println()
    })
  }

  def listNAvailableMaps(n: Int): Map[File, Board] = {
    println("Available maps:\n")
    new java.io.File(MAPS_LOCATION).listFiles
      .toList
      .take(n)
      .map(path => path -> new Board(BoardMaker.createMapMatrix(path.getPath)))
      .toMap
  }

}
