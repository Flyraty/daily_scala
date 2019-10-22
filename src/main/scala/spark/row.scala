/**
 * Row
 */
package spark
import org.apache.spark.sql.Row

object row {

  def main(args: Array[String]): Unit = {
    val row = Row(1, "hello")
    println(row.get(1))  // emnn, 是否可以理解Row 就是一个collection对象，只是带有可选的schema
    println(row(1))
    println(row.getAs[Int](0))

    val row1 = Row.fromSeq(Seq(1, "hello"))
    val row2 = Row.fromTuple((0, "hello"))

    val mergeRow = Row.merge(row1, row2)

    println(mergeRow)

    Row.unapplySeq(row1)

    row1 match {
      case Row(key:Int, value: String) => key -> value
    }

    // 针对数据量较小的数据集对每行数据做PatternMatch 处理？规范化？

  }
}
