package utilites

case class EconomicsData(
  name: String,
  year: Double,
  gdp_constPrices: Long = 0,
  gdp_constPrices_delta: Double = 0,
  gdp_currentPrices: Double = 0,
  gdp_currentPrices_usd: Double = 0,
  gdp_currentPrices_ppp: Double = 0, //ppp = purchasing power parity
  gdp_deflator: Double = 0,
  gdp_perCap_constPrices: Double = 0,
  gdp_perCap_constPrices_ppp: Double = 0,
  gdp_perCap_currentPrices: Double = 0,
  gdp_perCap_currentPrices_usd: Double = 0,
  gdp_perCap_currentPrices_ppp: Double = 0,
  output_gap_pGDP: Double = 0,
  gdp_ppp_frac_of_total_world: Double = 0,
  implied_ppp: Double =
  0, //National Currency per current international dollar
  total_investment: Double = 0, //as percent of GDP
  gross_national_savings: Double = 0, //as percent of GDP
  inflation_avgConsumerPrices: Double = 0,
  inflation_avgConsumerPrices_delta: Double = 0,
  inflation_eopConsumerPrices: Double = 0,
  inflation_eopConsumerPrices_delta: Double = 0,
  six_month_LIBOR: Double = 0,
  vol_imports_goods_and_services_delta: Double = 0,
  vol_imports_goods_delta: Double = 0,
  vol_exports_goods_and_services_delta: Double = 0,
  vol_exports_goods_delta: Double = 0,
  unemployment_rate: Double = 0,
  employed_persons: Long = 0,
  population: Double = 0,
  government_revenue_currency: Double = 0,
  government_revenue_percent: Double = 0,
  government_total_expenditure_currency: Double = 0,
  government_total_expenditure_percent: Double = 0,
  government_net_lb_currency: Double = 0,
  government_net_lb_percent: Double = 0,
  government_structural_balance_currency: Double = 0,
  government_structural_balance_percent_pGDP: Double = 0,
  government_primary_net_lb_currency: Double = 0,
  government_primary_net_lb_percent: Double = 0,
  government_net_debt_currency: Double = 0,
  government_net_debt_percent: Double = 0,
  government_gross_debt_currency: Double = 0,
  government_gross_debt_percent: Double = 0,
  gdp_of_fiscal_year: Double = 0,
  current_account_balance_usd: Double = 0,
  current_account_balance_percentGDP: Double = 0
)

case class CountryStats(
   date: String,
   country: String = null,
   total_cases: Double = 0,
   new_cases: Double = 0,
   new_cases_smoothed: Double = 0,
   total_deaths: Double = 0,
   new_deaths: Double = 0,
   new_deaths_smoothed: Double = 0,
   total_cases_per_million: Double = 0,
   new_cases_per_million: Double = 0,
   new_cases_smoothed_per_million: Double = 0,
   total_deaths_per_million: Double = 0,
   new_deaths_per_million: Double = 0,
   new_deaths_smoothed_per_million: Double = 0,
   reproduction_rate: Double = 0,
   icu_patients: Double = 0,
   icu_patients_per_million: Double = 0,
   hosp_patients: Double = 0,
   hosp_patients_per_million: Double = 0,
   weekly_hosp_admissions: Double = 0,
   weekly_hosp_admissions_per_million: Double = 0,
   total_tests: Double = 0,
   new_tests: Double = 0,
   total_tests_per_thousand: Double = 0,
   new_tests_per_thousand: Double = 0,
   new_tests_smoothed: Double = 0,
   new_tests_smoothed_per_thousand: Double = 0,
   tests_per_case: Double = 0,
   positive_rate: Double = 0,
   tests_units: String = null,
   stringency_index: Double = 0
)

object util {
  def recast(): Seq[String] = Seq(
    "year",
    "total_cases",
    "new_cases"
  )
}