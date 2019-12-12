package polexp

import DataTransformation._
import IO._

object PolyExpansionApp extends App{

  val pipeline = divideExpressionByBrackets _ andThen
                 splitIntoElements andThen
                 convertEachToProduct andThen
                 openBrackets andThen
                 addUpMonomials andThen
                 sortByDegree andThen
                 prepareToPrint

  val result = pipeline(readUserInput())

  printAnswer(result)
}
