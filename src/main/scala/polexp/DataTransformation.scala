package polexp

import scala.annotation.tailrec

object DataTransformation {

  def isLetter(sym: Char): Boolean = if (sym >= 'a' && sym <= 'z') true else false

  def divideExpressionByBrackets(expr: String)(implicit regex: String = "[()]"): Vector[String] =
    expr.split(regex).filter(_ != "").toVector

  def addMissedDegrees(expr: String): String = {
    @tailrec
    def helper(symbols: List[Char], acc: String): String =
      if (symbols.isEmpty) acc
      else if (!isLetter(symbols.head)) helper(symbols.tail, acc + symbols.head)
      else if (symbols.tail.isEmpty && isLetter(symbols.head)) helper(symbols.tail, acc + symbols.head ++ "^1")
      else if (isLetter(symbols.head) && symbols.tail.head != '^') helper(symbols.tail, acc + symbols.head ++ "^1")
      else helper(symbols.tail, acc + symbols.head)

    helper(expr.toList, "")
  }

  def splitStrExprIntoElements(expr: String)
                              (implicit regex: String =
                           "^[-]?(\\d*[a-z]*\\^{1}[-]?\\d*)+|([+-]?\\d*[a-z]*\\^{1}[-]?\\d*)|([+-]?\\d*[a-z]*)"
                          ): Vector[String] = {
    val modifiedExpr = addMissedDegrees(expr)
    regex.r.findAllIn(if (isLetter(modifiedExpr.charAt(0))) "1" ++ modifiedExpr else modifiedExpr)
      .toVector
      .filter(_ != "")
      .map(elem => if (elem == "-") "-1" else if (elem == "+") "1" else elem)
  }



  def toProduct(expr: String)(implicit digitRegex: String = "[+-\\\\d]+", letterRegex:String = "[a-z]?"): Product = {
    val (digits, letters) = (splitStrExprIntoElements(expr)(digitRegex), splitStrExprIntoElements(expr)(letterRegex))
    val (coeff, degrees) = (digits.head.toDouble, digits.tail.map(_.toInt))
    Monomial(coeff, (letters zip degrees).toMap)
  }

  val splitIntoElements: Vector[String] => Vector[Vector[String]] = (expr: Vector[String]) =>
    expr map splitStrExprIntoElements

  val convertEachToProduct: Vector[Vector[String]] => Vector[Vector[Product]] = (seqOfelements: Vector[Vector[String]]) =>
    seqOfelements.map(_ map toProduct)

  val openBrackets: Vector[Vector[Product]] => Vector[Product] = (examinedExpession: Vector[Vector[Product]]) =>
    examinedExpession.foldLeft(Vector[Product](One)) {case (acc, monomial) =>
    acc * monomial
  }

  val addUpMonomials: Vector[Product] => Vector[Monomial] = (polynomialAfterOpenBrackets: Vector[Product]) =>
    polynomialAfterOpenBrackets.shrink

  val sortByDegree: Vector[Monomial] => Vector[Monomial] = (shrinkPolynomial: Vector[Monomial]) =>
    shrinkPolynomial.sorted(Ordering[Monomial].reverse)

  val prepareToPrint: Vector[Monomial] => String = (orderedPolynomial: Vector[Monomial]) =>
  if (orderedPolynomial.mkString("").startsWith("+")) orderedPolynomial.mkString("").substring(1)
  else orderedPolynomial.mkString("")
}
