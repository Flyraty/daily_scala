/*
 * 简单的容错处理， try catch, throw, finally
 */
package jikewiki

import java.io.FileReader
import java.io.FileNotFoundException

class TryFault {

  def readFile() = {
    try {
      val f = new FileReader("source.txt")
    } catch {
      case ex: FileNotFoundException => println("File not Found")
    } finally {
      // f.close()
    }
  }

  def g1() = try 1 finally 2

  def g2():Int = try {return 1} finally {return 2}
}

object TryFault{
  def main(args: Array[String]): Unit = {

    val diuDiu =  new TryFault()
    diuDiu.readFile()

    // TODO 返回值不一样？
    println(diuDiu.g1())
    println(diuDiu.g2())

  }
}