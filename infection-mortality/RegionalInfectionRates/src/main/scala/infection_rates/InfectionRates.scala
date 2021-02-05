package infection_rates

import org.apache.spark.sql.functions.{bround, count, desc, when}
import org.apache.spark.sql.{SparkSession, Column, DataFrame}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.FileWriter
import java.io.File
import org.jsoup.Jsoup
import scalaj.http._
import sys.process._
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.services.s3.model.PutObjectRequest


/** Percentage of countries/regions with increasing COVID-19 Infection rate
  *
  */
object InfectionRates {
	
	// Class variables
	private val Africa = Array(
		"Algeria", "Angola",
		"Benin", "Botswana", "Burkina Faso", "Burundi",
		"Cameroon", "Cabo Verde", "Central African Republic",
		"Chad", "Comoros", "Côte d'Ivoire",
		"DRC", "Djibouti", "Egypt",
		"Equatorial Guinea", "Eritrea",
		"Ethiopia", "Gabon", "Gambia", "Ghana", "Guinea",
		"Guinea-Bissau", "Kenya", "Lesotho", "Liberia", "Libyan Arab Jamahiriya",
		"Madagascar", "Malawi", "Mali", "Mauritania", "Mauritius", "Mayotte", "Morocco",
		"Mozambique", "Namibia", "Niger", "Nigeria", "Republic of the Congo",
		"Reunion", "Rwanda", "Réunion", "Saint Helena", "Sao Tome and Principe",
		"Senegal", "Seychelles", "Sierra Leone", "Somalia", "South Africa",
		"South Sudan", "Sudan", "Swaziland", "Tanzania", "Togo",
		"Tunisia", "Uganda", "Western Sahara", "Zambia", "Zimbabwe"
	)

	private val Asia = Array(
		"Afghanistan", "Armenia",
		"Azerbaijan", "Bahrain", "Bangladesh", "Bhutan", "Brunei",
		"Myanmar", "Cambodia", "China", "Cyprus",
		"Timor-Leste", "Georgia", "Hong Kong", "India", "Indonesia",
		"Iran", "Iraq", "Israel", "Japan",
		"Jordan", "Kazakhstan", "Kuwait", "Kyrgyzstan", "Lao People's Democratic Republic",
		"Lebanon", "Macao", "Malaysia",
		"Maldives", "Mongolia", "Nepal", "North Korea", "Oman",
		"Pakistan", "Palestine", "Philippines", "Qatar", "Saudi Arabia", "Singapore",
		"S. Korea", "Sri Lanka", "Syrian Arab Republic",
		"Taiwan", "Tajikistan", "Thailand", "Turkey",
		"Turkmenistan", "UAE",
		"Uzbekistan", "Vietnam", "Yemen"
	)

	private val Europe = Array(
		"Albania", "Andorra", "Austria", "Belarus",
		"Belgium", "Bosnia", "Bulgaria", "Channel Islands",
		"Croatia", "Czechia", "Denmark", "Estonia",
		"Faroe Islands", "Finland", "France", "Germany",
		"Gibraltar", "Greece", "Holy See (Vatican City State)", "Hungary",
		"Iceland", "Ireland", "Italy", "Isle of Man",
		"Kosovo", "Latvia", "Liechtenstein", "Lithuania",
		"Luxembourg", "Macedonia", "Malta", "Moldova",
		"Monaco", "Montenegro", "Netherlands", "Norway",
		"Poland", "Portugal", "Romania", "Russia",
		"San Marino", "Slovakia", "Slovenia", "Spain",
		"Serbia", "Sweden", "Switzerland", "Ukraine",
		"UK"
	)

	private val Caribbean = Array(
		"Anguilla", "Antigua and Barbuda", "Aruba",
		"Bahamas", "Barbados", "Bermuda",
		"British Virgin Islands", "Caribbean Netherlands", "Cayman Islands",
		"Cuba", "Curaçao", "Dominica",
		"Dominican Republic", "Grenada",
		"Guadeloupe", "Haiti", "Jamaica",
		"Martinique", "Montserrat", "Netherlands Antilles",
		"Puerto Rico", "St. Barth", "Saint Martin",
		"Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
		"Sint Maarten", "Trinidad and Tobago", "Turks and Caicos Islands",
		"U.S. Virgin Islands"
	)

	private val Central_America = Array(
		"Belize", "Costa Rica", "El Salvador",
		"Guatemala", "Honduras", "Nicaragua",
		"Panama"
	)

	private val North_America = Array(
		"Canada", "Greenland", "Mexico",
		"Saint Pierre Miquelon", "USA"
	)

	private val South_America = Array(
		"Argentina", "Bolivia",
		"Brazil", "Chile", "Colombia", "Ecuador",
		"Falkland Islands (Malvinas)", "French Guiana", "Guyana", "Paraguay",
		"Peru", "Suriname", "Uruguay", "Venezuela"
	)

	private val Oceania = Array("American Samoa",
		"Australia", "Christmas Island", "Cocos (Keeling) Islands",
		"Cook Islands", "Federated States of Micronesia", "Fiji",
		"French Polynesia", "Guam", "Kiribati", "Marshall Islands",
		"Nauru", "New Caledonia", "New Zealand",
		"Niue", "Northern Mariana Islands", "Palau",
		"Papua New Guinea", "Pitcairn Islands",
		"Samoa", "Solomon Islands", "Tokelau", "Tonga",
		"Tuvalu", "Vanuatu", "Wallis and Futuna"
	)

	/** Sets up the spark session, json data, and runs the analysis.
	  *
	  * @param args None
	  */
	def main(args: Array[String]): Unit = {

		// Declaring spark session at global scope
		val spark = SparkSession.builder()
			.appName("Infection-Rates")
			.master("local[4]")
			.getOrCreate()

		// Setting up spark
		spark.sparkContext.setLogLevel("ERROR")

		// Configuration to be able to use AWS s3a
		spark.sparkContext.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")

		// Set up S3 with secret and access key with spark
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.awsAccessKeyId", sys.env("AWS_ACCESS_KEY_ID"))
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.awsSecretAccessKey", sys.env("AWS_SECRET_ACCESS_KEY"))

		import spark.implicits._
		println()

		// Creates the json for today and yesterday data
		createJsonFile( "today.json", "https://disease.sh/v3/covid-19/countries?yesterday=false&allowNull=false" )
		createJsonFile( "yesterday.json", "https://disease.sh/v3/covid-19/countries?yesterday=true&allowNull=false" )

		// Creates the tables in temp view
    createTodayTable(spark)
		createYesterdayTable( spark )

		// Percentage of Regions with increasing COVID-19 Infection rate
		covidRegionalInfectionRate( spark )

		// Percentage of countries with increasing COVID-19 Infection rate
		covidCountryInfectionRate( spark )

		// Stops the spark session
		spark.stop()
	}

	/** Grabs json object from the url and writes it to a file, and then uploads the file to S3 datalake
	  *
	  * @param fileName Name of the file one wants to write to.
	  * @param url Url of the where the json should be pulled from.
	  */
	def createJsonFile(fileName: String, url: String):Unit = {
		// Gets the json data from the url
		val jsonData = Jsoup.connect( url ).ignoreContentType( true ).execute.body

		// Make the json
		val json = new File( s"datalake/InfectionRates/${fileName}" )
		val jsonWriter = new FileWriter(json)

		// Write the json to the file
		jsonWriter.write(jsonData)

		// Build S3 client 
		val credentials = new BasicAWSCredentials(sys.env("AWS_ACCESS_KEY_ID"), sys.env("AWS_SECRET_ACCESS_KEY"))
		val client = AmazonS3ClientBuilder
      .standard()
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.withRegion("us-east-1")
      .build();

		// Upload file to S3 datalake
		client.putObject(
			"covid-analysis-p3/datalake/infection-mortality/RegionalInfectionRates",
			json.getName(),
			json
		)

		// Close the writer
		jsonWriter.close()
    }

	/** Reads in the JSON from S3 - https://disease.sh/v3/covid-19/countries?yesterday=false&allowNull=false
	  * Provides a temp view for todays disease info to be used later
	  *
	  * @param spark SparkContext for this application
	  */
	def createTodayTable(spark: SparkSession):Unit = {
		import spark.implicits._

		// Reads in a local json file
		val todayJson = spark.read.json("datalake/InfectionRates/today.json")

		// Makes a DataFrame with a schema for columns
		val today = todayJson.withColumn("Region",when($"country".isin(Africa: _*), "Africa")
			.when($"country".isin(Asia: _*), "Asia")
			.when($"country".isin(Europe: _*), "Europe")
			.when($"country".isin(Caribbean: _*), "Caribbean")
			.when($"country".isin(Central_America: _*), "Central America")
			.when($"country".isin(South_America: _*), "South America")
			.when($"country".isin(North_America: _*), "North America")
			.when($"country".isin(Oceania: _*), "Oceania")
		)

		// Creates the today table
		today.createOrReplaceTempView("today")
	}

	/** Reads in the JSON from S3 - https://disease.sh/v3/covid-19/countries?yesterday=true&allowNull=false
	  * Provides a temp view for yesterdays disease info to be used later
	  *
	  * @param spark SparkContext for this application
	  */
	def createYesterdayTable(spark: SparkSession):Unit = {
		import spark.implicits._

		// Reads in a local json file
		val yesterdayTemp = spark.read.json("datalake/InfectionRates/yesterday.json")

		// Makes a DataFrame with a schema for columns
		val yesterday = yesterdayTemp.withColumn("Region",when($"country".isin(Africa: _*), "Africa")
			.when($"country".isin(Europe: _*), "Europe")
			.when($"country".isin(Asia: _*), "Asia")
			.when($"country".isin(Caribbean: _*), "Caribbean")
			.when($"country".isin(Central_America: _*), "Central America")
			.when($"country".isin(North_America: _*), "North America")
			.when($"country".isin(South_America: _*), "South America")
			.when($"country".isin(Oceania: _*), "Oceania")
		)

		// Creates the yesterday table
		yesterday.createOrReplaceTempView("yesterday")
	}



	/**
	  * Returns dataframe for infection rate changes of one specific region
	  *
	  * @param spark SparkContext for this application
	  * @param region String that specifies the region for this query
	  */
	def getRegionInfectionRate(spark: SparkSession, region : String): DataFrame = {
		// The sql query for the infection rate change of one specific region
    spark.sql(
			s"""
				Select first(yesterday.Region) As Region,
					bround(avg((((today.todayCases/today.population)*1000000) - ((yesterday.todayCases/yesterday.population)*1000000)) / yesterday.casesPerOneMillion * 100), 2) AS Infection_Rate_Change
				FROM yesterday
				INNER JOIN today
				ON today.country=yesterday.country
				WHERE yesterday.Region='$region'
      """
		)
	
	}



	/**
	  * Calculates Regional Infection Rate Changes
	  *
	  * @param spark SparkContext for this application
	  */
	def covidRegionalInfectionRate(spark: SparkSession):Unit = {
    import spark.implicits._

		println("Regions and their change in Infection Rate")

		// Setting the DataFrames for each region
		val dfAfrica = getRegionInfectionRate(spark, "Africa")
		val dfAsia = getRegionInfectionRate(spark, "Asia")
		val dfCaribbean = getRegionInfectionRate(spark, "Caribbean")
		val dfCentralAmerica = getRegionInfectionRate(spark, "Central America")
		val dfEurope = getRegionInfectionRate(spark, "Europe")
		val dfNorthAmerica = getRegionInfectionRate(spark, "North America")
		val dfOceania = getRegionInfectionRate(spark, "Oceania")
		val dfSouthAmerica = getRegionInfectionRate(spark, "South America")

		//Combine the DataFrames
		val regions = dfAfrica.union(dfAsia)
			.union(dfCaribbean)
			.union(dfCentralAmerica)
			.union(dfEurope)
			.union(dfNorthAmerica)
			.union(dfOceania)
			.union(dfSouthAmerica)
			.sort(desc("Infection_Rate_Change"))

		// Print the infection rate change for all regions
		regions.show()
			
		// Upload region info to S3 bucket
		regions.coalesce(1).write.mode("overwrite").option("header","true").csv("s3a://covid-analysis-p3/datawarehouse/infection-mortality/RegionalInfectionRates/Infection_Rate_Change")
	}



	/** Builds and shows the results of a SQL query with parameters coming in from the function below
	  *
	  * @param spark SparkContext for this application
	  * @param todayArg Arg string for specific query
	  * @param resName Result string for specific query
	  * @param orderByArg Order by string for specific query
	  */
	def buildAndShowQuery(spark: SparkSession, todayArg: String, resName: String, orderByArg: String, filename: String) : Unit = {
		val query = spark.sql(
			s"""
				SELECT today.country AS Country,
					today.Region AS Region, bround((((today.$todayArg/today.population)*1000000) - ((yesterday.$todayArg/yesterday.population)*1000000)) / yesterday.casesPerOneMillion * 100, 2) AS $resName
				FROM today
				INNER JOIN yesterday
				ON today.country=yesterday.country
				$orderByArg
				LIMIT 1
			"""
		)

		// Write to the CLI
		query.show()

		// Write to the AWS s3 bucket
		query.coalesce(1).write.mode("overwrite").option("header","true").csv(s"s3a://covid-analysis-p3/datawarehouse/infection-mortality/RegionalInfectionRates/$filename")
	}


	
	/** Queries and prints several sets of data based on a countries infection rates.
	  *
	  * @param spark SparkContext for this application
	  */
	def covidCountryInfectionRate(spark: SparkSession):Unit = {
		import spark.implicits._

		println("Percentage of countries with a rising infection rate")

		// Joins the tables today and yesterday then does a calcuation to get the differece in stats
		val query = spark.sql(
			"""
				SELECT today.country AS Country,
					bround((((today.todayCases/today.population)*1000000) - ((yesterday.todayCases/yesterday.population)*1000000)) / yesterday.casesPerOneMillion * 100, 2) AS Infection_Rate_Change
				FROM today
				INNER JOIN yesterday
				ON today.country=yesterday.country
			"""
		).filter($"Infection_Rate_Change" > 0)
		.select(bround((count("Infection_Rate_Change") / 218) * 100, 2) as "Percentage of Countries w/Rising Infection Rate")
		
		// Write to the CLI
		query.show()

		// Write to the AWS s3 bucket
		query.coalesce(1).write.mode("overwrite").option("header","true").csv("s3a://covid-analysis-p3/datawarehouse/infection-mortality/RegionalInfectionRates/percentCountriesRisingInfectionRate")

		// Most increase in infection rate per capita
		println("Country with the LARGEST increase in Infection Rate")
		buildAndShowQuery(spark, "todayCases", "Infection_Rate_Change", "ORDER BY Infection_Rate_Change DESC", "largestIncreaseInfectionRate")

		// Least increase in infection rate per capita
		println("Country with the SMALLEST increase and/or LARGEST decrease in Infection Rate")
		buildAndShowQuery(spark, "todayCases", "Infection_Rate_Change", "ORDER BY Infection_Rate_Change ASC NULLS LAST", "smallestIncreaseInfectionRate")

		// Most increase in fatality rate per capita
		println("Country with the LARGEST increase in Fatality Rate")
		buildAndShowQuery(spark, "todayDeaths", "Fatality_Rate_Change", "ORDER BY Fatality_Rate_Change DESC", "largestIncreseFatalityRate")

		// Least increase in fatality rate per capita
		println("Country with the SMALLEST increase and/or LARGEST decrease in Fatality Rate")
    buildAndShowQuery(spark, "todayDeaths", "Fatality_Rate_Change", "ORDER BY Fatality_Rate_Change ASC NULLS LAST", "smallestIncreaseFatalityRate")
        
		// Most increase in recovery rate per capita
		println("Country with the LARGEST increase in Recovery Rate")
		buildAndShowQuery(spark, "todayRecovered", "Recovery_Rate_Change", "ORDER BY Recovery_Rate_Change DESC", "largestIncreaseRecoveryRate")

		// Least increase in recovery rate per capita
		println("Country with the SMALLEST increase and/or LARGEST decrease in Recovery Rate")
		buildAndShowQuery(spark, "todayRecovered", "Recovery_Rate_Change", "ORDER BY Recovery_Rate_Change ASC NULLS LAST", "smallestIncreaseRecoveryRate")
	}
}