# COVID-19 Stock Market Composite Index Daily Change Calculator by Region

## Project Description

Functions to calculate the stock market composite index daily percentage change by region and use the calculation as a metrics to find out which region handled COVID-19 the best.

## Technologies Used

* Scala - version 2.12.10
* sbt - version 1.4.7
* ScalaTest - version 3.2.2
* Spark SQL - version 3.0.1
* AWS S3
* AWS EMR

## Features

* Reads stock market composite index datasets from an AWS S3 bucket by each region and converts each region's datasets into a dataframe.
* Converts String number dates and String word dates into a uniform date object.
* Sums all composite index prices of the same date by region.
* Calculates daily differences of the Region Index column and daily percentage changes of the Region Index column.
* Saves the result as a csv file into an AWS S3 bucket by each region.

## Getting Started

To run this package, you'll need to install sbt by following the instructions here: https://scala-lang.org/download/.

Once you have sbt set up, you'll need to download the repository. Open a terminal and change to the directory you want to clone the repo into:

`cd folder/to/clone-into/`

Then use:

`git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git`

to download the project as a whole into your working directory.

## Usage

In the terminal, change your directory to the `stockmarket` directory inside the project. Assuming you're currently in the main repo's directory:

`cd ./herdimmunity-stockmarket/stockmarket`

In order to create a jar file, use the command below:

`sbt package`

Once a jar file is created, copy the file located within /target/scala-2.12 directory and paste it to JVM or a local cluster. If you are putting the jar file in AWS S3 bucket, use the command below:

`aws s3 cp ./target/scala-2.12/<Name of the jar file> s3://<path to the s3 bucket>`

In order to run a jar file in AWS EMR, use the command below:

`spark-submit --class <name of the class> --master yarn s3://<path to the jar file in a AWS S3 bucket>`


## Contributors

* [Trenton](https://github.com/granpawalton)
* [Bryant](https://github.com/brysingh76)
* [Sundoo](https://github.com/spark131008)
