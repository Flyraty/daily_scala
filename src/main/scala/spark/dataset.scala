/*
 * DataSet, DataFrame
 * Dataset is a strongly-typed data structure in Spark SQL that represents a structured query.
 * It is therefore fair to say that Dataset consists of the following three elements:  可以通过debug 查看类型得到
 *    1. QueryExecution (with the parsed unanalyzed LogicalPlan of a structured query)
 *    2. Encoder (of the type of the records for fast serialization and deserialization to and from InternalRow)
 *    3. SparkSession
 * type DataFrame = Dataset[Row]
 * It is only with Datasets to have syntax and analysis checks at compile time
 * Spark SQL introduces a tabular functional data abstraction called DataFrame.
 */
package spark
import org.apache.spark.sql.SparkSession

object dataset {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._

  case class Person(name:String, age:Int)

  def CreateDFUseSchemaAndRows() = {
    /*
     * Custom DataFrame Creation using createDataFrame
     */
    val lines = spark.read.textFile("./src/main/scala/resource/people.csv")
    val headers = lines.first
    val noheaders = lines.filter(_ != headers)

    import org.apache.spark.sql.types.{StructField, StringType, StructType}

    val fs = headers.split("|").map(f => StructField(f, StringType))
    val schema = StructType(fs)


    import org.apache.spark.sql.Row

    val rows = noheaders.map(_.split("|")).map(a => Row.fromSeq(a))
    val people = spark.createDataFrame(rows, schema)
    people.show()

  }

  def main(args: Array[String]): Unit = {
    spark.range(1).filter($"id" === 0).explain(true)

    spark.range(1).filter(_ == 0).explain(true)  // 和上面的执行计划有啥区别？？

    val df = Seq("I am a DataFrame").toDF("text")
    df.show()


    val people = Seq(Person("Tom", 20), Person("Mary", 21), Person("David", 22))

    spark.createDataFrame(people).show()

    CreateDFUseSchemaAndRows()


  }

}
