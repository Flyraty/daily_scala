package jikewiki

import java.net.URL

import scala.io.Source

object either_type {

  def main(args: Array[String]): Unit = {

    getContent(new URL("http://google.com")) match {
      case Left(msg) => println(msg)
      case Right(source) => source.getLines.foreach(println)
    }

    // Either具有无偏性，因此调用集合上的方法，需要选择一种立场(Projection), 就是Either.left, Either.right

    val content: Either[String, Iterator[String]] = getContent(new URL("http://google.com")).right.map(_.getLines())

    val baidu = new URL("http://www.baidu.com")
    val github = new URL("https://github.com")
    val average_size = getContent(baidu).right.flatMap(a =>
      getContent(github).right.map(b =>
        (a.getLines().size + b.getLines().size) / 2))
    println(s"the average size of baidu and github request page is ${average_size.right.getOrElse(0)}")

    println(averageLineCountWontCompile(baidu, github))

    type Citizen = String
    case class BlackListedResource(url: URL, visitors: Set[Citizen])
    val blacklist = List(
      BlackListedResource(new URL("https://google.com"), Set("John Doe", "Johanna Doe")),
      BlackListedResource(new URL("http://yahoo.com"), Set.empty),
      BlackListedResource(new URL("https://maps.google.com"), Set("John Doe")),
      BlackListedResource(new URL("http://plus.google.com"), Set.empty)
    )

    val checkedBlackList: List[Either[URL, Set[Citizen]]] = blacklist.map(
      resource => if (resource.visitors.isEmpty) Left(resource.url)
      else Right(resource.visitors)
    )

    println(checkedBlackList)
  }

  def getContent(url: URL): Either[String, Source] = {
    if (url.getHost.contains("google"))
      Left("Request URL is blocked by GFW")
    else
      Right(Source.fromURL(url))
  }

  // 在for循环中使用Either容易出现的错误 TODO:There have a problem
  def averageLineCountWontCompile(url1: URL, url2: URL): Either[String, Int] = {
    for {
      source1 <- getContent(url1)
      source2 <- getContent(url2)
      lines1 = source1.getLines().size
      lines2 = source2.getLines().size

    } yield (lines1 + lines2) / 2
  }


}
