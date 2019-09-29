/*
 * 使用trait来实现一个整数队列
 * get some error: Error:(30, 23) method put in class IntQueue is accessed from super. It may not be abstract unless it is overridden by a member declared `abstract' and `override'
 *   if (x >= 0) super.put(x)
 *  需要注意在中接口中复写 调用抽象类的抽象方法时，需要用 abstract override
 */
package Practice

import scala.collection.mutable.ArrayBuffer

abstract class IntQueue {
  def put(x: Int)

  def get(x: Int):Int
}

class BasicQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]

  override def get(x: Int): Int = buf.remove(0)

  override def put(x: Int): Unit = buf += x
}

trait Doubling extends IntQueue {
  abstract override def put(x: Int): Unit = {
    super.put(x * 2)
  }
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int): Unit = {
    if (x >= 0) super.put(x)
  }
}


object IntQueue {
  def main(args: Array[String]): Unit = {
    val queue = new BasicQueue with Doubling with Filtering
    queue.put(-10)
    queue.put(11)
    println(queue.get(0))
  }
}