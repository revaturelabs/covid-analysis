package com.revature.scalawags.project3.herdimmunity

import org.scalatest.flatspec.AnyFlatSpec

class HerdImmunityCalculatorTest extends AnyFlatSpec {
  
  val totalPopulation = 10000
  val vaccinatedPopulation = 2000
  val dailyAdministered = 40

  "Days Until Herd Immunity" should "return 190" in {
    assert(HerdImmunityCalculator.daysUntilHerdImmunity(
      totalPopulation,
      vaccinatedPopulation,
      dailyAdministered
    ) == 190)
  }

  "Herd Immunity Date" should "return 8/11/2021" in {
    assert(HerdImmunityCalculator.dateOfHerdImmunity(190) == "8/11/2021")
  }
}