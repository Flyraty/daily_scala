/*
 * 窗口函数
 *
 */
package spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions._

object window_aggregate {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  /*
   * 详细可以查看源码，类型 org.apache.spark.sql.expressions.WindowSpec
   * val window:WindowSpec = Window.partitionBy("id").orderBy("year")
   */

  case class Salary(depName: String, empNo: Long, salary: Long)
  val empSalary = Seq(
    Salary("sales", 1, 5000),
    Salary("personnel", 2, 3900),
    Salary("sales", 3, 4800),
    Salary("sales", 4, 4800),
    Salary("personnel", 5, 3500),
    Salary("develop", 7, 4200),
    Salary("develop", 8, 6000),
    Salary("develop", 9, 4500),
    Salary("develop", 10, 5200),
    Salary("develop", 11, 5200)).toDS

  def main(args: Array[String]): Unit = {
    /*
     * window Rank functions
     * rank
     * dense_rank
     * row_number
     */
    val nameWindowDesc = Window.partitionBy("depName").orderBy($"salary".desc)
    empSalary.withColumn("rank", rank() over nameWindowDesc).show()

    empSalary
      .withColumn("dense_rank", dense_rank() over nameWindowDesc)  // Top n Salary
      .filter($"dense_rank" <= 2)
      .show()


    /*
     * window Analytic functions
     * lag  计算当前行与前 offset 行, 没有会置null, 也可以设置参数 default_value
     * lead 计算当前行与后 offset 行
     * cume_dist 出现的概率累计分布
     *
     */
    val diffSalaryPerRow = empSalary.withColumn("diff", lag($"salary", 1) over nameWindowDesc)
    val diffSalaryTwoRow = empSalary.withColumn("diff", lead($"salary", 2) over nameWindowDesc)

    diffSalaryPerRow.show()
    diffSalaryTwoRow.show()

    empSalary
      .withColumn("cume_dist", cume_dist() over nameWindowDesc)
      .show()


    /*
     * window Aggregate functions
     * 注意一下rangeBetween, rowBetween, 说白了就是为window Frame 的计算设置边界。
     */
    val nameWindow = Window.partitionBy("depName")
    empSalary.withColumn("avg", avg($"salary") over nameWindow).show()
    empSalary.withColumn("avg", max($"salary") over nameWindow).show()

    val rangeWindow = Window.rangeBetween(Window.currentRow, 1)
    empSalary.withColumn("avg", sum($"salary") over rangeWindow).show() // 打印出来会是什么呢？

    val maxSalary = max($"salary").over(nameWindow) - $"salary"
    empSalary.withColumn("salary_max_diff", maxSalary).show()

    val orderWindow = Window.orderBy("salary") // 会报Warning, 没有partitionBy 会导致数据都到一个partition下面
    empSalary
      .withColumn("salary_total", sum("salary") over orderWindow)
      .show()


  }


}
