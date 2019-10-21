/*
 * 类和对象的成员函数, 局部函数
 * 函数字面量  函数也是一个对象，可以赋值给其他变量，也可以用来传参
 * 部分应用函数
 * 闭包，
 * 函数参数: 可变参数，命名参数，缺省参数
 * 尾递归优化
 */
package scala

import scala.io.Source

class function_oo {
  def processFiles(file:String, width:Int) = {
    val source = Source.fromFile(file)
    for (line <- source.getLines()) {
      processLines(file, width, line)
    }
  }

  private def processLines(file:String, width:Int, line:String) = {
    if (line.length > width){
      println(s"${file}:${line.trim()}")
    }
  }

  val f: Int => Int = (x:Int) => x+1

  val seq:Seq[Int] = Seq(1, 2, 3, 4, 5, 6, 7)



  // 可变参数
  def mutable(args: String *) = for (arg <- args) println(arg)

  // 命名参数
  def candy(sweet:String, love:Boolean) = {}

  val arr:Array[String] = Array("1", "2", "3", "4")

  // 缺省参数
  def log(i:Int= 1) = println(i)

  def test() = {
    println(seq.filter(_ %2==0).map(f))
    println(seq.map(f))

    mutable("hello", "world")
    mutable(arr: _*)  // 注意这里的使用，不能直接传Array

    candy(love=true, sweet = "crow candy") // 命名参数可以改变参数的顺序

    log()

    log(10)
  }


}

object function_oo {

  def main(args: Array[String]): Unit = {

    new function_oo().test()

  }

  /* 局部函数 */
  def processFiles(file:String, width:Int) = {
    def processLines(line:String) = {
      if (line.length > width){
        println(s"${file}:${line.trim()}")
      }
    }
    val source = Source.fromFile(file)
    for (line <- source.getLines()) {
      processLines(line)
    }

  }

}
