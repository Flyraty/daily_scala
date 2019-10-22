/**
 * DataFrameNaFunctions is used to work with missing data in a structured query (a DataFrame).
 * 处理数据缺失的方法 删除，填充，替换
 */
package spark
import org.apache.spark.sql.SparkSession

object na_func {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  def main(args: Array[String]): Unit = {

    val example = Seq(
      (1, 2, "1"),
      (2, 3, null),
      (3, 4, "3"),
      (4, 5, "4"),
      (null, null, "5"),
      (6, null, "6")
    ).toDF("id", "idPlus", "idString")

    // 删除 idString 为null 的数据
    example.na.drop("idString").show()

    // 填充各列null值为default
    example.na.fill(Map("id" -> -1, "idPlus" -> -1, "idString" -> "default")).show()

    // 替换, 如果replace null 值该什么办？emnn，用上面的呗
    example.na.replace(Seq("id", "idPlus"), Map(1 -> 666)).show()



  }

}
