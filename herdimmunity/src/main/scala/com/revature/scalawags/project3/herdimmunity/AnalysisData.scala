package com.revature.scalawags.project3.herdimmunity

import com.github.nscala_time.time.Imports._

case class AnalysisData(
  date: DateTime, 
  peopleVaccinated: Double, 
  peopleFullyVaccinated: Double, 
  newVaccinationsSmoothed: Double, 
  population: Double
)
