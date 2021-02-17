# CovidLiveUpdateApp

## Project Description
    Covid Live Update App takes in data from https://disease.sh/v3/covid-19/countries/ 
and performs calculations compairing today's data verses yesterday's data. 

    This program gives a live update by Region of current relevant totals from COVID-19 data, 
including the rate of change of infection rate, mortality rate, and recovery rate for each 
region. The program additionally calcualtes the rate of change of these variables as a combined 
whole (the rate of change of these values for the whole world). Region data is computed using 
country data and converting the data to represent the regions Asia, Africa, Europe, North America,
South America, Central America, The Caribbean, and Oceania.


## Technologies Used

* Scala - version 2.12.12
* Spark - version 3.0.1
* Spark SQL
* AWS EMR
* AWS S3

## Features

* Transforms and analyzes data utilizing Spark SQL.
* Uploads incoming data to S3 datalake bucket and processed data to S3 datawarehouse.
* Answers questions such as:
    * What is the rate of change in infection rates across each region?
    * What is the rate of change in mortality rates across each region?
    * What is the rate of change in recovery rates across each region?
    * Finally, what is the rate of change for each of these three values for the entire world as a whole (regions combined)?
* Packagable and runnable on AWS EMR.

## Setup

After cloning this repository, you need to set the environment variables for the S3 bucket
```bash
export AWS_ACCESS_KEY_ID=************
export AWS_SECRET_ACCESS_KEY=************
```

You also need to ensure hadoop or atleast part of it is installed on your machine
https://github.com/steveloughran/winutils/tree/master/hadoop-2.7.1/bin
```md
download these to a directory set as HADOOP_HOME:
[hadoop.dll]
[winutils.exe]

download this to system32:
[hadoop.dll]
```

## Run
```bash
sh q3_data_update.sh
```

## Test
```bash
sbt test
```

## Package
```bash
sbt package
```

## Scala Docs
```bash
sbt doc
```
the docs can then be found under ./target/scala-2.12/api/index.html

## Contributors
Collin Breeding
Trenton Serpas
Trevor Spear
Tristan Cates