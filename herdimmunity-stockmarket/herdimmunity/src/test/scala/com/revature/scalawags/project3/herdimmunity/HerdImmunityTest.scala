package com.revature.scalawags.project3.herdimmunity

import com.github.nscala_time.time.Imports._
import org.scalatest.flatspec.AnyFlatSpec

class HerdImmunityTest extends AnyFlatSpec {

  val data = AnalysisData(
    date = "2021-02-01".toDateTime,
    peopleVaccinated = 6000,
    peopleFullyVaccinated = 2000,
    newVaccinationsSmoothed = 40,
    population = 10000
  )
  val otherZero = data.copy(newVaccinationsSmoothed = 0)
  val otherNeg = data.copy(newVaccinationsSmoothed = -1)

  "Days Remaining" should "return 139 from test data" in {
    assert(HerdImmunity.daysRemaining(data).getOrElse(0) == 139)
  }
  it should "return None when newVaccinationsSmoothed <= 0" in {
    assert(HerdImmunity.daysRemaining(otherZero) == None)
    assert(HerdImmunity.daysRemaining(otherNeg) == None)
  }

  "Exact Date" should "return Jun 20, 2021 (as DateTime object)" in {
    assert(HerdImmunity.exactDate(139, data.date) == "2021-6-20".toDateTime)
  }

  "Pretty Date" should "return 'Sunday, June 20, 2021'" in {
    assert(HerdImmunity.prettyDate("2021-6-20".toDateTime) == "Sunday, June 20, 2021")
  }
}