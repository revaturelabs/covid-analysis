# Regional Infection Rates

## Project Description TODO
What percentage of countries have an increasing COVID-19 Infection rate?

## Technologies Used

* Scala - version 2.12.12
* Spark - version 3.0.1
* Spark SQL
* AWS EMR
* AWS S3

## Features TODO

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
sbt run
```

## Test
```bash
sbt test
```

## Package
```bash
sbt package
```

## Contributors
Collin Breeding
Trenton Serpas
Trevor Spear
Tristan Cates