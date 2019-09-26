package jikewiki

object PatternMatch {

  def main(args: Array[String]): Unit = {

    // 简单匿名函数, python里面也有这样的map, filter高阶函数
    val songList = List("Di da", "Hu ha", "Wu la")
    songList.map(_.toUpperCase)

    val wordFrequencies = ("Di da", 7) :: ("Hu ha", 6) :: ("Wu la", 5) :: Nil

    // 使用模式匹配
    def wordFilter(words: Seq[(String, Int)]): Seq[String] = {

      words.filter { case (_, f) => f > 3 && f < 7 } map { case (w, _) => w }
    }

    // 不使用模式匹配

    def wordFilter_(words: Seq[(String, Int)]): Seq[String] = {
      words.filter(wf => wf._2 > 3 && wf._2 < 5).map(_._1)
    }

    // 使用偏函数来处理
    // TODO 需要再去了解下什么是偏函数, 将对象处理成需要的样子
    val pf: PartialFunction[(String, Int), String] = {
      case (word, f) if f > 3 && f < 7 => word

    }
    wordFrequencies.collect(pf)


  }

}
