package utilites

case class EconomicsData(
  name: String,
  year: Int,
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
   country: String = null,
   date: String,
   total_cases: Int = 0,
   new_cases: Int = 0,
   new_cases_smoothed: Double = 0,
   total_deaths: Int = 0,
   new_deaths: Int = 0,
   new_deaths_smoothed: Double = 0,
   total_cases_per_million: Double = 0,
   new_cases_per_million: Double = 0,
   new_cases_smoothed_per_million: Double = 0,
   total_deaths_per_million: Double = 0,
   new_deaths_per_million: Double = 0,
   new_deaths_smoothed_per_million: Double = 0,
   reproduction_rate: Double = 0,
   icu_patients: Int = 0,
   icu_patients_per_million: Double = 0,
   hosp_patients: Int = 0,
   hosp_patients_per_million: Double = 0,
   weekly_hosp_admissions: Int = 0,
   weekly_hosp_admissions_per_million: Double = 0,
   total_tests: Int = 0,
   new_tests: Int = 0,
   total_tests_per_thousand: Double = 0,
   new_tests_per_thousand: Double = 0,
   new_tests_smoothed: Double = 0,
   new_tests_smoothed_per_thousand: Double = 0,
   tests_per_case: Double = 0,
   positive_rate: Double = 0,
   tests_units: String = null,
   stringency_index: Double = 0
)

object Util {
  def getCovidSchema: Seq[String] =
       Seq("DATE", "COUNTRY", "TOTAL_CASES", "NEW_CASES", "NEW_CASES_SMOOTHED", "TOTAL_DEATHS", "NEW_DEATHS", "NEW_DEATHS_SMOOTHED", "TOTAL_CASES_PER_MILLION", "NEW_CASES_PER_MILLION", "NEW_CASES_SMOOTHED_PER_MILLION", "TOTAL_DEATHS_PER_MILLION", "NEW_DEATHS_PER_MILLION12", "NEW_DEATHS_PER_MILLION13", "REPRODUCTION_RATE", "ICU_PATIENTS", "ICU_PATIENTS_PER_MILLION", "HOSP_PATIENTS", "HOSP_PATIENTS_PER_MILLION", "WEEKLY_ICU_ADMISSIONS", "WEEKLY_ICU_ADMISSIONS_PER_MILLION", "WEEKLY_HOSP_ADMISSIONS", "WEEKLY_HOSP_ADMISSIONS_PER_MILLION", "TOTAL_TESTS", "NEW_TESTS", "NEW_TESTS_SMOOTHED", "TOTAL_TESTS_PER_THOUSAND", "NEW_TESTS_SMOOTHED_PER_THOUSAND", "TESTS_PER_CASE", "POSITIVE_RATE", "TESTS_UNITS", "STRINGENCY_INDEX")

  def getEconSchema: Seq[String] =
      Seq("name", "year", "gdp_constPrices", "gdp_constPrices_delta", "gdp_currentPrices", "gdp_currentPrices_usd", "gdp_currentPrices_ppp", "gdp_deflator", "gdp_perCap_constPrices", "gdp_perCap_constPrices_ppp", "gdp_perCap_currentPrices", "gdp_perCap_currentPrices_usd", "gdp_perCap_currentPrices_ppp", "output_gap_pGDP", "gdp_ppp_frac_of_total_world", "implied_ppp", "total_investment", "gross_national_savings", "inflation_avgConsumerPrices", "inflation_avgConsumerPrices_delta", "inflation_eopConsumerPrices", "inflation_eopConsumerPrices_delta", "six_month_LIBOR", "vol_imports_goods_and_services_delta", "vol_imports_goods_delta", "vol_exports_goods_and_services_delta", "vol_exports_goods_delta", "unemployment_rate", "employed_persons", "population", "government_revenue_currency", "government_revenue_percent", "government_total_expenditure_currency", "government_total_expenditure_percent", "government_net_lb_currency", "government_net_lb_percent", "government_structural_balance_currency", "government_structural_balance_percent_pGDP", "government_primary_net_lb_currency", "government_primary_net_lb_percent", "government_net_debt_currency", "government_net_debt_percent", "government_gross_debt_currency", "government_gross_debt_percent", "gdp_of_fiscal_year", "current_account_balance_usd", "current_account_balance_percentGDP")

}