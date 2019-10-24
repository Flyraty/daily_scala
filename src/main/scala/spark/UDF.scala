/*
 * User-Defined Functions
 * UDF 是个黑盒子，Spark SQL 不会尝试去优化UDF，所以 UDF 是个双刃剑
 */
package spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import scala.collection.mutable.WrappedArray

object UDF {
  val spark  = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  def main(args: Array[String]): Unit = {

    // 写 UDF 和写普通的 Scala 函数并没有太大区别
    val upperUDF = udf((t:String) => t.toUpperCase)
    val sizeUDF = udf((t:WrappedArray[String]) => t.length)
    val equalUDF = udf((t:String) => t.equals("hello"))

    Seq("hello", "World")
      .toDF("word")
      .withColumn("word_upper", upperUDF($"word"))
      .show()


    // 对比一下UDF 实现和内置实现的 执行计划. 没发现有啥不一样。 TODO:需要去了解一下 SQL 的解析，执行计划这些概念
    val df1 = Seq("hello", "World")
      .toDF("word")
      .filter(equalUDF($"word"))

    val df2= Seq("hello", "World")
      .toDF("word")
      .filter($"word".equalTo("hello"))

    println(df1.queryExecution.executedPlan)
    println(df2.queryExecution.executedPlan)

  }

}
