package com.revature.scalawags.project3.herdimmunity

import com.github.nscala_time.time.Imports._

object HerdImmunity {
  val VaccinationEfficacy = .99
  
  /** Returns the number of days until reaching herd immunity, based on total 
    * population, population already vaccinated, vaccination administration rate, 
    * and target vaccinated percentage for the pandemic.
    */
  def daysRemaining(
    totalPopulation: Int, 
    vaccinatedPopulation: Int, 
    dailyAdministered: Int,
    herdImmunityPercent: Double = .75
  ): Int = {
    val targetPop = totalPopulation * herdImmunityPercent
    val effectiveDaily = dailyAdministered * VaccinationEfficacy
    
    math.ceil(targetPop / effectiveDaily).toInt
  }

  /** Returns the date achieving herd immunity based on the number of days from
    * the current date that herd immunity will be reached.
    * 
    * @param days The number of days until herd immunity is reached.
    * @param from The date the data was pulled on.
    */
  def exactDate(days: Int, from: DateTime = DateTime.now()): DateTime = {
    from + days.days
  }

  /** Returns a print-ready string containing weekday, month, month day, and year
    * of the date passed in.
    */
  def prettyDate(date: DateTime): String = {
    s"${date.dayOfWeek.text}, ${date.monthOfYear.text} ${date.dayOfMonth.get()}, " +
      s"${date.year.get()}"
  }

  
}
