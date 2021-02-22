# COVID-19 Stock Market Composite Index Data Downloader

## Overview

A simple data downloader that programmatically downloads stock market composite index data to a local file system and uploads them to an AWS S3 bucket.

## Technologies Used

* Scala - version 2.12.10
* sbt - version 1.4.7
* ScalaTest - version 3.2.2
* AWS Java SDK - version 1.3.32
* AWS S3

## Features

* Pulls information about each country's name, region, an index name, a ticker symbol, a country code, and a data source from the CompositeIndexList.csv file.
* Builds a url for downloading each country's stock market composite index from [Market Watch](https://www.marketwatch.com/investing).
* Downloads each country's stock market composite index data to a local file system.
* Uploads the data to an AWS S3 bucket.

## Getting Started

To run this package, you'll need to install sbt by following the instructions here: https://scala-lang.org/download/.

Once you have sbt set up, you'll need to download the repository. Open a terminal and change to the directory you want to clone the repo into:

`cd folder/to/clone-into/`

Then use:

`git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git`

to download the project as a whole into your working directory.

Please make sure that there is a `log4j.properties` file under `./src/main/resources` directory in order to have successful AWS S3 connection. 

## Usage

In the terminal, change your directory to the `stockmarket-data` directory inside the project. Assuming you're currently in the main repo's directory:

`cd ./herdimmunity-stockmarket/stockmarket-data`

In order to set AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY, use the command below:

```
export AWS_ACCESS_KEY_ID=""
export AWS_SECRET_ACCESS_KEY=""
```

In order to run this program, use the command below:

`sbt run`


## Contributors

* [Trenton](https://github.com/granpawalton)
* [Bryant](https://github.com/brysingh76)
* [Sundoo](https://github.com/spark131008)
