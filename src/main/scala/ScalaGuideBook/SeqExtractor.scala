/*
 * 序列提取器, 简单来说就是将对象中的所需要的内容提取出来
 */

package ScalaGuideBook

object SeqExtractor {

  object GivenNames {
    def unapplySeq(name: String): Option[Seq[String]] = {
      val names = name.trim.split(" ")
      if (names.forall(_.isEmpty)) None
      else Some(names)
    }
  }

  object MultiGivenNames {
    def unapplySeq(name: String): Option[(String, String, Seq[String])] = {
      val names = name.trim.split(" ")
      if (names.forall(_.isEmpty)) None
      else Some(names.last, names.head, names.drop(1).dropRight(1))
    }
  }

  def greetWithFirstName(name: String) = name match {
    case GivenNames(firstName, _*) => println("Good Noon " + firstName)
    case _ => println("Plz make sure to fill your name")
  }

  def helloWithName(name: String): Unit = name match {
    case MultiGivenNames(lasname, firstname, _*) => println("hello" + firstname + lasname + " dida dida")
    case _ => println("Plz make sure to fill your full name")
  }

  def main(args: Array[String]): Unit = {

    val dida = 3 :: 6 :: 12 :: Nil

    dida match {
      case List(a, b, c) => a + b + c
      case List(a, b) => a * b
      case _ => 0
    }

    // 序列提取器，
    greetWithFirstName("Di da")

    helloWithName("Di da")

  }


}
