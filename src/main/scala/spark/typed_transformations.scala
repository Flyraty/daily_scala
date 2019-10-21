/*
 * 常见转换算子和行为算子
 * Typed transformations are part of the Dataset API for transforming a Dataset with an Encoder (except the RowEncoder).
 * Untyped transformations are part of the Dataset API for transforming a Dataset to a DataFrame, a Column, a RelationalGroupedDataset, a DataFrameNaFunctions or a DataFrameStatFunctions (and hence untyped).
 */
package spark

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object typed_transformations {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()

  import spark.implicits._

  final case class Sentence(id: Long, text: String)

  /*
     * 一群人参加了夏令营, 一群人又参加了春游
     */
  val columns = Seq("name", "age", "education", "skill", "id")
  val summer = Seq(
    ("丢丢", "20", "University", "Computer Science", 1),
    ("点点", "16", "High School", "liberal arts", 2),
    ("小小", "19", "University", "Math", 3),
    ("大大", "20", "Postgraduate", "Chemistry", 4),
    ("空空", "21", null, null, 5)
  ).toDF(columns: _*)

  val spring = Seq(
    ("丢丢", "20", "University", "Computer Science", 1),
    ("点点", "16", "High School", "liberal arts", 2)
  ).toDF(columns: _*)

  val activity = Seq(
    ("Computer Science", "game"),
    ("liberal arts", "reading"),
    ("Chemistry", "experiment")
  ).toDF("skill", "activity")

  def main(args: Array[String]): Unit = {


    summer.show()

    /*
     * as, alias Typed Transformation
     */
    summer.select($"age".as("Age")).show()
    summer.select($"age".alias("Age")).show()

    /*
     * coalesce  repartition, 可以点到源码里面去看
     */
    summer.coalesce(1).explain(true) // shuffle = false
    summer.repartition(1).explain(true) // shuffle = true

    /*
     * dropDuplicates
     */
    summer.dropDuplicates("age").show()

    /*
     * except 差集, interesect
     */
    summer.except(spring).show()
    summer.intersect(spring).show()

    /*
     * filter where
     */
    summer
      .select("name", "age")
      .withColumn("age", $"age".cast("Int"))
      .filter($"age" > 20)
      .show()

    summer
      .select("name", "age")
      .withColumn("age", $"age".cast("Int"))
      .where($"age" > 20)
      .show()


    /*
     * flatMap, map, mapPartitions
     * map对所有元素做操作, mapPartitions对每个分区做操作
     */

    val sentences = Seq(Sentence(0, "hello world"), Sentence(1, "witaj swiecie")).toDS()
    sentences.flatMap(s => s.text.split("\\s+")).show()
    sentences.map(s => s.text.length > 12).show()
    sentences.mapPartitions(it => it.map(s => s.text.length > 12)).show()

    /*
     * joinWith
     */
    summer.joinWith(activity, summer("skill") === activity("skill")).show()

    /*
     * limit
     */
    summer.limit(1).show()
    summer.limit(3).show()

    /*
     * randomSplit, 可以用于对照组测试，分割出几个不同数据集
     */
    spark.range(10).randomSplit(Array[Double](2, 3)).foreach(_.show())

    /*
     * toJSON
     */
    sentences.toJSON.show()

    /*
     * transform  可以做出很多高级操作，详细见transform
     */

    def withDoubled(df: DataFrame) = df.withColumn("doubled", col("id") * 2)

    summer.transform(withDoubled).show()

    /*
     * union, unionByName
     * unionByName throws an AnalysisException if there are duplicate columns in either Dataset.
     * unionByName throws an AnalysisException if there are columns in this Dataset has a column that is not available in the other Dataset.
     */
    summer.union(spring).show()

    /*
     * TODO withWatermark 创建流式数据集
     */


  }


}
