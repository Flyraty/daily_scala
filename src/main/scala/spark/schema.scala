/*
 * Spark Schema
 * 展示数据的结构
 */
package spark

import org.apache.spark.sql.{Encoders, SparkSession}
import org.apache.spark.sql.types.DataTypes._
import org.apache.spark.sql.types.{StructField, StructType}


object schema {
  val spark = SparkSession.builder.master("local[*]").getOrCreate()
  import spark.implicits._
  case class Person(id:Int, name:String)
  case class Category(cat1: String, cat2: String, str2: String, str3: String)

  def main(args: Array[String]): Unit = {
    // 创建Schema，嵌套的Schema
    val schema = new StructType()
      .add("name", "string", false)
      .add("id", "int", false)
      .add("properties", new StructType()
          .add("age", "int")
          .add("address", "string")
      )

    val schemaWithMap = StructType(
      StructField("map", createMapType(LongType, StringType), false) :: Nil
    )

    val simpleSchema = new StructType().add($"id".int)
    println(simpleSchema.simpleString)

    schema.printTreeString()
    println(schemaWithMap.prettyJson)

    // 利用Encoder 来解析Schema

    Encoders.INT.schema.printTreeString()

    Encoders.product[(String, java.sql.Timestamp)].schema.printTreeString()

    Encoders.product[Person].schema.printTreeString()

    // Implicit Schema
    val df = spark.range(10)
    df.printSchema()
    println(df.schema)
    println(df.schema("id").dataType)

    // 查看StructType 的数据结构，本质上就是Seq[StructFiled], 所以其具有Seq的性质
    schema.foreach(println)

    println(schema.toDDL) // Schema 转换为数据定义语言DDL

    // StructFiled 的组成 name, datatype, nullable, metadata
    schema("id").withComment("person id")
    schema("id").getComment()

    import org.apache.spark.sql.types.MetadataBuilder
    val metadata = new MetadataBuilder()
      .putString("name", "person name, primary key")
      .build

    val some = new StructType().add("name", dataType = "string", nullable = false, metadata=metadata).toDDL
    println(some)

    val category = Seq(
      Category("mc.idx", "喊麦", "E", "MC"),
      Category("game.idx", "游戏", "G", "Other Games"),
      Category("other.mobilelive", "手机直播", "O", "Mobile Live"),
      Category("talk.idx", "脱口秀", "E", "Talk Show"))
    val a = category.toDS()
    var b = List[Category]()
    for (c <- a.collect()){ b = c::b}

    val categoryConvertMap = b.map(t => t.cat1 -> t.cat2).toMap
    print(categoryConvertMap.getOrElse("ccc", "啊啊啊啊啊"))


  }

}
