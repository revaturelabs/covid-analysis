# Regional Infection Rates

## Project Description
    Regional infection rates analyzes the trend in COVID infection rates, mortality
rates, and recovery rates for different countries, by using data on COVID cases 
and deaths for "today" and "yesterday", gathering data with HTTP calls to 
https://disease.sh/v3/covid-19/countries?yesterday=false&allowNull=false and
https://disease.sh/v3/covid-19/countries?yesterday=true&allowNull=false

    Country data is converted into the regions Asia, Africa, Europe, North America,
South America, Central America, The Caribbean, and Oceania in order to perform our region analysis, 
calculating the percent change in infection rate across regions. This program additionally 
performs an analysis of the rate of change of particular countries in infection rate, 
mortality rate, and recovery rate. This program utlizing Spark SQL for data analysis, 
AWS S3 for data storage, and is intended to be packaged and run on AWS EMR.

## Technologies Used

* Scala - version 2.12.12
* Spark - version 3.0.1
* Spark SQL
* AWS EMR
* AWS S3

## Features

* Transforms and analyzes data utilizing Spark SQL.
* Uploads incoming data to S3 datalake bucket and processed data to S3 datawarehouse.
* Answers questions such as:
    * What percentage of countries have an increasing COVID-19 Infection rate?
    * What is the rate of change in infection rate of each region?
    * Which countries had the largest increase, and which has the largest decrease in infection rate?
    * How about the largest increase and largest decrease for mortality rate among countries?
    * Largest increase/decrease in recovery rate among countries?
* Packagable and runnable on AWS EMR.

## Setup

After cloning this repository, you need to set the environment variables for the S3 bucket
```bash
export AWS_ACCESS_KEY_ID=************
export AWS_SECRET_ACCESS_KEY=************
```

You also need to ensure hadoop or atleast part of it is installed on your machine
https://github.com/steveloughran/winutils/tree/master/hadoop-2.7.1/bin
```md
download these to a directory set as HADOOP_HOME:
[hadoop.dll]
[winutils.exe]

download this to system32:
[hadoop.dll]
```

## Run
```bash
sbt run
```

## Test
```bash
sbt test
```

## Scala Docs
```bash
sbt doc
```
the docs can then be found under ./target/scala-2.12/api/index.html

## Package
```bash
sbt package
```

## Contributors
Collin Breeding
Trenton Serpas
Trevor Spear
Tristan Cates