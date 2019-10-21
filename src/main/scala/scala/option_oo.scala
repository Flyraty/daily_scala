package scala

object option_oo {

  val greeting: Option[String] = Some("Di da, di da")

  val nogreeting: Option[String] = None

  // getOrElse 方法为Option提供一个默认值

  case class User(
                   id: Int,
                   firstName: String,
                   lastName: Option[String]
                 )

  object UserRepository {
    private val users = Map(
      1 -> User(1, "John", Some("Doe")),
      2 -> User(2, "Johanna", Some("Doe"))
    )

    def findById(id: Int): Option[User] = users.get(id)

    def findAll = users.values
  }

  val user = User(1, "Di da", Some("Wula"))

  val name: Option[String] = Some(user.firstName)

  // getOrElse 方法提供一个默认值
  val user1 = User(2, "David", None)
  println(user1.lastName.getOrElse("default"))

  // 使用模式匹配
  val lastName = user1.lastName match {
    case Some(lastName) => lastName
    case None => "default"
  }

  println(lastName)

  // Option也支持集合的操作
  val names: List[Option[String]] = List(Some("Tony"), Some("Tome"), None)

  names.map(_.map(_.toUpperCase()))

  names.flatMap(xs => xs.map(_.toUpperCase()))
}
