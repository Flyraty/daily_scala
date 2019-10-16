/*
 * SparkSession
 * SparkSession.builder.withExtensions https://zhuanlan.zhihu.com/p/50493032,可以用于新增自定义规则，像OptimizerRule，ParseRule. 场景:数据查询平台中枢每天接受大量的Sql请求，可以通过自定义Check 规则来过滤掉每个session提交的不合理请求
 */
package spark
import com.mongodb.spark.MongoSpark
import org.apache.spark.sql.SparkSession

object session {

  val session: SparkSession = {
    SparkSession.builder
      .appName("My Spark Application")
      .master("local[4]")
      .config("spark.mongodb.input.uri", "mongodb://127.0.0.1:27017/spark")
      .config("spark.mongodb.input.collection", "spark_input")
      .config("spark.mongodb.output.uri", "mongodb://127.0.0.1:27017/spark")
      .config("spark.mongodb.output.collection", "spark_output")
      .getOrCreate()
  }

  def main(args: Array[String]): Unit = {
    val seq = Seq(
      (1, 2, 3),
      (4, 5, 6))
    session.createDataFrame(seq).show()

    session.createDataFrame(seq).toDF()

    session.emptyDataFrame.show()

    session.range(1,10).show()

    session.sql("show tables")

    MongoSpark.load(session).toDF().show() // 通过session.config 连接读取mongo数据库



  }
}
