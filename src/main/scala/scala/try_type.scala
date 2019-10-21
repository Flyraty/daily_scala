package scala

import java.io.{FileNotFoundException, InputStream}
import java.net.{MalformedURLException, URL}

import scala.io.Source
import scala.util.{Failure, Success, Try}

object try_type {

  def main(args: Array[String]): Unit = {

    val youngCustomer = Customer(15)

    try {
      buyCigarette(youngCustomer)
      println("Yo, here are your cancer sticks! Happy smokin'!")
    } catch {
      case UnderAgeException(msg) => msg
    }

    val invalidURL = parseURL("invalid URL")
    println(invalidURL) // Failure(java.net.MalformedURLException: no protocol: invalid URL) 返回Try类型的子类型Failure

    val validURL = parseURL("https://github.com")
    println(validURL) // Success(https://github.com) 返回Try类型的子类型Success
    println(validURL.map(_.getHost))

    val url = parseURL(Console.readLine("URL: ")) getOrElse new URL("https://github.com")
    println(url)

    // Try 类型同Option一样，都支持一些在集合维度上的操作
    parseURL("https://github.com").map(_.getProtocol)
    parseURL("invalid URL").map(_.getProtocol)

    val result = inputStreamFromURL("https://github.com")
    println(result)

    // 通过flatMap 来对嵌套try类型解包
    val response = getInputStreamFromURL("https://github.com")
    println(response)

    getURLContent("https://github.com") match {
      case Success(lines) => lines.foreach(println)
      case Failure(ex) => println(s"Problem occurred in getContent ${ex.getMessage}")
    }

    // recover的使用
    val content = getURLContent("https://github.com") recover {
      case e: MalformedURLException => Iterator("Plz input a valid URL")
      case e: FileNotFoundException => Iterator("Request Page not exists")
      case _ => Iterator("An unExcept error occurred, We are so sorry")
    }

    println(content.get.foreach(println))
  }

  case class Customer(age: Int)

  case class UnderAgeException(message: String) extends Exception(message)

  class Cigarettes

  def buyCigarette(customer: Customer): Cigarettes = {
    if (customer.age < 16) {
      throw UnderAgeException(s"Customer must be older than 16 but was $customer.age")
    }
    else new Cigarettes
  }

  // Try[A] 则表示一种计算： 这种计算在成功的情况下，返回类型为 A 的值，在出错的情况下，返回 Throwable。
  def parseURL(url: String): Try[URL] = Try(new URL(url))

  def inputStreamFromURL(url: String): Try[Try[Try[InputStream]]] = {
    parseURL(url).map(u => Try(u.openConnection()).map(conn => Try(conn.getInputStream)))

  }

  def getInputStreamFromURL(url: String): Try[InputStream] = {
    parseURL(url).flatMap(u => Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream)))

  }

  // for 语句中的try
  def getURLContent(url: String): Try[Iterator[String]] =
    for {
      url <- parseURL(url)
      conn <- Try(url.openConnection())
      is <- Try(conn.getInputStream)
      source = Source.fromInputStream(is)
    } yield source.getLines()


}
