package spark.mysqlReader

import org.apache.spark.sql.SparkSession

object ReaderTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("MySQL").master("local[*]").getOrCreate()

    import spark.implicits._

    spark.read
      .format("spark.mysqlReader")
      .option("url", "jdbc:mysql://127.0.0.1:3306/fangtianxia?user=root&password=123456789")
      .option("dbtable", "newfangdetail")
      .option("driver", "com.mysql.jdbc.Driver")
      .load()
      .selectExpr("url_name", "score")
      .filter($"url_name".equalTo("121119") && $"score" >= "3.6")
      .explain(true)

  }

}
