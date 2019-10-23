/*
 * Spark build-in collection functions
 */
package spark

import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.catalyst.expressions.ArrayContains
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType

object collection_func {
  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  import spark.implicits._

  def main(args: Array[String]): Unit = {
    /*
     * reverse
     */
    val arr = Seq((Array[Int](1, 2, 3, 4))).toDF("array")

    arr.withColumn("reverse", reverse($"array")).show()

    /*
     * size
     */
    arr.withColumn("size", size($"array")).show()

    /*
     * posExplode
     * posExplode_outer
     * arr.withColumn("posExplode", posexplode($"array")).show()  error
     */
    arr.select(posexplode($"array")).show()


    /*
     * explode
     * explode_outer
     */
    arr.withColumn("num", explode($"array")).show()

    Seq(Seq.empty[String]).toDF("array").select(explode_outer($"array")).show()


    /*
     * from_json
     */
    val addressesSchema = new StructType()
      .add($"city".string)
      .add($"state".string)
      .add($"zip".string)
    val schema = new StructType()
      .add($"firstName".string)
      .add($"lastName".string)
      .add($"email".string)
      .add($"addresses".array(addressesSchema))
    val schemaAsJson = schema.json

    val rawJsons = Seq(
      """
        {
          "firstName" : "Jacek",
          "lastName" : "Laskowski",
          "email" : "jacek@japila.pl",
          "addresses" : [
            {
              "city" : "Warsaw",
              "state" : "N/A",
              "zip" : "02-791"
            }
          ]
        }
      """).toDF("rawjson")
    val people = rawJsons
      .select(from_json($"rawjson", schemaAsJson, Map.empty[String, String]) as "json")
      .select("json.*")
      .withColumn("address", explode($"addresses"))
      .drop("addresses")
      .select("firstName", "lastName", "email", "address.*")

    people.show()

    /*
     * array_contains
     */
    val fil = array_contains($"array", 5)
    arr.filter(fil).show()

    val codes = Seq(
      (Seq(1, 2, 3), 2),
      (Seq(1), 1),
      (Seq.empty[Int], 1),
      (Seq(2, 4, 6), 0)).toDF("codes", "cd")

    codes.where("array_contains(codes, cd)").show()

    // codes.where(array_contains($"codes", $"cd")).show()  error

    codes.where(new Column(ArrayContains($"codes".expr, $"cd".expr))).show()


  }

}
