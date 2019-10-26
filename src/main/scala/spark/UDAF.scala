/*
 * User-Defined Aggregate Functions
 * Aggregator
 * Spark自定义聚合函数时，需要实现UserDefinedAggregateFunction中8个方法：
 * 1. inputSchema：输入的数据类型
 * 2. bufferSchema：中间聚合处理时，需要处理的数据类型
 * 3. dataType：函数的返回类型
 * 4. deterministic：同样的输入是否每次对应同样的输出
 * 5. initialize：为每个分组的数据初始化
 * 6. update：每个分组，有新的值进来时，如何进行分组的聚合计算
 * 7. merge：由于Spark是分布式的，所以一个分组的数据，可能会在不同的节点上进行局部聚合，就是update，但是最后一个分组，在各节点上的聚合值，要进行Merge，也就是合并
 * 8. evaluate：一个分组的聚合值，如何通过中间的聚合值，
 */
package spark
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction, Aggregator}
import org.apache.spark.sql.{Encoders, Encoder}

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

class MyMaxUDAF extends UserDefinedAggregateFunction {
  override def inputSchema: StructType = {
    new StructType().add("inputColumn", LongType)
  }

  override def bufferSchema: StructType = {
    new StructType().add("max", LongType, nullable = true)
  }

  override def dataType: DataType = LongType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = buffer(0) = 0L

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {

    val temp = buffer.getLong(0)
    val rowValue = input.getLong(0)
    buffer(0) = if (temp > rowValue) temp else rowValue
  }

  override def merge(buffer: MutableAggregationBuffer, row: Row): Unit = {
    val temp = buffer.getLong(0)
    val rowValue = row.getLong(0)
    buffer(0) = if (temp > rowValue) temp else rowValue
  }

  override def evaluate(buffer: Row): Any = buffer.getLong(0)

}

case class Employee(name:String, salary:Long)
case class Average(var count:Long, var sum:Long)

class MyAverageAggregator extends Aggregator[Employee, Average, Double]{
  // 初始化类型buffer
  override def zero: Average = Average(0L, 0L)
  // 计算聚合中间结果
  override def reduce(b: Average, a: Employee): Average = {
    b.count += 1
    b.sum += a.salary
    b
  }
  // 合并中间结果
  override def merge(b1: Average, b2: Average): Average = {
    b1.sum += b2.sum
    b1.count += b2.count
    b1
  }
  // 计算最终结果并确定类型
  override def finish(reduction: Average): Double = reduction.sum.toDouble / reduction.count
  // 中间值类型指定编码器
  override def bufferEncoder: Encoder[Average] = Encoders.product
  // 结果类型指定编码器
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble

}

object UDAF {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  def main(args: Array[String]): Unit = {
    val myCount = new MyCountUDAF
    val myAverage = new MyAverageUDAF
    val myAveragor = new MyAverageAggregator
    val myMax = new MyMaxUDAF

    spark.udf.register("myMax", myMax)

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

    val employee = Seq(
      Employee("Tom", 2674),
      Employee("Ton", 3400),
      Employee("Top", 4218),
      Employee("Tos", 1652)
    )

    employee.toDS().select(myAveragor.toColumn.name("average_salary")).show() // 因为这里是Dataset[Employee]类型数据做聚合,所以toDS

    val df = Seq(("Michael", 3000), ("Andy", 4500), ("Justin", 3500), ("Berta", 4200)).toDF("name","salary")

    df.repartition(1).agg(myMax($"salary"))
      .show()


    df.repartition(1).selectExpr("myMax(salary)").show()

    spark
      .range(start = 0, end = 4, step = 1, numPartitions = 2)
      .agg(myMax($"id"))
      .show()

  }

}


