/*
 * scala 实现字符串的全排列
 */
package Practice

object Permutations {
  def permutations(s: String):IndexedSeq[String] = {
    if (s == "") IndexedSeq("")
    else {
      val head = s.head
      val tail = s.tail
      for (l <- permutations(tail); i <- 0 to l.length; (a, b) = l.splitAt(i)) yield (a + head.toString + b)
    }

  }

  def main(args: Array[String]): Unit = {
    permutations("1234").foreach(println)
  }
}

