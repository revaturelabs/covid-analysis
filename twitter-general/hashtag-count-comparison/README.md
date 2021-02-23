# Twitter-general Hashtag-Count-Comparison

## Project Description

This application reads in twitter data from the S3 bucket and analyses the covid vs non-covid related hashtags

## Technologies Used

* Scala 2.12.12
* Apache Spark 3.0.1
  * Spark SQL	3.0.1
* Scalatest 3.2.2
* AWS S3
* AWS EMR
* sbt


## Features

* Collects tweet text from the S3 bucket, extracts the hashtags from all the tweets, groups the covid related hashtags together, sorts all hashtags by count, and ouptuts the results to the console and a csv file in S3

## Getting Started
   

* set AWS access keys as environment variables

## Usage

* general Usage
    * must be run with comandline argument `[mode]`
    * mode is a number between 0-3
        * 0 runs the application on tweet data in the date range `12/11/2020 - 12/25/2020`
        * 1 runs the application on tweet data in the date range `12/26/2020 - 01/05/2021`
        * 2 runs the application on tweet data in the date range `02/02/2021 - 02/14/2021`
        * 3 runs the application on a test dataset
* for running locally
    * the outputS3 function must be altered to run the code for local testing (see comments in funtion)
* for running on the EMR cluster
    * the outpusS3 function must be altered to run the code for running on the cluster (see comments in function)
    * run `sbt package` and use AWS cli comands to copy the jar file to the S3 bucket at address `s3://covid-analysis-p3/datawarehouse/twitter-general/jars/jarName.jar` (subject to change)
    * ssh into EMR cluster
    * run `spark-submit --class Runner --master yarn s3a://covid-analysis-p3/datawarehouse/twitter-general/jars/jarName.jar [mode]` (subject to change)
