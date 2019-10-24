/*
 * build-in regular functions
 */
package spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object regular_func {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  def main(args: Array[String]): Unit = {
    /*
     * broadcast
     * 用于标记小数据集合 broadcast hash join
     * 在执行计划里面可以看到 Broadcast Hint
     */
    val a = Seq((1, "666"), (2, "777")).toDF("id", "strNum")
    val b = Seq((1, "-1"), (2, "-2")).toDF("id", "negative")

    a.join(broadcast(b), Seq("id")).explain(extended = true)

   /*
    * coalesce  这里不是 coalesce hint
    * Returns the first column that is not null, or null if all inputs are null.
    * lit function
    */

    Seq((1, null), (1, "1"))
      .toDF("id", "null")
      .withColumn("null_fill", coalesce($"null", lit("default")))
      .show()

    /*
     *  col, column
     */
    col("name") == org.apache.spark.sql.functions.column("name") // true

    /*
     * struct
     * array
     * 两者的不同可以通过打印 schema 看出来， struct 里面的数据类型可以是不同的
     */
    val arrayDF = Seq((1, null), (1, "1"))
      .toDF("id", "null")
      .withColumn("array", array($"id", $"null"))


    val structDF = Seq((1, null), (1, "1"))
      .toDF("id", "null")
      .withColumn("struct", struct($"id", $"null"))

    arrayDF.show()
    structDF.show()

    arrayDF.printSchema()
    structDF.printSchema()

   /*
    * when otherwise
    */
    val example = Seq(
      (16, 0),
      (24, 12),
      (32, 11)
    ).toDF("divisor", "byDivisor")

    example
      .withColumn("div",
        when($"byDivisor".equalTo(0), -1)
          .otherwise($"divisor" / $"byDivisor")
      )
      .show()

    /*
     * monotonically_increasing_id  生成单调递增的ID, 不一定连续
     * monotonically_increasing_id() 是怎样计算的
     * 为什么不用Window row_number? 需要 shuffle 操作，大数据集下性能较低。emm, 不对，只是对每个partition里的数据进行了排序id。整个数据集上会有很多重复
     */
    spark.range(start = 0, end = 8, step = 1, numPartitions = 4)
      .withColumn("increase_id", monotonically_increasing_id())
      .show()


  }

}
