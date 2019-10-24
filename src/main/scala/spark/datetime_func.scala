/*
 * Spark build-in Date and Time Functions
 */
package spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import java.time.LocalDateTime
import java.sql.Timestamp

object datetime_func {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  def main(args: Array[String]): Unit = {

    val timeDF =  Seq("2019-01-01 00:00:00").toDF("time")

    /*
     * current_date
     */

    spark.range(1).select(current_date()).show()

    /*
     * date_format
     */

    timeDF
      .withColumn("format_time", date_format($"time", "yyyy/mm/dd hh:mm:ss"))
      .show()

    /*
     * current_timestamp
     */
    spark.range(1).select(current_timestamp()).show()

    /*
     * unix_timestamp
     * 如果时间转换错误，默认为null
     */
    timeDF.withColumn("unix_timestamp", unix_timestamp($"time"))

    Seq("2017/01/01 00:00:00").toDF("time").withColumn("unix_timestamp", unix_timestamp($"time")).show

    /*
     * Time Window
     * tumbling window 开始时间和持续时间构成的流动窗口
     */
    val timeColumn = window($"time", "5 seconds")

    val levels = Seq(
      ((2012, 12, 12, 12, 12, 12), 5),
      ((2012, 12, 12, 12, 12, 14), 9),
      ((2012, 12, 12, 13, 13, 14), 4),
      ((2016, 8,  13, 0, 0, 0), 10),
      ((2017, 5,  27, 0, 0, 0), 15)).
      map { case ((yy, mm, dd, h, m, s), a) => (LocalDateTime.of(yy, mm, dd, h, m, s), a) }.
      map { case (ts, a) => (Timestamp.valueOf(ts), a) }.
      toDF("time", "level")


    val q = levels.select(window($"time", "5 seconds"), $"level")
    q.show(truncate = false)

    // 仔细看一下这里的执行结果
    levels
      .groupBy(window($"time", "5 seconds"))
      .agg(
       sum("level").alias("level_sum")
      )
      .select("window.start", "window.end", "level_sum")
      .show()

    /*
     * to_date
     * to_timestamp
     */
    Seq(("2019-01-01")).toDF("time").withColumn("to_date", to_date($"time")).show()
    Seq(("2019-01-01")).toDF("time").withColumn("to_timestamp", to_timestamp($"time", "yyyy-mm-dd")).show()


  }

}
