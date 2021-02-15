import com.github.mrpowers.spark.fast.tests.DatasetComparer
import firstRegionPeaks.Calculator
import org.scalatest.funspec.AnyFunSpec

class CalculatorSpecs extends AnyFunSpec with DatasetComparer {

  describe("Calculator") {
    describe("correlation()") {

      /** Correlation coefficient formulas are used to find how strong a relationship is between data.
        * The formulas return a value between -1 and 1, where:
        *        1 indicates a strong positive relationship.
        *        -1 indicates a strong negative relationship.
        *        A result of zero indicates no relationship at all.
        */
      it(
        "should return 1 for the correlation coefficient for two equal arrays"
      ) {
        val calc = Calculator()
        val (arr1: Array[Double], arr2: Array[Double]) =
          (Array(2.4d, 1.62d), Array(2.4d, 1.62d))
        assert(calc.correlation(arr1, arr2) == 1)
      }
      it(
        "should return -1 for Arrays with opposite values"
      ) {
        val calc = Calculator()
        val (arr1: Array[Double], arr2: Array[Double]) =
          (Array(300d, 212d), Array(-300d, -212d))
        assert(calc.correlation(arr1, arr2) == -1)
      }
      it("should produce NumberFormatException when empty array is passed") {
//        assertThrows[NumberFormatException] {}
        // TODO: please complete.
        assert(true)
      }
    }

  }
}
