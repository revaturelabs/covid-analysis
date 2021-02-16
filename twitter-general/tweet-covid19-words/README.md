# TWEET-COVID19-WORDS
## Project Description

This directory contains the Spark sbt project that was used to answer the question:

For each date range below, what were the most popular meaningful non-Covid19 related words used in Covid19 related Tweets?

* 12/11/2020 - 12/25/2020
* 12/26/2020 - 01/05/2021
* 02/03/2021 - 02/14/2021

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
- Terms.scala - contains a Set of Covid-19 related terms and a Set of words that will be omitted from our analysis results.
- WordCount.scala - contains the core program functionality. See the Scaladocs inside this class for more information about how it works.
- WordCountSpec.scala - our unit testing suite for this application.

## Requirements
- [JDK version 8 or 11](https://adoptopenjdk.net/).
- [Scala and SBT](https://www.scala-lang.org/download/2.12.8.html).
- [Spark](https://spark.apache.org/downloads.html)
- [AWS Account](https://aws.amazon.com)
- [AWS S3 Bucket](https://aws.amazon.com/s3)
- [AWS CLI](https://aws.amazon.com/cli/) that has been configured with your AWS account credentials
- [AWS EMR](https://aws.amazon.com/emr) Spark cluster for Spark 3.0.1

## Usage
1. Ensure all above requirements are met
2. Navigate to your desired work folder and run the shell command:
```bash
git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git
```
3. Navigate to the downloaded "../covid-analysis-1/twitter-general/data-cleaner" folder, and follow the README to obtain your desired data for analysis.
4. Navigate to "../covid-analysis-1/twitter-general/tweet-covid19-words/src/main/scala" and open WordCount.scala in a text/code editor of your choice.
    - Modify lines 56, 57, and 58 to point to the location(s) of your input data that has been cleaned previously with data-cleaner.
    - Modify lines 96 and 97 to match the proper data ranges of your analyzed data.
    - Modify line 108 to point to the location of your desired output S3 bucket directory.
5. Open your desired shell of choice, navigating to the downloaded "../covid-analysis-1/twitter-general/tweet-covid19-words" folder.
6. Enter "sbt package" into your terminal and press enter. This will generate your jar file that will be located at "../tweet-covid19-words/target/scala-2.12/twitter-general-word-count.jar".
7. Copy your jar file onto your S3 bucket by running the command "aws s3 cp tweet-covid19-words/target/scala-2.12/twitter-general-word-count.jar [Your S3 bucket location]"
8. Connect to your EMR Spark cluster and save your AWS credentials as environment variables onto your cluster:
```bash
export AWS_ACCESS_KEY_ID=[YOUR_AWS_ACCESS_KEY_ID_HERE]
export AWS_SECRET_ACCESS_KEY=[YOUR_AWS_SECRET_ACCESS_KEY_HERE]
```
9. Run the following spark-submit command to perform your analysis and have the results saved to your S3 bucket as a .csv file:
```bash
spark-submit \
--class Runner \
--master yarn \
[S3 bucket path to your jar file] \
[Input corresponding to your desired analysis ("1" for your first date range, "2" for your second date range, etc.)]
```
Note: Your analysis options are defined by you on lines 56 and 57 of WordCount.scala.

