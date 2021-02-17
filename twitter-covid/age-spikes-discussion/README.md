# Europe 25yr and Under Covid Cases vs Twitter Covid Discussion

## Project Description

This is a Spark Scala program to answer the questions: 

Is the trend of the global COVID-19 discussion increasing or decreasing? 
Do spikes in infection rates of the 25 and under age range in Europe affect the volume of discussion?

## Technologies Used

* Scala - version 2.12.10
* Spark - version 3.0.1
* Amazon S3
* Amazon EMR

## Data Sources

* https://zenodo.org/record/3738018#.YCxeipNKiu4
    * Download full_dataset_clean.tsv
* https://www.ecdc.europa.eu/en/publications-data/covid-19-data-14-day-age-notification-rate-new-cases
    * Download as CSV
    
## Features

* Pulls data sources (Twitter Covid Tweets and European Covid Cases) from S3 bucket
* Performs various transformations on data in order to answer questions in project description
* Spark Scala program that is packaged as a jar and executed on Amazon EMR cluster

## Usage

> git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git

1. Download data from provided links under "Data Sources"
2. Add Amazon keys as environment variables by inputting the following commands into terminal:
   >export AWS_ACCESS_KEY_ID=[Your AWS access key id]
   >export AWS_SECRET_ACCESS_KEY=[Your AWS secret access key]
3. Upload data to S3 and modify all S3 read and write paths to your own S3 paths
4. Produce jar to location target/scala-2.12/age-spikes-discussion_2.12-0.1.jar by running: 
    > sbt package
5. Copy jar to S3 by running:
    > cp target/scala-2.12/age-spikes-discussion_2.12-0.1.jar [Path to your s3 bucket]
6. SSH into EMR cluster
7. Add Amazon keys as environment variables to EMR:
    >export AWS_ACCESS_KEY_ID=[Your AWS access key id]
    >export AWS_SECRET_ACCESS_KEY=[Your AWS secret access key]
8. Spark-submit your jar to EMR, outputting your results as a CSV file to the s3 output path you specified in step 3:
    >spark-submit \
    --class Runner \
    --master yarn \
   [Path to your jar in your s3 bucket here]