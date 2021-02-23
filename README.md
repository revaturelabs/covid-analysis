# COVID-19 Analysis
## Project Description

Big Data Capstone Project examining the effects of COVID-19 on society.

## Technologies Used

### Programs
* Scala - versions 2.12, 2.13
* sbt - version 1.4.x
* ScalaTest - version 3.2.x
* Spark - version 3.0.1

### DevOps
* git / GitHub
* Amazon S3
* Amazon EMR
* Jenkins

## Features

* Analyzes the Twitter conversation regarding COVID-19.
  * TODO: [twitter-covid directory](actual link)
* Analyzes changes in the general Twitter conversation since the onset of COVID-19.
  * TODO: [twitter-general directory](actual link)
* Assesses COVID-19's impact on the GDP of countries around the world.
  * TODO: [gdp directory](actual link)
* Assesses countries' and regions' COVID-19 infection and mortality rates.
  * TODO: [infection rates directory](actual link)
* Predicts when a country's population is expected to reach herd immunity towards COVID-19 and assesses how the pandemic has affected stock market composite indices.     
* Web based [dashboard](https://covid-data-dashbrd.herokuapp.com/) to shwocase the results for several application findings.
  * TODO: [herdimmunity directory](actual link)
  * TODO: [stockmarket directory](actual link)

To-do list:
* Allow the S3 bucket to be specified dynamically (instead of needing to change all code in the event of an S3 migration): Currently all projects use `s3://covid-analysis-p3/`
* Automate the generation of compile, test, and package/assembly checking for Jenkins

## Getting Started

Each module has its own specific setup instructions that you can find by following the links above, but there are some steps you can take to unify the modules' data and processes: 

### Java / Scala / sbt

Install sbt by following the instructions here: https://scala-lang.org/download/.

### git / GitHub

Once you have Java and sbt set up, you'll need to download the repository. Open a terminal and change to the directory you want to clone the repo into:

`cd folder/to/clone-into/`

Then use:

`git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git`

to download the project as a whole into your working directory.

### Jenkins

Set up a [Jenkins](https://www.jenkins.io/) instance on an [Amazon EC2](https://aws.amazon.com/ec2/) Cluster and integrate Jenkins with GitHub repository, having it ensure that all tests pass before branches are integrated with `main`. (TODO: actually give instructions for this)

### Amazon S3

To store input and output data for the modules, you'll need access to an [Amazon S3](https://aws.amazon.com/s3/) bucket. If setting up your own bucket, you'll need to change any code in this project that references the old bucket to reference your own bucket. Store input data in a `datalake` directory; output data is stored in a `datawarehouse` directory(see code for each module for specific location details); and the `jar` files for each module is stored in a `modules` directory.

### Amazon EMR

To run the `jar` files, you'll need to set up an [Amazon EMR](https://aws.amazon.com/emr/) Cluster. (TODO: actually give instructions for this)

### Covid-19 Dashboard
Web Application built with ReactJS.   
Features the finding for many of this project's applications:

[Deployed Website](https://covid-data-dashbrd.herokuapp.com/).       
[Github Repo for this dashboard](https://github.com/891-MehrabRahman-CovidAnalysis/covid-dashboard) 

## Usage

Because most of the modules in this project handle separate datasets and data transformations, each has its own usage instructions detailed in the module's README.

In general, users run `spark-submit`s on the EMR Cluster using the S3 file path of their constructed `jar`, and the output is stored in the S3 bucket's `datawarehouse` directory.

## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the [License](LICENSE).
