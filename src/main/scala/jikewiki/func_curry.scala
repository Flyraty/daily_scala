/*
 * 函数部分应用和柯里化
 * 柯里化的依赖注入？
 * 在函数式编程语言中，调用函数的过程也叫做将函数应用(applying)到参数。当调用时传入了所有的参数，就叫做将函数完全应用(fully applied)到了所有参数。如果在调用时只传入了部分参数，返回的结果就是一个部分应用函数(Partially Applied Function)
 * 将一个接受多个参数的函数分解成一系列的函数，每个函数只接受一个参数，这个过程叫做柯里化。
 */
package jikewiki

import func_combination.{Email, EmailFilter, mails}

object func_curry {

  // 函数部分应用
  val divide =  (num:Double, n:Double) => num / n

  val halfOf = divide(_:Double, 2)

  type IntPairPred = (Int, Int) => Boolean

  val sizeChecker = (pred:IntPairPred, n:Int, email:Email) => pred(email.text.length, n)

  val gt:IntPairPred = _ > _
  val ge:IntPairPred = _ >= _
  val lt:IntPairPred = _ < _
  val le:IntPairPred = _ <= _
  val qe:IntPairPred = _ == _

  val minimumSize = sizeChecker(ge, _:Int, _:Email)

  val size20 = sizeChecker(_:IntPairPred, 20, _:Email)

  // 函数柯里化
  val divideCurry: Double => Double => Double = divide.curried  // curried uncurried

  val halfOf2 = divideCurry(_:Double)(2)

  def sizeConstraint(pred: IntPairPred)(n: Int)(email: Email): Boolean =
    pred(email.text.length, n)

  val minSize: Int => Email => Boolean = sizeConstraint(ge)

  val min20: Email => Boolean = sizeConstraint(ge)(20)



  def main(args: Array[String]): Unit = {

  }

}
