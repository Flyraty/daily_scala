/*
 * scala 提取器, 对象解包。 三种unapply 方法签名. 中缀表达式表示提取器
 */


package scala

trait User {
  def name: String
}

trait MultiUser {
  def name: String

  def score: Int
}

object Extractor {


  class FreeUser(val name: String) extends User

  class PremiumUser(val name: String) extends User

  class MultiFreeUser(val name: String, val score: Int, val upgradeProbability: Double) extends MultiUser

  class MultiPremiumUser(val name: String, val score: Int) extends MultiUser


  object FreeUser {
    def unapply(user: FreeUser): Option[String] = Some(user.name)
  }

  object PremiumUser {
    def unapply(user: PremiumUser): Option[String] = Some(user.name)
  }

  object MultiFreeUser {
    def unapply(user: MultiFreeUser): Option[(String, Int, Double)] = Some((user.name, user.score, user.upgradeProbability))
  }

  object MultiPremiumUser {
    def unapply(user: MultiPremiumUser): Option[(String, Int)] = Some((user.name, user.score))
  }

  // 提取器不一定非要在类的伴生对象中定义, 调用这种提取器的时候可以用一些语法糖
  object PremiumCandidate {
    def unapply(user: MultiFreeUser): Boolean = user.upgradeProbability > 0.75
  }

  def didabooleanExtractor(user: MultiFreeUser): Unit = {
    println("Hello " + user.name)
    println("This is a boolean extractor")
  }

  def didaOtherExtractor(user: MultiUser): Unit = {
    println("di da, di da")
  }

  def main(args: Array[String]): Unit = {
    val user: User = new PremiumUser("Di Da")
    user match {
      case FreeUser(name) => "Hello" + name
      case PremiumUser(name) => "Welcaome back! Dear " + name

    }

    val multiUser: MultiUser = new MultiFreeUser("Di Da", 3000, 0.7d)

    multiUser match {
      case MultiFreeUser(name, _, p) =>
        if (p > 0.75) s"$name, What can i do for you?"
        else "Hello" + name

      case MultiPremiumUser(name, _) =>
        s"Welcoem back, dear $name"
    }

    val booleanUser: MultiUser = new MultiFreeUser("Di Da", 3000, 0.8d)

    /*
     * Scala 的模式匹配也允许将提取器匹配成功的实例绑定到一个变量上， 这个变量有着与提取器所接受的对象相同的类型。这通过 @ 操作符实现
     */
    booleanUser match {
      case multiFreeUser@PremiumCandidate() => didabooleanExtractor(multiFreeUser)
      case _ => didaOtherExtractor(booleanUser)
    }


    // 用中缀表达式表示提取器, 可以直接点到 #::的unapply方法实现
    val dida = 58 #:: 43 #:: 93 #:: Stream.empty

    dida match {
      case first #:: second #:: _ => first - second
      case _ => -1
    }


  }
}


