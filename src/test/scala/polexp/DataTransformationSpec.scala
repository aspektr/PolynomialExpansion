package polexp

import org.scalatest.{Matchers, WordSpec}

class DataTransformationSpec extends WordSpec with Matchers {
  "DataTransformation.divideExpression" should {
    "divide an expression by 2 parts where one part is short" in {
      val input = "(1x)(2x^-2+1)"
      val expected = Vector("1x", "2x^-2+1")
      expected should ===(DataTransformation.divideExpressionByBrackets(input))
    }
    "divide an expression by 3 parts" in {
      val input = "(1x)(2x^-2+1)(5y^3-4x^2)"
      val expected = Vector("1x", "2x^-2+1", "5y^3-4x^2")
      expected should ===(DataTransformation.divideExpressionByBrackets(input))
    }
  }

  "DataTransformation.splitIntoElements" should {
    "split string representation of polynomial into single elements" in {
      val polynomial = "-2x^3y^2+4x^2+5x-42"
      val expected = Vector("-2x^3y^2", "+4x^2", "+5x^1", "-42")
      expected should ===(DataTransformation.splitStrExprIntoElements(polynomial))
    }
    "correctly split single positive element" in {
      val polynomial = "4x^2"
      val expected = Vector("4x^2")
      expected should ===(DataTransformation.splitStrExprIntoElements(polynomial))
    }
    "correctly split single negative element" in {
      val polynomial = "-2x^3"
      val expected = Vector("-2x^3")
      expected should ===(DataTransformation.splitStrExprIntoElements(polynomial))
    }
    "correctly split single element with negative degree" in {
      val polynomial = "x^-3"
      val expected = Vector("1x^-3")
      expected should ===(DataTransformation.splitStrExprIntoElements(polynomial))
    }
    "return digits only" in {
        val elem = "-4.2x^2y^6"
        val expected = Vector("-4.2", "2", "6")
        expected should ===(DataTransformation.splitStrExprIntoElements(elem)(regex = "[+-\\\\d]+"))
    }
    "return -1 if expressions starts from - and variable" in {
        val elem = "-x^2"
        val expected = Vector("-1", "2")
        expected should ===(DataTransformation.splitStrExprIntoElements(elem)(regex = "[+-\\\\d]+"))
    }
    "return 1 if expressions starts from variable only" in {
        val elem = "x^2"
        val expected = Vector("1", "2")
        expected should ===(DataTransformation.splitStrExprIntoElements(elem)(regex = "[+-\\\\d]+"))
    }
    "return letters only" in {
          val elem = "-4.2x^2y^2zb^6"
          val expected = Vector("x", "y", "z", "b")
          expected should ===(DataTransformation.splitStrExprIntoElements(elem)( regex ="[a-z]?"))
    }
    }

  "DataTransformation.addMissedDegrees" should {
    "add 1 degree everywhere if there is no one" in {
      val expr = "-xyz"
      val expected = "-x^1y^1z^1"
      expected should ===(DataTransformation.addMissedDegrees(expr))
    }
    "add 1 degree everywhere if there is no one (positive)" in {
      val expr = "+xyz"
      val expected = "+x^1y^1z^1"
      expected should ===(DataTransformation.addMissedDegrees(expr))
    }
    "add 1 degree everywhere if there is no one (negative coeff)" in {
      val expr = "-32xyz"
      val expected = "-32x^1y^1z^1"
      expected should ===(DataTransformation.addMissedDegrees(expr))
    }
    "add 1 degree everywhere if there is no one (degree in the middle)" in {
      val expr = "-32xy^2z"
      val expected = "-32x^1y^2z^1"
      expected should ===(DataTransformation.addMissedDegrees(expr))
    }
    "add 1 degree everywhere if there is no one (degree in the back)" in {
      val expr = "-32xyz^3"
      val expected = "-32x^1y^1z^3"
      expected should ===(DataTransformation.addMissedDegrees(expr))
    }
    "add 1 degree everywhere if there is no one (negative degree in the back)" in {
      val expr = "-xyz^-3"
      val expected = "-x^1y^1z^-3"
      expected should ===(DataTransformation.addMissedDegrees(expr))
    }
    "add 1 degree for one positive variable" in {
      val expr = "1x"
      val expected = "1x^1"
      expected should ===(DataTransformation.addMissedDegrees(expr))
    }
  }

  "DataTransformation.toProduct" should {
    "create product from string literal" in {
      val expr = "2x^4y^3"
      val expected = Monomial(2d, Map("x"->4, "y"->3))
      expected should ===(DataTransformation.toProduct(expr))
    }
    "create product from string literal with positive coeff" in {
      val expr = "+2.3x^4y^3"
      val expected = Monomial(2.3d, Map("x"->4, "y"->3))
      expected should ===(DataTransformation.toProduct(expr))
    }
    "create product from string literal with negative coeff" in {
      val expr = "+2x^4y^3"
      val expected = Monomial(2d, Map("x"->4, "y"->3))
      expected should ===(DataTransformation.toProduct(expr))
    }
    "create product from string literal without coeff" in {
      val expr = "x^4y^3"
      val expected = Monomial(1d, Map("x"->4, "y"->3))
      expected should ===(DataTransformation.toProduct(expr))
    }
    "create product from string literal without coeff(positive)" in {
      val expr = "+x^4y^3"
      val expected = Monomial(1d, Map("x"->4, "y"->3))
      expected should ===(DataTransformation.toProduct(expr))
    }
    "create product from string literal without coeff(negative)" in {
      val expr = "-x^4y^3"
      val expected = Monomial(-1d, Map("x"->4, "y"->3))
      expected should ===(DataTransformation.toProduct(expr))
    }
    "create product from string literal with negative degree" in {
      val expr = "-x^-4y^-3"
      val expected = Monomial(-1d, Map("x"->(-4), "y"->(-3)))
      expected should ===(DataTransformation.toProduct(expr))
    }
    "create product from string literal (number only)" in {
      val expr = "-3"
      val expected = Monomial(-3d, Map.empty)
      expected should ===(DataTransformation.toProduct(expr))
    }
    "create product from string literal without degrees" in {
      val expr = "-xyz"
      val expected = Monomial(-1d, Map("x"->1, "y"->1, "z"->1))
      expected should ===(DataTransformation.toProduct(expr))
    }
  }
}
