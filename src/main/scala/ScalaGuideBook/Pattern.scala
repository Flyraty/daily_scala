/*
 * 模式在scala中的使用
 */

package ScalaGuideBook

object Pattern {

  case class Player(name: String, score: Int)

  def printMessage(player: Player) = player match {

    case Player(_, score) if score > 100000 =>
      "Congratulations %s".format(_)

    case Player(name, _) =>
      "see you later %s".format(name)

  }

  def gameResult(): (String, Int) = ("Dida", 100)

  def gameResults(): Seq[(String, Int)] = ("Tom", 5000) :: ("Jack", 6000) :: ("David", 7000) :: Nil

  def handleResults = for {
    (name, score) <- gameResults()
    if score > 5000
  } yield name

  def main(args: Array[String]): Unit = {

    // 在模式匹配中用println打印会产生副作用, println本身就是一种副作用
    println(printMessage(Player("Di Da", 200000)))

    // 与Python中的解包非常类似
    val (name, score) = gameResult()
    println("name:%s score:%s".format(name, score))

    println(handleResults)

    val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil

    // TODO 没搞懂
    for {
      list@head :: _ <- lists
    } yield lists.size
  }

}
