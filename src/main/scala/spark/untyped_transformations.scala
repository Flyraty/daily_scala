/**
  * Spark 常见转换算子
  * Untyped transformations are part of the Dataset API for transforming a Dataset to a DataFrame, a Column, a RelationalGroupedDataset, a DataFrameNaFunctions or a DataFrameStatFunctions (and hence untyped).
  */
package spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import spark.typed_transformations.{activity, spring, summer}

object untyped_transformations {
  val spark: SparkSession = SparkSession.builder.master("local[4]").getOrCreate()

  import spark.implicits._

  def main(args: Array[String]): Unit = {

    /*
     * agg groupBy
     */
    summer
      .union(spring)
      .withColumn("age", summer.apply("age").cast("int"))
      .groupBy("name")
      .avg("age")
      .show()

    /*
     * apply  col colRegex
     */
    val df1 = summer.withColumn("age", summer.apply("age").cast("int"))
    df1.printSchema()
    val df2 = summer.withColumn("id", col("id").cast("string"))
    df2.printSchema()

    /*
     * crossJoin 笛卡尔积全连接
     */
    summer.crossJoin(activity).show()

    /*
     * cube, rollup, grouping_sets  na
     * 城市 年龄对用户vip 等级和浏览数量的影响
     */
    val columns = Seq("name", "age", "city", "vip_level", "view_count")
    val users = Seq(
      ("Tom", 10, "北京", 0, 111),
      ("Jack", 20, "上海", 1, 3000),
      ("David", 23, "北京", 3, 4000),
      ("Mary", 18, "上海", 1, 1234),
      ("Tony", 50, "深圳", 0, 200),
      ("Group", 60, "北京", 0, 40),
      ("Ali", 34, "上海", 4, 6666),
      ("Alas", 45, "北京", 2, 10000)
    ).toDF(columns: _*)

    users
      .rollup("age", "city")
      .sum("view_count").alias("sum_view")
      .na.fill("None")
      .show()

    users
      .cube("age", "city")
      .sum("view_count").alias("sum_view")
      .show()

    val sales = Seq(
      ("Warsaw", 2016, 100),
      ("Warsaw", 2017, 200),
      ("Boston", 2015, 50),
      ("Boston", 2016, 150),
      ("Toronto", 2017, 50)
    ).toDF("city", "year", "amount")
    sales.createOrReplaceTempView("sales")

    val q = spark.sql(
      """
      SELECT city, year, sum(amount) as amount
      FROM sales
      GROUP BY city, year
      GROUPING SETS ((city, year), (city), ())
      ORDER BY city DESC NULLS LAST, year ASC NULLS LAST
    """)

    q.show()


    /*
     * drop  join
     */
    summer.union(spring).drop("name").show()
    summer.join(activity, Seq("skill")).show()

    /*
     * select selectExpr
     * selectExpr accepts SQL statements.
     */
    users.selectExpr("view_count as count").show()


    /*
     * withColumn, withColumnRenamed
     * 重命名 添加新列
     */
    summer.withColumn("id", $"id" + 1).show()
    summer.withColumnRenamed("id", "ID").show()

    /*
     * stat
     */
    summer.stat

  }


}
