/*
 * transform 高阶函数的使用  https://medium.com/@mrpowers/schema-independent-dataframe-transformations-d6b36e12dca6
 */
package spark

import org.apache.spark.sql.{DataFrame, SparkSession}


object transform {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local")
      .config("spark.ui.port", "14040").getOrCreate()
    import org.apache.spark.sql.functions._
    import spark.implicits._
    val colNames = Seq("vendor", "20190101", "20190102", "20190103", "20190104",
      "20190105", "20190106", "20190107", "20190108", "20190109")
    var ds = Seq(
      ("20015545", 1, 2, 3, 4, 5, 6, 7, 8, 9),
      ("20015546", 11, 12, 13, 14, 15, 16, 17, 18, -1),
      ("20015547", 11, 12, -1, 14, 15, 16, 17, 18, -1))
      .toDF(colNames: _*)

    val valColNames = colNames.drop(1)

    def averageFunc(colNames: Seq[String]) = {
      val markCols = colNames.map(col(_))
      markCols.foldLeft(lit(0)) { (x, y) => x + y } / markCols.length
    }

    def replaceCol(colIdx: Int, colNames: Seq[String])(df: DataFrame): DataFrame = {
      val colI = colNames(colIdx)
      val start = if (colIdx >= 4) colIdx - 4 else 0
      val cols = colNames.slice(start, colIdx)
      println(cols)
      val checkVal = udf((v: Int) => v != -1)
      if (cols.length == 0) df else df.withColumn(colI, when(checkVal(col(colI)), col(colI)).otherwise(averageFunc(cols)))
    }

    ds.show()
    valColNames.indices.foreach(idx => {
      ds = ds.transform(replaceCol(idx, valColNames))
      ds.show()
    })

  }
}
