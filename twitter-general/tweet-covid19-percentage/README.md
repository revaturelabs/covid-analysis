# TWEET-COVID19-PERCENTAGE CALCULATION
## Project Description

This directory contains the Spark sbt project that was used to answer the question:

For each date range below, what is the percentage of Covid-related Tweets

* 12/11/2020 - 12/25/2020
* 12/26/2020 - 01/05/2021

## Technologies Used
- Spark version 3.0.1
- Scala version 2.12.12
- Scalatest
- AWS S3
- AWS EMR

## Features
- Utilizes Scala, Spark, and AWS to analyze Twitter Text data for the above specified date ranges

## Classes
- Runner.scala - contains the main method used to run the application
- CovidTermsList.scala - contains a Set of Covid-19 related terms for percentage calculation
- RunnerSpec.scala - the unit testing suite for this portion of the application.

## Requirements
- [JDK version 8 or 11](https://adoptopenjdk.net/).
- [Scala and SBT](https://www.scala-lang.org/download/2.12.8.html).
- [Spark](https://spark.apache.org/downloads.html)
- [AWS Account](https://aws.amazon.com)
- [AWS S3 Bucket](https://aws.amazon.com/s3)
- [AWS CLI](https://aws.amazon.com/cli/) that has been configured with your AWS account credentials
- [AWS EMR](https://aws.amazon.com/emr) Spark cluster for Spark 3.0.1

## Usage
1. Copy your jar file onto your S3 bucket by running the command "aws s3 cp tweet-covid19-words/target/scala-2.12/twittercovid19percentageanalysis_2.12-1.jar [Your S3 bucket location]"
2. Connect to your EMR Spark cluster and save your AWS credentials as environment variables onto your cluster:
```bash
export AWS_ACCESS_KEY_ID=[YOUR_AWS_ACCESS_KEY_ID_HERE]
export AWS_SECRET_ACCESS_KEY=[YOUR_AWS_SECRET_ACCESS_KEY_HERE]
```
3. Run the following spark-submit command to perform your analysis and have the results saved to your S3 bucket as a .csv file:
```bash
spark-submit \
--class Runner \
--master yarn \
[S3 bucket path to your jar file] \
[Input corresponding to your desired analysis ("1" for your first date range, "2" for your second date range, etc.)]
