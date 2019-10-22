/**
 * spark 与JDBC
 */
package spark

import org.apache.spark.sql.SparkSession

object data_source_jdbc {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()

  def main(args: Array[String]): Unit = {
    // val table = spark.read.jdbc(url, table, properties)
    // val table = spark.read.format("jdbc").options(...).load(...)

  }

}
