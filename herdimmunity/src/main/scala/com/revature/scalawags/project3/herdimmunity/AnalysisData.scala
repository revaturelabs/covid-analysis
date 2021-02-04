package com.revature.scalawags.project3.herdimmunity

import com.github.nscala_time.time.Imports._

case class AnalysisData(
  date: DateTime, 
  peopleVaccinated: Int, 
  peopleFullyVaccinated: Int, 
  newVaccinationsSmoothed: Int, 
  population: Int
)
