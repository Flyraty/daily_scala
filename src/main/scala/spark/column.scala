/*
 * Spark Column API
 */
package spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

object column {
  val spark = SparkSession.builder.master("local[4]").getOrCreate()
  import spark.implicits._

  def main(args: Array[String]): Unit = {

    // 创建Column的几种方式
    val idCol:Column = $"id"
    println(idCol.getClass.getSimpleName)

    val column1 = org.apache.spark.sql.functions.column("id")

    val column2 = col("id")

    val column3 = spark.range(5).col("id")

    val column4 = column3("id")

    val column5 = spark.range(5).apply("id")

    idCol.as("int")
    idCol.cast("int")

    val q = spark.range(5)
    q.withColumn("idPlus", $"id"+1).show()
    q.filter("id like 1").show()

    val ids = Seq((1, 2 , 2), (2, 3, 1)).toDF("x", "y", "id")
    val f = $"id".isin($"x", $"y")
    ids.filter(f).show()

    spark.range(10).sort($"id".asc).show()
    spark.range(10).sort($"id".desc).show()

    // Typed Column

    $"id".alias("ID")

    $"id".as[Int]


  }

}
