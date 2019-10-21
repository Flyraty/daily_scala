/*
 * Scala 中的并发处理， Future 与 Promise,
 * TODO: Future 与组合器结合使用
 */
package scala

import scala.util.{Success, Failure}
import scala.concurrent.future
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random
import scala.util.Try


object future_oo {

  type CoffeeBeans = String
  type GroundCoffee = String
  case class Water(temperature:Int)
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  case class GrindingException(msg:String) extends Exception(msg)
  case class FrothingException(msg:String) extends Exception(msg)
  case class WaterBoilingException(msg:String) extends Exception(msg)
  case class BrewingException(msg:String) extends Exception(msg)

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println("heating the water now")
    Thread.sleep(Random.nextInt(2000))
    println("hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
    println("milk frothing system engaged!")
    Thread.sleep(Random.nextInt(2000))
    println("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    println("happy brewing :)")
    Thread.sleep(Random.nextInt(2000))
    println("it's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  def prepareCappuccino(): Future[Cappuccino] = {
    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(20))
    val frothedMilk = frothMilk("milk")
    for {
      ground <- groundCoffee
      water <- heatedWater
      foam <- frothedMilk
      espresso <- brew(ground, water)
    } yield combine(espresso, foam)
  }




  def main(args: Array[String]): Unit = {
//    prepareCappuccino()

    grind("baked beans").onComplete {
      case Success(ground) => println(s"got my $ground")
      case Failure(ex) => println("This grinder needs a replacement, seriously!")
    }

  }

}
