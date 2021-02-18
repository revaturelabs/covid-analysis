# Twitter COVID-19 Sentiment Analysis
## Project Description

This directory contains the Spark sbt project that was used to answer the question:

Were the content of Tweets during these time frames more positive in tone or negative? 

* 12/11/2020 - 12/25/2020
* 12/26/2020 - 01/05/2021
* 02/03/2021 - 02/14/2021

## Technologies Used
- Spark version 2.4.7
- Scala version 2.11.12
- Scalatest 3.2.2
- AWS S3
- AWS EMR 5.27.0

## Features
- Utilizes Scala, Spark, and AWS to analyze sentiments of twitter text data for the above specified date ranges 

## Classes
- Runner.scala - contains the main method used to run the application
- SentimentRatio.scala - contains the core program functionality. See the Scaladocs inside this class for more information about how it works.
- SentimentRatioTest.scala - unit testing suite for this application.

## Requirements
- [JDK version 8 or 11](https://adoptopenjdk.net/).
- [Scala and SBT](https://www.scala-lang.org/download/2.12.8.html).
- [Spark](https://spark.apache.org/downloads.html)
- [AWS Account](https://aws.amazon.com)
- [AWS S3 Bucket](https://aws.amazon.com/s3)
- [AWS CLI](https://aws.amazon.com/cli/) that has been configured with your AWS account credentials
- [AWS EMR](https://aws.amazon.com/emr) Spark cluster for Spark 2.4.7

## Usage
1. Ensure all above requirements are met
2. Navigate to your desired work folder and run the shell command:
```bash
git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git
```
3. Navigate to the downloaded "../covid-analysis-1/twitter-general/data-cleaner" folder, and follow the README to obtain your desired data for analysis.
4. Navigate to "../covid-analysis-1/twitter-general/tweet-positive-negative/src/main/scala" and open Runner.scala in a text/code editor of your choice.
5. Enter "sbt assembly" into your terminal and press enter. This will generate your jar file that will be located at "../tweet-positive-negative/target/scala-2.11/sentiment-analysis.jar".
6. Copy your jar file onto your S3 bucket by running the command "aws s3 cp tweet-positive-negative/target/scala-2.11/sentiment-analysis.jar [Your S3 bucket location]"
7. Connect to your EMR Spark cluster and save your AWS credentials as environment variables onto your cluster:
```bash
export AWS_ACCESS_KEY_ID=[YOUR_AWS_ACCESS_KEY_ID_HERE]
export AWS_SECRET_ACCESS_KEY=[YOUR_AWS_SECRET_ACCESS_KEY_HERE]
```
8. Run the following spark-submit command to perform your analysis and have the results saved to your S3 bucket as a .csv file:
```bash
spark-submit \
--class Runner \
--master yarn \
[S3 bucket path to your jar file] \
[Input corresponding to your desired analysis ("1" for the first date range, "2" for the second date range, "3" for the last date range.)]
```

## License
[Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

