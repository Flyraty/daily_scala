/*
 * User-Defined Aggregate Functions
 * Aggregator
 * TODO: 都懂，还是不大会写UDAF，让我在尝试一下
 */
package spark
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction, Aggregator}

class MyCountUDAF extends UserDefinedAggregateFunction {
  override def inputSchema: StructType = {
    new StructType().add("id", LongType, nullable = true)
  }

  override def bufferSchema: StructType = {
    new StructType().add("count", LongType, nullable = true)
  }

  override def dataType: DataType = LongType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = buffer(0) = 0L

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = buffer(0) = buffer.getLong(0) + 1

  override def merge(buffer: MutableAggregationBuffer, row: Row): Unit = buffer(0) = buffer.getLong(0) + row.getLong(0)

  override def evaluate(buffer: Row): Any = buffer.getLong(0)

}

class MyAverageUDAF extends UserDefinedAggregateFunction{
  override def inputSchema: StructType = {
    new StructType().add("inputColumn", LongType)
  }

  override def bufferSchema: StructType = {
    new StructType()
      .add("sum", LongType)
      .add("count", LongType)
  }

  override def dataType: DataType = DoubleType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getLong(0) + input.getLong(0)
    buffer(1) = buffer.getLong(1) + 1
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  def evaluate(buffer: Row): Double = buffer.getLong(0) / buffer.getLong(1)

}

object UDAF {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  def main(args: Array[String]): Unit = {
    val myCount = new MyCountUDAF
    val myAverage = new MyAverageUDAF

    spark
      .range(start = 0, end = 4, step = 1, numPartitions = 2)
      .withColumn("group", $"id" % 2)
      .groupBy("group")
      .agg(myCount.distinct($"id") as "count")
      .show()

    spark
      .range(start = 0, end = 4, step = 1, numPartitions = 2)
      .agg(myAverage($"id"))
      .show()


  }

}
