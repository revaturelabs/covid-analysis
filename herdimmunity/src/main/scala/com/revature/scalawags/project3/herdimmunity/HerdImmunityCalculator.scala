package com.revature.scalawags.project3.herdimmunity

object HerdImmunityCalculator {
  val VaccinationEfficacy = .99
  
  /** Returns the number of days until reaching herd immunity, based on total 
    * population, population already vaccinated, vaccination administration rate, 
    * and target vaccinated percentage for the pandemic.
    */
  def daysUntilHerdImmunity(
    totalPopulation: Int, 
    vaccinatedPopulation: Int, 
    dailyAdministered: Int,
    herdImmunityPercent: Double = .75
  ): Int = {
    0
  }

  /** Returns the date achieving herd immunity based on the number of days from
    * the current date that herd immunity will be reached.
    */
  def dateOfHerdImmunity(days: Int): String = {
    ""
  }
}
