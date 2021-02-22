package com.revature.scalawags.project3.herdimmunity

import com.github.nscala_time.time.Imports._

object HerdImmunity {
  // The user story for this module assumes 99% effectiveness for the vaccine.
  val VaccinationEfficacy = .99
  
  /** Returns the number of days until reaching herd immunity, based on total 
    * population, population already vaccinated, vaccination administration rate, 
    * and target vaccinated percentage for the pandemic.
    * 
    * @return `None` if `data.newVaccinationsSmoothed == 0`
    */
  def daysRemaining(data: AnalysisData, herdImmunityPercent: Double = .75): Option[Int] = {
    val remaining = (data.population * herdImmunityPercent) - data.peopleFullyVaccinated
    val effectiveDaily = data.newVaccinationsSmoothed * VaccinationEfficacy
    
    Option(math.ceil(remaining / effectiveDaily).toInt)
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
