/*
 * 布局元素类
 * 抽象方法只能在抽象类中定义，抽象类不能实例化
 * 统一访问原则： uniform access principle， 就是说客户代码不应受通过字段还是方法实现属性的决定的影响。 常见的现象是scala 调用很多方法不用()
 * 可以使用 val 来重载无参数的方法。
 * override 用于重载父类的非抽象成员。在基类中新添加方法时要注意是否有的子类已经实现了同名方法
 * final 修饰的类和方法使得子类不能重写
 *
 */
package practice

abstract class Element {
  def contents: Array[String]

  def height = contents.length

  def width = if (height == 0) 0 else contents(0).length

  def above(that: Element): Element = {
    val this1=this widen that.width
    val that1=that widen this.width
    Element.elem(this1.contents ++ that1.contents)
  }

  def beside(that: Element): Element = {
    val this1=this heighten that.height
    val that1=that heighten this.height
    Element.elem(
      for {
        (line1, line2) <- this1.contents zip that1.contents // 不等长序列zip,多余的元素会被去掉
      } yield line1 + line2
    )
  }

  def widen(w: Int): Element = {
    if (w<=width) this
    else{
      val left = Element.elem(' ', (w - width) / 2, height)
      val right = Element.elem(' ', w - width - left.width, height)
      left beside this beside right
    }
  }

  def heighten(h: Int): Element = {
    if (h<=height) this
    else {
      val top = Element.elem(' ', width, (h - height) / 2)
      val behind = Element.elem(' ', width, h - height - top.height)
      top above this above behind
    }
  }

  override def toString: String = contents mkString "\n"
}


object Element {

  private class ArrayElement(conts: Array[String]) extends Element {
    def contents: Array[String] = conts

  }

  private class LineElement(s: String) extends ArrayElement(Array(s)) {
    override def width = s.length

    override def height = 1
  }

  private class UniformElement(ch: Char,
                               override val width: Int,
                               override val height: Int
                              ) extends Element {
    private val line = ch.toString * width

    def contents = Array.fill(height)(line)
  }

  // elem factory
  def elem(contents: Array[String]): Element = new ArrayElement(contents)

  def elem(chr: Char, width: Int, height: Int): Element = new UniformElement(chr, width, height)

  def elem(line: String): Element = new LineElement(line)

}


// TODO 想不出来，有点笨。。。
object Spiral {
  import Element._
  val space = elem (" ")
  val corner = elem ("+")
  def spiral(nEdges:Int, direction:Int): Element = {
    if(nEdges==1)
      elem("+")
    else{
      val sp=spiral(nEdges -1, (direction +3) % 4)
      def verticalBar = elem ('|',1, sp.height)
      def horizontalBar = elem('-',sp.width,1)
      if(direction==0)
        (corner beside horizontalBar) above (sp beside space)
      else if (direction ==1)
        (sp above space) beside ( corner above verticalBar)
      else if(direction ==2 )
        (space beside sp) above (horizontalBar beside corner)
      else
        (verticalBar above corner) beside (space above sp)
    }
  }
  def main(args:Array[String]) {
    val nSides=5
    println(spiral(nSides, 0))
    val a = corner beside space beside corner
    val b = corner above space above corner
    println(a above b beside b)
  }
}