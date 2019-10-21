/*
 spark 常见行为算子
 */
package spark

import org.apache.spark.sql.SparkSession

object actions {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  val newSpark = spark.newSession()

  def main(args: Array[String]): Unit = {
    /*
     * createTempView createOrReplaceTempView createGlobalTempView  createTempViewCommand
     * createTempView createOrReplaceTempView 创建或者替换临时视图，生命周期和sparkSession有关
     * createGlobalTempView 创建全局视图，生命周期和Spark Application有关
     */
    val doc = spark.read.textFile("./Readme.md").cache()
    doc.createGlobalTempView("doc")

    spark.range(1).createTempView("foo")

    newSpark.catalog.tableExists("doc") // true

    newSpark.catalog.tableExists("foo") // false

    /*
     * checkPoint
     */


    /*
     * explain
     */
    doc.explain(extended = true)

    /*
     * hint 查询提示，即向查询加入注释，告诉查询优化器提供如何优化逻辑计划， 这在查询优化器无法做出最佳决策时十分有用
     * coalesce reparation
     */
    val q = spark.range(1).hint(name = "myHint", 100, true)
    val plan = q.queryExecution.logical
    println(plan.numberedTreeString)


    /*
     * rdd toDF
     * toDF converts a Dataset into a DataFrame.
     */
    doc.rdd
    doc.toDF()

    /*
     * schema printSchema
     */
    doc.printSchema()

    /*
     * cache, persist, write
     */





  }

}
