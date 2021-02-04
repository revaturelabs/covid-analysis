package com.revature.scalawags.project3.stockmarket

import org.scalatest.flatspec.AnyFlatSpec

class CompositeIndexChangeCalculatorTest extends AnyFlatSpec {
  val sumOfIndexCapBaseWeek: Double = 100.00
  val sumOfIndexCapNextWeek: Double = 120.00
  val doubleNumber = 12.345123

    "Weekly Change" should "return 20.00" in {
    assert(CompositeIndexChangeCalculator.weeklyChange(
      sumOfIndexCapBaseWeek,
      sumOfIndexCapNextWeek
    ) == 20)
    }
    
    "Round At 2 Decimal Places" should "return 12.35" in {
    assert(CompositeIndexChangeCalculator.roundAt2(doubleNumber) == 12.35)
  }

}