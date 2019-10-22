/**
 * 一些常见的函数组合子, 利用现有函数生成新的函数
 * type 设置类型别名，有时候这样写代码更有意义
 */
package scala



object func_combination {
  case class Email(subject: String, text: String, sender: String, recipient: String)

  type EmailFilter = Email => Boolean

  def newMailsForUser(mails: Seq[Email], f: EmailFilter) = mails.filter(f)

  // TODO: 需要熟悉一下这样的写法
  val sentByOneOf: Set[String] => EmailFilter =
    senders =>
      email => senders.contains(email.sender)

  val notSentByAnyOf: Set[String] => EmailFilter =
    senders =>
      email => !senders.contains(email.sender)

  val minimumSize: Int => EmailFilter =
    n =>
      email => email.text.length >= n

  val mails = Email(
    subject = "It's me again, your stalker friend!",
    text = "Hello my friend! How are you? your your qie ke nao",
    sender = "johndoe@example.com",
    recipient = "me@example.com") :: Nil

  val emailFilter: EmailFilter = sentByOneOf(Set("johndoe@example.com"))

  val emailSizeFilter: EmailFilter = minimumSize(100)

  // 函数组合来实现 minimumSize，没感觉有啥简化，清晰。适用于多种类似filter
  type SizeChecker = Int => Boolean
  val sizeCheckerFunc: SizeChecker => EmailFilter =
    f =>
      email => f(email.text.length)

  val minimumSizeComb: Int => EmailFilter =
    n => sizeCheckerFunc(_ >= n)

  // complement(a) 得到a的补  f.compose(g) = f(g())  f.andThen(g) = g(f())

  def complement[A](predicate: A => Boolean) = (a: A) => !predicate(a)

  val notSentByAnyOfComb = sentByOneOf andThen (g => complement(g))

  // 组合多个邮件过滤器
  // The exists method takes a predicate function and will use it to find the first element in the collection which matches the predicate.
  def any[A](predicates: (A => Boolean)*): A => Boolean =
    a => predicates.exists(pred => pred(a))

  def none[A](predicates: (A => Boolean)*) = complement(any(predicates: _*))

  // TODO: view 视图惰性处理 https://docs.scala-lang.org/zh-cn/overviews/collections/views.html
  def every[A](predicates: (A => Boolean)*) = none(predicates.view.map(complement(_)): _*)

  val filter: EmailFilter = every(
    notSentByAnyOf(Set("johndoe@example.com")),
    minimumSize(100)
  )

  // 流水线组合
  val addMissingSubject = (email: Email) =>
    if (email.subject.isEmpty) email.copy(subject = "No subject")
    else email
  val checkSpelling = (email: Email) =>
    email.copy(text = email.text.replaceAll("your", "you're"))

  val pipeline = Function.chain(Seq(checkSpelling, addMissingSubject))

  def main(args: Array[String]): Unit = {
    println(newMailsForUser(mails, emailFilter))
    println(newMailsForUser(mails, emailSizeFilter))

    println(pipeline(mails.head))

  }


}
