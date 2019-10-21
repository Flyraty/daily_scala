/*
 * FP 风格来简化代码
 * 传名参数的意义，非传名参数会先计算结果
 */
package scala

import java.io._

object simplify_code {

  private def files = new File("./").listFiles()

  def filesMatching(matcher: (String) => Boolean) = {
    for (file <- files if matcher(file.getName))
      yield file
  }

  def filesEnding(query: String) = filesMatching(_.endsWith(query))

  def filesContains(query: String) = filesMatching(_.contains(query))

  // 使用函数柯里化来简化代码

  def withPrintWriter(file: File)(op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  def main(args: Array[String]): Unit = {
    filesEnding(".sbt")
    filesContains(".sbt")

    val file = new File("Cycle.scala")

    withPrintWriter(file) {
      writer => writer.println(new java.util.Date)
    }

  }
}
