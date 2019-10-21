/*
 * 读取avro 格式的文件
 * AvroDataSourceOption  读取的json schema, 压缩方式, 扩展,
 */
package spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.avro._

object data_source_avro {
  val spark = SparkSession.builder.master("local[4]").getOrCreate()

  import spark.implicits._

  def main(args: Array[String]): Unit = {
    val q = spark
      .range(1)
      .withColumn("to_avro_id", to_avro($"id"))

    q.show()

    val jsonFormatSchema =
      """
        |{
        |   "type":"long",
        |   "name":"id"
        |}
      """.stripMargin

    q
      .select(from_avro($"to_avro_id", jsonFormatSchema) as "id from avro")
      .show()

    spark
      .range(1)
      .repartition(1)
      .write
      .format("avro")
      .save("./src/main/scala/resource/data.avro")

    spark
      .read
      .format("avro")
      .load("./src/main/scala/resource/data.avro")
      .show()
  }
}
