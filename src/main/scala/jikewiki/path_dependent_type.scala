/*
 * 路径依赖类型，场景: 在编译期检查来自不同类型的操作是否合理
 */
package jikewiki

object path_dependent_type {
  class A {
    class B
    var b: Option[B] = None
  }
  val a1 = new A
  val a2 = new A
  val b1 = new a1.B
  val b2 = new a2.B
  a1.b = Some(b1)
//  a2.b = Some(b1) //  error, b1类型为a1.b
}
