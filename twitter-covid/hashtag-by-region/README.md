# Hashtag By Region
## Project Description
This application is a single module of a much larger project related to the analysis of COVID-19 data. The full project can be found at https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1

This module serves to answer the question: What are the Twitter hashtags used to describe COVID-19 by Region (e.g. #covid, #COVID-19, #Coronavirus, #NovelCoronavirus)?

To answer this question, the application uses Apache Spark and a dataset of approximately 20 million hydrated tweets, gathered from the Twitter API's COVID-19 endpoint.

## Technologies Used
This application makes use of these technologies:
* Java JDK 11
* Scala 2.12.13
* sbt 1.4.5
* Apache Spark 3.0.1
* AWS S3
* AWS EMR

## Features
* Determines trends in discussion about CVOID-19 on Twitter by analyzing hashtag content.
* Outputs global, as well as region-specific, result sets.
* Results are sorted in descending order by hashtag frequency.
* Writes output to Amazon S3.

## Getting Started
* Open a Git Bash terminal in your desired directory and enter `git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git`.
* Navigate to covid-analysis-1/twitter-covid/hashtag-by-region and open it in your IDE of choice.

## Usage
* Ensure that your AWS credentials are stored in a .env file on the project root.
* Update the jsonPath variable in src/main/scala/main/Main.scala to point to your data source.
* Update the output path in src/main/scala/util/FileUtil.scala to point to your desired output destination.
* Update the spark variable in src/main/scala/main/Main.scala to your desired configuration settings, if necessary.
* Assuming your credentials and paths are correct, you can use `sbt run` to run the program locally, or you can use `sbt assembly` to create a jar file in target/scala-2.12, which can then be uploaded to a remote cluster to be executed.
* Use `sbt test` to execute the unit tests.  Test results will be printed to console, as well as stored in logs/test/test_results.log

### For more details on configuration and data acquisition, please read the STARTUP.md in this repository.

## Contributors
* Chris Johnson
* Nick Rau
* Josh Rubin
* Michael Tsoumpariotis