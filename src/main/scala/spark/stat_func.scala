/**
 * DataFrameStatFunctions is used to work with statistic functions in a structured query (a DataFrame).
 * 用于数据统计，中位值，均值，方差等等
 */
package spark
import org.apache.spark.sql.SparkSession

object stat_func {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  def main(args: Array[String]): Unit = {
    /*
     *
     */
  }

}
