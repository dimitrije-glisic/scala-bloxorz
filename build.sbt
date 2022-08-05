ThisBuild / scalaVersion := "2.13.8"
ThisBuild / organization := "com.etf.dglisic"

lazy val bloxorz = (project in file("."))
  .settings(
    name := "BloxorzScala"
  )