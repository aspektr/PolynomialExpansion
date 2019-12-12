package polexp

import polexp.PolyExpansionApp.result

object IO {
  val greetMsg = """
                   |
                   |*************************************************************
                   |Please, type the polynomial for expansion.
                   |Pattern must be:
                   |([-][num][[letter][[^][-][num}]]...[[+/-][num]]...)(copy)
                   |
                   |where:
                   |* "[]" represents optional features
                   |* "{}" represents mandatory features
                   |* "num" represents integers
                   |* "letter" represents letters such as "x".
                   |*************************************************************
                   |BE CAREFUL! There is no user validation and error handling.
                   |*************************************************************
                   |You also can pick up options prepared in advance.
                   |To do this type the number of option and press Enter
                   | Options:
                   | 1. (1x)(2x^-2+1)
                   | 2. (-1x^3)(3x^3+2)
                   | 3. (a-b)(a-b)
                   | 4. (a-b)(a-b)(a-b)
                   |*************************************************************
                   |""".stripMargin


  def readUserInput(): String = {
    println(greetMsg)
    val inp = scala.io.StdIn.readLine()
    inp match {
      case "1" => "(1x)(2x^-2+1)"
      case "2" => "(-1x^3)(3x^3+2)"
      case "3" => "(a-b)(a-b)"
      case "4" => "(a-b)(a-b)(a-b)"
      case _ => inp
    }
  }

  def printAnswer(result: String): Unit =
    println(s"The answer is:\n $result")
}
