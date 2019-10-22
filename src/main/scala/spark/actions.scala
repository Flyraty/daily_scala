/**
 spark 常见行为算子
 */
package spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

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
    spark.sparkContext.setCheckpointDir("./src/main/scala/checkpoint")
    doc.checkpoint()
    doc.count()


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
     * DataFrame 和 RDD 的 persist， cache的实现还是有区别的。直观上RDD 默认MEMORY_ONLY , DataFrame 默认MEMORY_AND_DISK
     * 可以点开源码看一下，cache 调用的是无参数的persist, persist 可以通过 StorageLevel 设置持久化级别
     */
    spark.read.textFile("./Readme.md").cache()

    spark.read.textFile("./Readme.md").persist(StorageLevel.MEMORY_AND_DISK_2)

    spark.read.textFile("./Readme.md").rdd.cache()


    /*
     * collect, 点开源码查看实现
     * Returns a Java list that contains all rows in this Dataset.
     * 会触发 Shuffle 操作，将多结点的数据拉至一个结点，耗时耗内存，避免在大数据集上使用
     */

    println(spark.range(5).collect().mkString(""))

    /*
     * take
     * take is an action on a Dataset that returns a collection of n records.
     */
    println(spark.range(5).take(3))

    /*
     * summary
     */
    doc.summary().show()

    /*
     * toLocalIterator  小数据集可以用，大数据集估计凉凉，不在分布式了
     * val total = for (row <- spark.range(10).toLocalIterator()) yield row
     * println(total)
     * 上面的报错了。。。。。
     */



    /*
     * reduce
     */




  }

}
