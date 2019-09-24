/*
 * 循环控制，在循环中使用过滤，嵌套循环，生层器
 */
package jikewiki

import java.io.File

object CycleControl {
  def main(args: Array[String]): Unit = {
    val filesHere = new File(".").listFiles()

    simpleCycle(filesHere)
    filterCycle(filesHere)
    nestCycle(filesHere)
    println(createNewSetByCycle(filesHere))
  }

  def simpleCycle(filesHere: Array[File]) = {
    for (file <- filesHere){
      println(file)
    }

    filesHere.foreach(println)
  }

  def filterCycle(filesHere: Array[File]) = {
    for (file <- filesHere
      if file.isFile
      if file.getName.endsWith(".sbt")
    ) println(file)
  }

  def fileLines(file: java.io.File) = {
    scala.io.Source.fromFile(file).getLines().toList
  }

  def nestCycle(filesHere: Array[File]) = {
    for {
      file <- filesHere    if file.getName.endsWith(".sbt")
      line <- fileLines(file)
    } println(file + ":" + line.trim)
  }

  def createNewSetByCycle(filesHere: Array[File]) = {
    for (
      file <- filesHere
      if file.getName.endsWith(".sbt")
    ) yield file
  }

}
