/*
 * spark.read 读写csv, json, text
 * DataFrameWriter
 * DataFrameReader
 */
package spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.DataStreamReader
import org.apache.spark.sql.{DataFrameWriter, Dataset, DataFrameReader}
import org.apache.spark.sql.types.StructType
import java.io.File


object data_source_txt {

  val spark:SparkSession = SparkSession.builder.master("local[4]").getOrCreate()
  import spark.implicits._

  // 这里可以想一下嵌套的schema该怎么写
  val schema = new StructType()
    .add("name", "string", false)
    .add("age", "int", false)
    .add("education", "string", nullable = true)
    .add("skill", "string")

  def readTxtExample() = {
    val reader:DataFrameReader = spark.read

    reader.json("./src/main/scala/resource/people.json").show()

    reader.csv("./src/main/scala/resource/people.csv").toDF()

    reader.textFile("Readme.md").toDF()

    reader
      .format("csv")
      .option("header", true)    // 一些很有用的option选项，读取时的部分configure
      .option("delimiter", "|")
      .schema(schema)  // 按照Schema 读取
      .load("./src/main/scala/resource/people.csv")
      .show()
  }

  def writeTxtExample() = {
    val ints: Dataset[Int] = (0 to 5).toDS.repartition(1)
    val writer:DataFrameWriter[Int] = ints.write
    val fileExists:File => Boolean = file => file.exists()
    val path = "./src/main/scala/resource/ints.json"
    val outputFile = new File(path)

    if (fileExists(outputFile))
      dirDel(outputFile)
    writer.json(path)
    dirDel(outputFile)
    writer.format("json").save(path)
  }

  def streamExample() = {
    val stream:DataStreamReader = spark.readStream
    val paper = spark.readStream.text("Readme.md").as[String]
    val streamWriter = paper.writeStream
  }

  def dirDel(path: File) {
    if (!path.exists())
      return
    else {
      path.isFile match {
        case true => path.delete()
        case false => path.listFiles().foreach(dirDel)
      }
    }
    path.delete()
  }


  def main(args: Array[String]): Unit = {
    readTxtExample()
    writeTxtExample()
    streamExample()
  }

}
