/**
 * scala 隐式转换，出现编译类型错误的一种修复机制
 * 隐式转换在代码重构中的作用，无须更改现有接口代码
 * 隐式转换类型，隐式视图，隐式类
 */
package scala

class implicit_oo {

  // implicit 扩展 TCP 鉴权请求
  class AuthParam {}
  trait InAuthService {
    // 服务鉴权接口
    def auth(p: AuthParam)
  }

  class AuthService extends InAuthService {
    def auth(p: AuthParam): Unit = println("默认鉴权服务请求")
  }

  class TcpAuthMsg {}

  object tcpAuthService {
    implicit class TcpAuth(authService: AuthService) {
      def auth(p:TcpAuthMsg) =  println("TCP鉴权服务请求")
    }

  }

  // 隐式参数, 如果参数没有定义且被 implicit 修饰，会在当前作用域内查找相同类型的隐式参数

  object implicitParams {
    def foo(amount:Float)(implicit rate: Float) = println(amount * rate)
  }

  // 隐式类型转换 编译器在当前作用域查找类型转换方法，对数据类型进行转换。

  object implicitTypeTra {
    implicit def doubleToInt(i: Double) = i.toInt
    def typeTra(i: Int) = println(i)
  }

  // 隐式方法调用，当对象没有定义方法时，会通过隐式转换查找定义了该方法的类型，并转换为对应类型
  class Horse {
    def drinking() = println("animal drinking")
  }

  class Crow {}

  object implicitFunc {
    implicit def extendsSkill(crow: Crow) = new Horse()
  }

  // 隐式类  隐式类指的是用 implicit 关键字修饰的类。在对应的作用域内，带有这个关键字的类的主构造函数可用于隐式转换。
  object implicitClass {
    implicit class Parrot(aninal: Crow) {
      def say() = println("luo luo luo, i am the crow can speak")
    }
  }

  def test(): Unit = {
    import tcpAuthService._
    import implicitParams._
    import implicitTypeTra._
    import implicitFunc._
    import implicitClass._

    val p = new TcpAuthMsg()
    val authService = new AuthService()
    authService.auth(p)

    implicit val rat = 0.3f
    foo(10)

    typeTra(3.5)

    new Crow().drinking()

    new Crow().say()
  }


}

object implicit_oo {
  def main(args: Array[String]): Unit = {
    new implicit_oo().test()
  }
}


