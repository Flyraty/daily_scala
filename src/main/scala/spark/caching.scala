/*
 * cache persist checkpoint
 * One of the optimizations in Spark SQL is Dataset caching
 */
package spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


object caching {

  val spark = SparkSession.builder.master("local[*]").getOrCreate()

  import spark.implicits._

  Logger.getLogger("org.apache.spark.rdd.ReliableRDDCheckpointData").setLevel(Level.INFO)


  def main(args: Array[String]): Unit = {
    /*
     * 在actions 算子里面已经搞过，如下
     * cache, persist, write
     * DataFrame 和 RDD 的 persist， cache的实现还是有区别的。直观上RDD 默认MEMORY_ONLY , DataFrame 默认MEMORY_AND_DISK
     * 可以点开源码看一下，cache 调用的是无参数的persist, persist 可以通过 StorageLevel 设置持久化级别
     * Spark-UI Storage 下面可以看到已经缓存的RDD
     */

    val data = spark.range(1).cache() // lazy 特性，延迟计算，这个时候并没有执行cache，persist

    data.count() // action 算子执行的时候才触发了cache

    spark.catalog.clearCache() // 清空缓存

    println(spark.sharedState.cacheManager.lookupCachedData(data.queryExecution.logical).isDefined) // 判断是否cache

    data.createGlobalTempView("one")

    println(data.queryExecution.withCachedData.numberedTreeString) // 在执行结构化查询的时候，queryExecution 会请求CacheManager替换掉需要逻辑化查询的段

    spark.sql("clear cache") // 清空缓存

    spark.sql("cache table global_temp.one") // cache table sql, 会立即出发执行， not lazy

    println(spark.catalog.isCached("one")) // 判断是否cache

    spark.sql("cache lazy table global_temp.one") // 也可以变成lazy的

    spark.sql("refresh table global_temp.one") // 刷新缓存

    spark.sql("uncache table global_temp.one") // 删除指定缓存

    val q1 = spark.range(5).cache.filter($"id" % 2 === 0).select("id")
    val q2 = spark.range(1).filter($"id" % 2 === 0).select("id").cache

    q1.explain()
    q2.explain() // 两者的执行计划是不一样的


    /*
     * checkPoint 保存执行的逻辑计划和RDD的依赖用于失败重启，避免重复计算。通过eager参数来控制是否延迟计算
     * 必须先设置CheckPointDir
     * 如何从 checkpoint 检查点恢复状态 LogicalRDD -> RDD[InternalRow] -> DataFrame
     */
    spark.sparkContext.setCheckpointDir("./src/main/scala/resource/checkpoint")

    val nums = spark.range(5).withColumn("random", rand())

    val numsCheckpointed = nums.checkpoint()

    val schema = nums.schema

    import org.apache.spark.sql.execution.LogicalRDD
    val logicalRDD = numsCheckpointed.queryExecution.optimizedPlan.asInstanceOf[LogicalRDD]
    val checkpointFiles = logicalRDD.rdd.getCheckpointFile.get

    // TODO:待解决
    /**
      *  error code, 应该是访问控制的问题
      * object my1 {
      * *
      * import org.apache.spark.rdd.RDD
      * import scala.reflect.ClassTag
      * *
      * def recover[T: ClassTag](sc: SparkContext, path: String): RDD[T] = {
      *sc.checkpointFile[T](path)
      * }
      * }
      * *
      * object my2 {
      * *
      * import org.apache.spark.rdd.RDD
      * import org.apache.spark.sql.{DataFrame, SparkSession}
      * import org.apache.spark.sql.catalyst.InternalRow
      * import org.apache.spark.sql.types.StructType
      * *
      * def createDataFrame(spark: SparkSession, catalystRows: RDD[InternalRow], schema: StructType): DataFrame = {
      *spark.internalCreateDataFrame(catalystRows, schema)
      * }
      * }
      * *
      * import org.apache.spark.sql.catalyst.InternalRow
      * val numsRddRecovered = my1.recover[InternalRow](spark.sparkContext, checkpointFiles)
      * *
      * val numsRecovered = my2.createDataFrame(spark, numsRddRecovered, schema)
      *numsRecovered.show()
      */
  }


}
