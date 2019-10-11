/*
 * Scala 中类层次关系
 * Any <- 值类，引用类 <- Null <- Nothing  Nothing是所有类的子类， Null 是所有引用类的子类
 * == equals isEqual === sameElements ne eq
 */
package jikewiki

object class_level {

  def error(message:String) :Nothing =
    throw new RuntimeException(message)

  def divide(x:Int, y:Int):Int = if (y!=0) x/y else  error("can`t divide by zero") // Nothing 是 Int 的子类,没毛病

  def main(args: Array[String]): Unit = {

  }

}
