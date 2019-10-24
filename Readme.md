# Learning Scala

## 目录
 - Scala
     - [循环控制](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/cycle_control.scala)
     - [类层次关系](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/class_level.scala)
     - [类和对象 继承]()
     - [接口Trait](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/traits_oo.scala)
     - [访问控制](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/function_oo.scala)
     - [函数](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/function_oo.scala)
     - [容错处理 Try Type](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/try_type.scala)
     - [包层次关系]()
     - [提取器](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/extractor.scala)
     - [序列提取器](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/seq_extractor.scala)
     - [模式及模式匹配](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/pattern_match.scala)
     - [Option](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/option_oo.scala)
     - [Either](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/either_type.scala)
     - [隐式转换](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/implicit_oo.scala)
     - [并行处理之Future Promise](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/future_oo.scala)
     - [简化代码](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/simplify_code.scala)
     - [一些函数组合子](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/func_combination.scala)
     - [柯里化和部分函数应用](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/func_curry.scala)
     - [类型类](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/type_class.scala)
     - [路径依赖类型](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/scala/path_dependent_type.scala)
 - Spark
     - [Session](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/session.scala)
     - [Dataset DataFrame](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/dataset.scala)
     - [Data Source API](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/data_source_txt.scala)
     - [Row](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/row.scala)
     - [Column](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/column.scala)
     - [Schema](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/schema.scala)
     - [Typed Transformations](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/typed_transformations.scala)
     - [Untyped Transformations](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/untyped_transformations.scala)
     - [Actions](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/actions.scala)
     - [DataFrameNaFunctions](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/na_func.scala)
     - [DataFrameStatFunctions](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/stat_func.scala)
     - [Basic Aggregate](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/basic_aggregate.scala)
     - [transform 高阶函数](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/transform.scala)
     - [Window Aggregate](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/window_aggregate.scala)
     - [Collection Functions](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/collection_func.scala)
     - [Date and Time Functions](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/datetime_func.scala)
     - [Regular Functions](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/regular_func.scala)
     - [UDF](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/UDF.scala)
     - [UDAF,Aggregator](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/UDAF.scala)
     - [Pivot](https://github.com/Flyraty/daily_scala/blob/master/src/main/scala/spark/basic_aggregate.scala)
     - cube/rollup/groupings_set
     - Execute Plan
     - CheckPoint Cache Persist
     - Configure
 - Practice
     - [Rational 有理数类]()
     - [Element 二维图形元素类]()
     - [IntQueue 简单的整数队列]()
     - [一些递归实现]()
     - more
 - Program Language Part A
    - Emacs
    - ML
 
## Notice
 - spark na.fill 当接收Map作为参数时，Map的value类型只能为Int, Long, Float, Double, String, Boolean.
 - [Unable to find encoder for type stored in a Dataset. Primitive types (Int, String, etc) and Product types (case classes) are supported by importing spark.implicits._ Support for serializing other types will be added in future releases.](https://forums.databricks.com/questions/13772/apache-spark-210-question-in-spark-sql.html)
 - case class 使用时遇到的问题，放在main方法作用域外面，否则容易出现奇奇怪怪的错
 - [What does setMaster `local[*]` mean in spark?](https://stackoverflow.com/questions/32356143/what-does-setmaster-local-mean-in-spark)

## 资料
 - [极客学院](http://wiki.jikexueyuan.com/list/scala/)
 - [Scala Doc](https://docs.scala-lang.org)
 - [Some Scala Blog Geeksforgeeks](https://www.geeksforgeeks.org/scala-functions-call-by-name/)
 - [Some Scala Blog alvinalexander](https://alvinalexander.com/scala/how-to-add-update-remove-elements-immutable-maps-scala)
 - [allaboutscala.com](http://allaboutscala.com/)
 - [Play Framework](https://doron.gitbooks.io/play-doc-zh/2.4/gettingStarted/06_Play_Tutorials.html)
 - [过往记忆的大数据](https://wemp.app/accounts/9228fadf-eedf-468f-b68a-8c2f69fd1f13)
 
## 课程
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
 - [深入浅出Spark的Checkpoint机制](https://www.jianshu.com/p/a75d0439c2f9)
 
## Spark Learning
 - [Spark](https://github.com/apache/spark)
 - [简单了解](https://juejin.im/post/5a73c8386fb9a0635e3cafaa)
 - [Spark SQL](https://jaceklaskowski.gitbooks.io/mastering-spark-sql)
   - The Internals of Spark SQL
   - FILE-BASED DATA SOURCES,  KAFKA DATA SOURCE, AVRO DATA SOURCE, HIVE DATA SOURCE
   - DEVELOPING SPARK SQL APPLICATIONS 
 - Spark UI
 
 
 
 

 
 


