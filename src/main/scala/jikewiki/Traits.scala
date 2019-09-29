/*
 * Scala 中的接口 trait，解决单继承的问题
 * 瘦接口与胖接口
 * 用trait 和用继承 ？
 * Ordered Trait 对象排序接口
 */
package jikewiki

trait Plant {
  def color() = println("I am green")
}
trait Animal {
  def say() = println("I can say")
}

object Traits {

  class ThingThing

  class Thing extends ThingThing with Animal with Plant {
    override def toString: String = "How am i"
  }

  class Rational(n:Int, d:Int) extends Ordered[Rational] {
    val number  = n
    val demon = d
    override def compare(that: Rational): Int = (this.number * that.demon) - (this.demon * that.number)
  }

  def main(args: Array[String]): Unit = {
    new Thing().color()
    new Thing().say()
  }

}
