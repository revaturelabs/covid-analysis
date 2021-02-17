# Twitter-general Emoji-Count

## Project Description

This application reads in twitter data from the S3 bucket and analyses the frequency of covid vs non-covid related emojis

## Technologies Used

* Apache Spark
  * Spark SQL	
* AWS S3
* AWS EMR
* sbt


## Features
* Collects and analyzes emojis and the frequency of their occurence in relation to Covid-19 related Tweets.

## Getting Started
   
* Create a .env file to set AWS access keys as environment variables in this format:

AWS_ACCESS_KEY_ID=<access_key>

AWS_SECRET_ACCESS_KEY=<secret_access_key>

## Usage

* general Usage
    * must be run with command line argument `[mode]`
        * Example: shell> sbt "run 3"
    * mode is a number between 0-3
        * 0 runs the application on tweet data in the date range `12/11/2020 - 12/25/2020`
        * 1 runs the application on tweet data in the date range `12/26/2020 - 01/05/2021`
        * 2 runs the application on tweet data in the date range `02/02/2021 - 02/14/2021`
        * 3 runs the application on a test dataset
* for running locally
    * the tweetcovid19emoji function in Utilities.scala must be altered to use a local filepath.
* for running on the EMR cluster
    * run `sbt assembly` and use AWS cli comands to copy the jar file to the S3 bucket at address
    * run `aws s3 mv target/scala-2.12/TweetCovid19Emoji-assembly-1.jar s3://covid-analysis-p3/datawarehouse/twitter-general/jars/emojiCount.jar`
    * ssh into EMR cluster
    * run `spark-submit --class emojis.Main --master yarn s3a://covid-analysis-p3/datawarehouse/twitter-general/jars/emojiCount.jar [mode]` (subject to change)
