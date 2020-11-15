package spark.mysqlReader

import java.sql.{DriverManager, ResultSet}

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.util.DateTimeUtils
import org.apache.spark.sql.jdbc.JdbcDialects
import org.apache.spark.sql.sources.v2.reader.{InputPartition, InputPartitionReader}
import org.apache.spark.sql.sources.{EqualTo, Filter, GreaterThan, IsNotNull}
import org.apache.spark.sql.types._
import org.apache.spark.unsafe.types.UTF8String


case class MySQLInputPartition(requiredSchema: StructType, pushed: Array[Filter], options: Map[String, String])
  extends InputPartition[InternalRow] {

  override def createPartitionReader(): InputPartitionReader[InternalRow] = {
    MySQLInputPartitionReader(requiredSchema, pushed, options)
  }

}

case class MySQLInputPartitionReader(requiredSchema: StructType, pushed: Array[Filter], options: Map[String, String])
  extends InputPartitionReader[InternalRow] {

  val tableName = options("dbtable")
  val driver = options("driver")
  val url = options("url")

  def initSQL = {
    val selected = if (requiredSchema.isEmpty) "1" else requiredSchema.fieldNames.mkString(",")

    if (pushed.nonEmpty) {
      val dialect = JdbcDialects.get(url)
      val filter = pushed.map {
        case EqualTo(attr, value) => s"${dialect.quoteIdentifier(attr)} = ${dialect.compileValue(value)}"
        case GreaterThan(attr, value) => s"${dialect.quoteIdentifier(attr)} > ${dialect.compileValue(value)}"
        case IsNotNull(attr) => s"${dialect.quoteIdentifier(attr)} IS NOT NULL"

      }.mkString(" AND ")

      s"select $selected from $tableName where $filter"
    } else {
      s"select $selected from $tableName"
    }
  }

  val rs: ResultSet = {
    Class.forName(driver)
    val conn = DriverManager.getConnection(url)
    println(initSQL)
    val stmt = conn.prepareStatement(initSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
    stmt.setFetchSize(1000)
    stmt.executeQuery()
  }

  override def next(): Boolean = rs.next()

  override def get(): InternalRow = {
    InternalRow(requiredSchema.fields.zipWithIndex.map { element =>
      element._1.dataType match {
        case IntegerType => rs.getInt(element._2 + 1)
        case LongType => rs.getLong(element._2 + 1)
        case StringType => UTF8String.fromString(rs.getString(element._2 + 1))
        case e: DecimalType => val d = rs.getBigDecimal(element._2 + 1)
          Decimal(d, d.precision, d.scale)
        case TimestampType => val t = rs.getTimestamp(element._2 + 1)
          DateTimeUtils.fromJavaTimestamp(t)

      }

    }: _*)
  }

  override def close(): Unit = rs.close()


}