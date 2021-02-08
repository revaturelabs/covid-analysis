# Hashtag By Region
## About
This application is a single module of a much larger project related to the analysis of COVID-19 data. The full project can be found at https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1

This module serves to answer the question: What are the Twitter hashtags used to describe COVID-19 by Region (e.g. #covid, #COVID-19, #Coronavirus, #NovelCoronavirus)?

To answer this question, the application uses Apache Spark and a dataset of approximately 20 million hydrated tweets, gathered from the Twitter API's COVID-19 endpoint.

## Environment Information
This application makes use of these technologies:
* Java JDK 11
* Scala 2.12.13
* sbt version 1.4.5
* Apache Spark 3.0.1
* AWS S3
* AWS EMR

## Usage
* Ensure that your AWS credentials are stored in a .env file on the project root.
* Update the jsonPath variable in src/main/scala/main/Main.scala to point to your data source.
* Update the output path in src/main/scala/util/FileUtil.scala to point to your desired output destination.
* Update the spark variable in src/main/scala/main/Main.scala to your desired configuration settings, if necessary.
* Assuming your credentials and paths are correct, you can use `sbt run` to run the program locally, or you can use `sbt assembly` to create a jar file in target/scala-2.12, which can then be uploaded to a remote cluster to be executed.
* Use `sbt test` to execute the unit tests.  Test results will be printed to console, as well as stored in logs/test/test_results.log

### For more details on configuration and data acquisition, please read the STARTUP.md in this repository.