/*
 * spark 读写kafka
 * kafkaDataSource Options
 * kafkaRelation
 * kafkaSourceRDD
 * TODO:有点多，还是要先了解下kafka
 */
package spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

object data_source_kafka {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.master("local[4]").getOrCreate()

    val schema = new StructType().add("id", "int")

    spark.read.format("org.apache.spark.sql.kafka010.KafkaSourceProvider").load

    val kafka = spark.read.format("kafka").load


    val fromkafkatopic1 = spark.read
      .format("kafka")
      .option("subscribe", "topic1")
      .option("kafka.bootstarp.servers", "localhost:9092")
      .schema(schema)
      .load

    fromkafkatopic1.printSchema()
  }

}
