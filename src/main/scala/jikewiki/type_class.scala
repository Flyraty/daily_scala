/*
 * 类型类, 建立一个trait, 在其伴生对象中定义隐式实现
 * TODO: 有些小迷离
 */
package jikewiki

object type_class {

  trait NumberLike[T] {
    def plus(x: T, y: T): T

    def divide(x: T, y: T): T

    def minus(x: T, y: T): T
  }

  object NumberLike {

    implicit object NumberLikeDouble extends NumberLike[Double] {
      def plus(x: Double, y: Double): Double = x + y

      def divide(x: Double, y: Double): Double = x / y

      def minus(x: Double, y: Double): Double = x - y
    }

    implicit object NumberLikeInt extends NumberLike[Int] {
      def plus(x: Int, y: Int): Int = x + y

      def divide(x: Int, y:Int): Int = x / y

      def minus(x:Int, y: Int):Int = x - y
    }

  }

//  object JodaImplicits {
//    import type_class.NumberLike
//    import org.joda.time.Duration
//    implicit object NumberLikeDuration extends NumberLike[Duration] {
//      def plus(x: Duration, y: Duration): Duration = x.plus(y)
//      def divide(x: Duration, y: Int): Duration = Duration.millis(x.getMillis / y)
//      def minus(x: Duration, y: Duration): Duration = x.minus(y)
//    }
//  }

  def main(args: Array[String]): Unit = {
    import type_class.NumberLike
//    def mean[T](xs: Vector[T])(implicit ev: NumberLike[T]): T = ev.divide(xs.reduce(ev.plus(_, _)), xs.size)
  }


}
