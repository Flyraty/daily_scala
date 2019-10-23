/**
 * 常见的基本聚合
 * agg
 * groupBy
 * groupByKey
 * RelationalGroupedDataset
 * KeyValueGroupedDataset
 */
package spark
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.functions._

object basic_aggregate {
  val spark = SparkSession.builder.master("local[4]").getOrCreate()
  import spark.implicits._

  case class Token(name:String, produceId:Int, score:Double)

  def main(args: Array[String]): Unit = {
    spark.range(10).agg(sum($"id"))

    val tokens = Seq(
      Token("aaa", 100, 0.12),
      Token("aaa", 200, 0.29),
      Token("bbb", 200, 0.31),
      Token("bbb", 300, 0.42)
    )

    val tokensDF = tokens.toDF()
    tokensDF.show()

    /*
     * 查看groupBy源码，groupBy返回的是RelationalGroupedDataset类型，在查看RelationalGroupedDataset数据结构，可以发现很多定义在上面的操作，可以发现cube，rollup, pivot也是这样子的
     * RelationalGroupedDataset is an interface to calculate aggregates over groups of rows in a DataFrame. 也就是说定义在Dataset[Row]上
     */

    tokensDF.groupBy("name").sum("score").show()
    tokensDF.groupBy("name").avg("score").show()
    tokensDF.groupBy("name").avg().show()
    tokensDF.groupBy("name").max("score").show()
    tokensDF.groupBy("name").count().show()
    tokensDF.groupBy("produceId").agg(Map("score" -> "avg")).show()

    /*
     * groupByKey
     * KeyValueGroupedDataset is an experimental interface to calculate aggregates over groups of objects in a typed Dataset. 下面的例子是操作在Dataset[Token]上
     */
    val tokensDS = tokens.toDS()
    tokensDS.groupByKey(_.produceId).count.orderBy($"value").show
    import org.apache.spark.sql.expressions.scalalang._
    tokensDS
      .groupByKey(_.produceId)
      .agg(typed.sum[Token](_.score))
      .toDF("produceId", "sum")
      .orderBy('produceId)
      .show()

    tokensDS.groupByKey(_.produceId).keys.show()


    /*
     * pivot  透视行列转换
     * unpivot 反透视, 通过stack 实现
     */

    val example = Seq(
      ("北京", 10000, 2015),
      ("北京", 11000, 2016),
      ("北京", 12000, 2017),
      ("上海", 10300, 2015),
      ("上海", 11700, 2016)
    ).toDF("city", "value", "year")

    example.show()

    val piv = example.groupBy("city").pivot("year").sum("value")
    piv.show()

    val unPiv = piv
      .selectExpr("city", "stack(3, '2015', `2015`, '2016', `2016`, '2017', `2017`) as (year, value)")
      .filter($"value".isNotNull)
    unPiv.show()

  }
}
