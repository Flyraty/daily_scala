/*
 * scala 中是如何处理和替代 break, continue 循环控制,
 * emnn, 用递归
 * breakable
 */
package scala

import java.io._
import scala.util.control.Breaks._


object cycle_control {

  def main(args: Array[String]): Unit = {

    val files = new File(".").listFiles()

    findFile(files)
    println(files(findFileRecursive(0)))
    useBreakable()


    def findFileRecursive(i:Int): Int = {
      if (i >= files.length) -1
      else if (files(i).getName.startsWith("./.g")) findFileRecursive(i)
      else if (files(i).getName.endsWith(".sbt")) i
      else findFileRecursive(i+1)
    }

    def findFile(files: Array[File]) = {
      var i = 0
      var foundIt = false
      while (i<files.length && !foundIt) {
        if (!files(i).getName.startsWith("./.g")) {
          if (files(i).getName.endsWith(".sbt")) {
            foundIt = true
            println(files(i).getName)
          }
        }
        i += 1
      }
    }

    def useBreakable() = {
      val in = new BufferedReader(new InputStreamReader(System.in))
      breakable {
        while(true) {
          println("? ")
          if(in.readLine()=="") break
        }
      }
    }
  }


}
