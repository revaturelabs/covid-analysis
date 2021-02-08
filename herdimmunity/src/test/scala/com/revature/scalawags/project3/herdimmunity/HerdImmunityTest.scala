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

  "Days Remaining" should "return 190" in {
    assert(HerdImmunity.daysRemaining(data).getOrElse(0) == 190)
  }

  "Exact Date" should "return August 10, 2021 (as DateTime object)" in {
    assert(HerdImmunity.exactDate(190, data.date) == "2021-8-10".toDateTime)
  }

  "Pretty Date" should "return 'Tuesday, August 10, 2021'" in {
    assert(HerdImmunity.prettyDate("2021-8-10".toDateTime) == "Tuesday, August 10, 2021")
  }
}