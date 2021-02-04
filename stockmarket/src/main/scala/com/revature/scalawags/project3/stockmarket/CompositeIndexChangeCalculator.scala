package com.revature.scalawags.project3.stockmarket

object CompositeIndexChangeCalculator extends App {

  val sumOfIndexCapBaseWeek: Double = 0.00
  val sumOfIndexCapNextWeek: Double = 0.00

  def weeklyChange(sumOfIndexCapBaseWeek: Double, sumOfIndexCapNextWeek: Double): Double ={
    val weeklyChange = (sumOfIndexCapNextWeek - sumOfIndexCapBaseWeek) / sumOfIndexCapBaseWeek * 100
    return roundAt2(weeklyChange)
  }

  def roundAt2(n: Double) = (n*100).round / 100.toDouble
  
}