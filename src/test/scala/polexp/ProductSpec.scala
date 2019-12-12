package polexp

import org.scalatest.{WordSpec, Matchers}

class ProductSpec extends WordSpec with Matchers {
  "Monomial class" should {
    "multiply its instance with the same variable" in  {
      val left = Monomial(2, Map("x"->2))
      val right = Monomial(3, Map("x"->3))
      val expected = Monomial(6, Map("x"->5))
      expected should ===(left * right)
    }
    "multiply its instance with the different variables" in  {
      val left = Monomial(2, Map("x"->2, "y"->3))
      val right = Monomial(3, Map("x"->3))
      val expected = Monomial(6, Map("x"->5, "y"->3))
      expected should ===(left * right)
    }
    "add its instance" in {
      val left = Monomial(2, Map("x"->2, "y"->3))
      val right = Monomial(8, Map("x"->2, "y"->3))
      val expected =  Monomial(10, Map("x"->2, "y"->3))
      expected should ===(left + right)
    }
  }
  "Products class" should {
    "multiply 2 list of Products (a, -b)*(a,-b)" in {
      val left = Vector(Monomial(1d, Map("a"->1)), Monomial(-1d, Map("b"->1)))
      val right = Vector(Monomial(1d, Map("a"->1)), Monomial(-1d, Map("b"->1)))
      val expected =  Vector(
        Monomial(1d, Map("a"->2)),
        Monomial(-1d, Map("a"->1, "b"->1)),
        Monomial(-1d, Map("a"->1, "b"->1)),
        Monomial(1d, Map("b"->2)))
      expected should ===(left * right)
    }
    "multiply 2 list of Products (a, b)*(a, b)" in {
      val left = Vector(Monomial(1d, Map("a"->1)), Monomial(1d, Map("b"->1)))
      val right = Vector(Monomial(1d, Map("a"->1)), Monomial(1d, Map("b"->1)))
      val expected =  Vector(
        Monomial(1d, Map("a"->2)),
        Monomial(1d, Map("a"->1, "b"->1)),
        Monomial(1d, Map("a"->1, "b"->1)),
        Monomial(1d, Map("b"->2)))
      expected should ===(left * right)
    }
    "multiply 2 list of Products (1x)*(2x^-2, 1)" in {
      val left = Vector(Monomial(1d, Map("x"->1)))
      val right = Vector(Monomial(2d, Map("x"->(-2))), Monomial(1d, Map.empty))
      val expected =  Vector(
        Monomial(2d, Map("x"->(-1))),
        Monomial(1d, Map("x"->1)))
      expected should ===(left * right)
    }
    "return the same if we multiply by One" in {
      val left = Vector(One)
      val right = Vector(Monomial(2d, Map("x"->(-2))), Monomial(1d, Map.empty))
      val expected =  Vector(Monomial(2d, Map("x"->(-2))), Monomial(1d, Map.empty))
      expected should ===(left * right)
    }
    "group list of Monomials by combining additive Monomials" in {
      val listOfProfuct = Vector(
        Monomial(2d, Map("x"->(-2))), Monomial(1d, Map.empty),
        Monomial(-3d, Map("x"->(-2))), Monomial(5d, Map.empty)
      )
      val expected = Map(
        Map("x"->(-2)) -> (-1),
        Map.empty -> 6d,
      )
      expected should ===(listOfProfuct.group)
    }
    "reduce list of Monomials by combining additive Monomials" in {
      val listOfProfuct = Vector(
        Monomial(2d, Map("x"->(-2))), Monomial(1d, Map.empty),
        Monomial(-3d, Map("x"->(-2))), Monomial(5d, Map.empty)
      )
      val expected = Vector(
        Monomial(-1d, Map("x"->(-2))),
        Monomial(6d, Map.empty)
      )
      expected should ===(listOfProfuct.shrink)
    }
  }
  "Monomial class (comparison)" should {
    "compare 2 Monomials which are numbers only (equality)" in {
      val left = Monomial(1d, Map.empty)
      val right = Monomial(1d, Map.empty)
      val expected = true
      expected should ===(left == right)
    }
    "compare 2 Monomials which are numbers only (greater than)" in {
      val left = Monomial(5d, Map.empty)
      val right = Monomial(1d, Map.empty)
      val expected = true
      expected should ===(left > right)
    }
    "compare 2 Monomials which are numbers only (less than)" in {
      val left = Monomial(1d, Map.empty)
      val right = Monomial(5d, Map.empty)
      val expected = true
      expected should ===(left < right)
    }
    "compare 2 Monomials with the same coef (greater than)" in {
      val left = Monomial(5d, Map("x"->2, "y"->3))
      val right = Monomial(5d, Map("x"->2, "y"->2, "z"->2))
      val expected = true
      expected should ===(left > right)
    }
  }
}
