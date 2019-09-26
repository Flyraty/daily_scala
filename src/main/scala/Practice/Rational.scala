/*
 * 实现 FP 风格的有理数表示类
 * this, that 的使用，this 在访问成员变量时可以省略掉，在访问对象本身时不能省略
 * that 不能直接访问类成员变量
 * scala 中成员变量的私有化
 * 类的重载
 * 类对象的符号定义，和普通函数一样
 * 递归函数式必须指明函数返回类型
 * 隐式类型转换
 */


package Practice

class Rational(n: Int, d: Int) {
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  val number = n/g
  val denom = d/g

  override def toString: String = n + "/" + d

  def this(n: Int) = this(n, 1)

  def add(that: Rational) = {
    new Rational(
      number * that.denom + that.number * denom,
      denom * that.denom
    )
  }

  def +(that: Rational) = {
    new Rational(
      number * that.denom + that.number * denom,
      denom * that.denom
    )
  }

  def +(i:Int) = {
    new Rational(
      number + i *denom, denom
    )
  }

  def sub(that: Rational) = new Rational(
    number * that.denom - that.number * denom,
    denom * that.denom
  )

  def lessThan(that:Rational) = denom * that.number < that.number * denom

  def max(that:Rational) = if(lessThan(that)) that else this

  private def gcd(a:Int, b:Int) :Int =  {
    if (b==0) a else gcd(b, a%b)
  }

}

object Rational {
  def main(args: Array[String]): Unit = {
    implicit def intToRational(x: Int) = new Rational(x)

    println(new Rational(1, 3))

    val a = new Rational(1, 3)
    val b = new Rational(2, 7)
    val c = 2
    println(a + b)
    println(a + c)

    println(c + a)

  }

}
