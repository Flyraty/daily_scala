/*
 * scala 实现字符串的全排列,
 */
package practice

object Permutations {
  def permutations(s: String):IndexedSeq[String] = {
    if (s == "") IndexedSeq("")
    else {
      val head = s.head
      val tail = s.tail
      for (l <- permutations(tail); i <- 0 to l.length; (a, b) = l.splitAt(i)) yield (a + head.toString + b)

    }
  }

  var i = 1

  def grad(a:Int, b:Int):Int = {
    val adjust:Int => Boolean = n => n % 2 == 0
    val ge:Int => Int => Int = n => m => if (n>m) n else m
    val le:Int => Int => Int = n => m => if (n>m) m else n

    if (a==b){
      a * i
    }else if (adjust(a) && adjust(b)){
      i = i *2
      grad(a / 2, b/2)
    }
    else {
      val min = le(a)(b)
      val max = ge(a)(b)
      val cha = max - min
      grad(min, cha)
    }

  }

  def main(args: Array[String]): Unit = {
    permutations("1234").foreach(println)
    println(grad(98, 63))
    println(grad(260, 104))
  }
}

