# Learning Scala

## 目录
 - Scala
     - [循环控制]()
     - [类层次关系]()
     - [类和对象 继承]()
     - [接口Trait]()
     - [访问控制]()
     - [函数]()
     - [容错处理 Try Type]()
     - [包层次关系]()
     - [提取器]()
     - [序列提取器]()
     - [模式及模式匹配]()
     - [Option]()
     - [Either]()
     - [隐式转换]()
     - [并行处理之Future Promise]()
     - [简化代码]()
     - [一些函数组合子]()
     - [柯里化和部分函数应用]()
     - [类型类]()
     - [路径依赖类型]()
 - Spark
     - [Session]()
     - [Dataset DataFrame]()
     - [Data Source API]()
     - [Row]()
     - Column
     - Schema
     - [Transformations, Actions](https://jaceklaskowski.gitbooks.io/mastering-spark-sql/spark-sql-dataset-operators.html)
     - Join
     - Window
     - Pivot
     - Aggregate
     - cube/rollup/grouping_set
     - UDF
     - UDAF
     - Build-In Function
     - Execute Plan
     - transform 高阶函数
     - 常见算子
     - Checkpointing  Caching
     - Configue
 - Doc
     - TODO
 - Practice
     - [Rational 有理数类]()
     - [Element 二维图形元素类]()
     - [IntQueue 简单的整数队列]()
     - [递归全排列]()
     - more
 
## Notice
 - spark na.fill 当接收Map作为参数时，Map的value类型只能为Int, Long, Float, Double, String, Boolean.
 - [Unable to find encoder for type stored in a Dataset. Primitive types (Int, String, etc) and Product types (case classes) are supported by importing spark.implicits._ Support for serializing other types will be added in future releases.](https://forums.databricks.com/questions/13772/apache-spark-210-question-in-spark-sql.html)
 - case class 使用时遇到的问题，放在方法作用域外面，否则容易出现奇奇怪怪的错误

## 资料
 - [极客学院](http://wiki.jikexueyuan.com/list/scala/)
 - [Scala Doc](https://docs.scala-lang.org)
 - [Some Scala Blog Geeksforgeeks](https://www.geeksforgeeks.org/scala-functions-call-by-name/)
 - [Some Scala Blog alvinalexander](https://alvinalexander.com/scala/how-to-add-update-remove-elements-immutable-maps-scala)
 - [allaboutscala.com](http://allaboutscala.com/)
 - [Play Framework](https://doron.gitbooks.io/play-doc-zh/2.4/gettingStarted/06_Play_Tutorials.html)
 - [过往记忆的大数据](https://wemp.app/accounts/9228fadf-eedf-468f-b68a-8c2f69fd1f13)
 
## 课程(need money)
 - [programming-languages](https://www.coursera.org/learn/programming-languages)
 - [Scala 函数式程序设计原理](https://www.coursera.org/learn/progfun1)
 
## Blog
 - [Scala 一些特殊符号](https://notes.mengxin.science/2018/09/07/scala-special-symbol-usage/)
 - [transfrom](https://medium.com/@mrpowers/schema-independent-dataframe-transformations-d6b36e12dca6)
 - [Kudu：一个融合低延迟写入和高性能分析的存储系统](https://zhuanlan.zhihu.com/p/26798353)
 - [Kafka 简明教程](https://zhuanlan.zhihu.com/p/37405836)
 - [技术分享丨HDFS 入门](https://zhuanlan.zhihu.com/p/21249592)
 - [深入浅出 Hadoop YARN](https://zhuanlan.zhihu.com/p/54192454)
 - [列存储格式Parquet浅析](https://www.jianshu.com/p/47b39ae336d5)
 - [美图离线ETL实践](https://juejin.im/post/5b90ca816fb9a05cdf306ddb)
 - [Avro](https://blog.kazaff.me/2014/07/07/是什么系列之Avro/)
 - [SQL优化器原理——查询优化器综述](https://zhuanlan.zhihu.com/p/40478975)
 - [深入理解spark之架构与原理](https://juejin.im/post/5a73c8386fb9a0635e3cafaa)
 - [你真的了解Join吗？](https://www.jianshu.com/p/47db8ac001ea)
 - [Scala 隐式转换implicit详解](https://tryanswer.github.io/2018/05/24/scala-implicit/)
 - [扩展Spark Catalyst，打造自定义的Spark SQL引擎](https://zhuanlan.zhihu.com/p/50493032)
 - [scala 下划线解析报错： missing parameter type for expanded function](https://www.iteye.com/blog/zhouchaofei2010-2260107)
 
## Spark Learning
 - [Spark](https://github.com/apache/spark)
 - [简单了解](https://juejin.im/post/5a73c8386fb9a0635e3cafaa)
 - [Spark SQL](https://jaceklaskowski.gitbooks.io/mastering-spark-sql)
   - The Internals of Spark SQL
   - FILE-BASED DATA SOURCES,  KAFKA DATA SOURCE, AVRO DATA SOURCE, HIVE DATA SOURCE
   - DEVELOPING SPARK SQL APPLICATIONS 
 - Spark UI
 
 
 
 

 
 


