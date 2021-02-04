package com.revature.scalawags.project3.herdimmunity

import com.revature.scalawags.project3.herdimmunity.CSVPullerAndParser._
import com.revature.scalawags.project3.herdimmunity.HerdImmunityCalculator._


object Main {
    def main(args: Array[String]) {
        pullCDCCSV()
        val analysis = parseCDCCSV()
        daysUntilHerdImmunity(analysis.population,analysis.peopleFullyVaccinated,analysis.newVaccinationsSmoothed,herdImmunityPercent=.75)

    }
}