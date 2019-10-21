/*
 * scala 中的导包机制
 * scala 默认import了3个包 顺序为java.lang._, scala._, preDef._  如果存在重复对象，并且没有明确指明import 的路径，则后面的定义会覆盖掉前面的定义
 * private[x]，protected[x]，可以直接用这个控制访问作用域，可以灵活的用这种方法来访问private
 * 包对象 eg:package object
 * TODO:访问控制
 */
package scala

class PPackage {

}


class p{
  class Super{
    protected def f() {
      println("f")
    }
  }
  class Sub extends Super{
    f()
  }
  class Other{
    // (new Super).f() error: f is not accessible
  }
}