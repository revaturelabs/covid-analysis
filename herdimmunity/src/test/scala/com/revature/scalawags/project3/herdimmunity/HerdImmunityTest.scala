package com.revature.scalawags.project3.herdimmunity

import com.github.nscala_time.time.Imports._
import org.scalatest.flatspec.AnyFlatSpec

class HerdImmunityTest extends AnyFlatSpec {

  val dataDate = "2021-02-01".toDateTime
  val totalPopulation = 10000
  val vaccinatedPopulation = 2000
  val dailyAdministered = 40

  "Days Remaining" should "return 190" in {
    assert(HerdImmunity.daysRemaining(
      totalPopulation,
      vaccinatedPopulation,
      dailyAdministered
    ) == 190)
  }

  "Exact Date" should "return August 10, 2021 (as DateTime object)" in {
    assert(HerdImmunity.exactDate(190, dataDate) == "2021-8-10".toDateTime)
  }

  "Pretty Date" should "return 'Tuesday, August 10, 2021'" in {
    assert(HerdImmunity.prettyDate("2021-8-10".toDateTime) == "Tuesday, August 10, 2021")
  }
}