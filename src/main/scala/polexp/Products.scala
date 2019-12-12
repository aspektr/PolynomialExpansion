package polexp

class Products(val source: Vector[Product]) {
  def *(that: Vector[Product]): Vector[Product] =
    for {
      l <- source
      r <- that
    } yield l*r

  def group: Map[Map[String, Int], Double] =
    source.foldLeft(Map.empty[Map[String, Int], Double]) { case (acc, monomial) =>
      val coefficient: Double = acc.getOrElse(monomial.letters, 0)
      val newCoefficient = coefficient + monomial.coefficient
      acc + (monomial.letters -> newCoefficient)
      }

  def shrink: Vector[Monomial] = {
    val groupedMonomial = source.group
    groupedMonomial.toVector.map { case (k, v) =>
      Monomial(v, k)
    }
  }


}
