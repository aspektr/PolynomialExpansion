package polexp

import scala.annotation.tailrec

sealed trait Product {

  def coefficient: Double
  def letters: Map[String, Int]

  def *(that: Product): Product = {
    val multiplier = coefficient * that.coefficient
    val letters = this.letters ++ that.letters.map[String, Int] { case (letter, degree) =>
      letter -> (degree + this.letters.getOrElse(letter, 0))
    }
    Monomial(multiplier, letters)
  }

  def +(that: Product): Product = {
    val multiplier = coefficient + that.coefficient
    Monomial(multiplier, letters)
  }

  override def toString: String = {
    val formattedCoefficient = if (coefficient % 1 == 0) String.format("%d", coefficient.toInt)
    else String.format("%s", coefficient)
    (if (coefficient == 1) "+"
    else if (coefficient == -1) "-"
    else if (coefficient > 0) "+" + formattedCoefficient
    else formattedCoefficient) + letters.foldLeft("") { case (acc, elem) =>
      acc + elem._1 + (if (elem._2 == 1) "" else "^" + elem._2)
    }
  }
}

object Product {
  implicit def listOfProductsToProducts(input: Vector[Product]): Products = new Products(input)
}

final case class Monomial(coefficient: Double, letters: Map[String, Int]) extends Product with Ordered[Monomial] {
  override def compare(that: Monomial): Int = {
    Monomial.compareLetters(this.letters, that.letters) match {
      case 0 =>  Monomial.compareCoefficient(this.coefficient, that.coefficient)
      case 1 => 1
      case -1 => -1
    }
  }
}

object Monomial {

  def isAddictive(left: Product, right: Product): Boolean =
    if (compareLetters(left.letters, right.letters) == 0) true else false

  private def compareCoefficient(left: Double, right: Double): Int =
    if (left == right) 0
    else if (left > right) 1
    else -1

  private def compareLetters(left: Map[String, Int], right: Map[String, Int]): Int =
    compareSeqByElements(left.values.toSeq.sorted(Ordering.Int.reverse),
                         right.values.toSeq.sorted(Ordering.Int.reverse))

  @tailrec
  private def compareSeqByElements(left: Seq[Int], right: Seq[Int]): Int =
    if (left.isEmpty && right.isEmpty) 0
    else if (left.isEmpty) -1
    else if (right.isEmpty) 1
    else if (left.head > right.head) 1
    else if (left.head < right.head) -1
    else compareSeqByElements(left.tail, right.tail)
}

case object One extends Product {
  val coefficient = 1d
  val letters = Map.empty[String, Int]
  implicit def oneToProducts(input: Vector[One.type]): Products = new Products(input)
}
