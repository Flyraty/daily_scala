name := "ScalaLearn"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.1"
  ,"org.joda" % "joda-convert" % "1.3"
  ,"org.reflections" % "reflections" % "0.9.11"
  ,"org.scalatest" %% "scalatest" % "2.2.6" % "test"
  ,"org.apache.spark" %% "spark-core" % "2.4.0"
  ,"org.apache.spark" %% "spark-sql" % "2.4.0"
)
