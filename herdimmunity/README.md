# COVID-19 Herd Immunity Calculator

## Project Description

Functions to calculate the estimated date the U.S. population reaches herd immunity in regards to COVID-19, given number of people already fully vaccinated and average daily vaccinations.

## Technologies Used

* Scala - version 2.13
* sbt - version 1.4.6
* ScalaTest - version 3.2.2
* scala-logging - version 3.9.2

## Features

* Pulls [Our World in Data](https://github.com/owid/covid-19-data/tree/master/public/data)'s COVID-19 dataset from https://covid.ourworldindata.org/data/owid-covid-data.csv and parses latest U.S. vaccination data from the set.
* Calculates estimated number of days remaining until the U.S. population reaches herd immunity with respect to COVID-19.
* Converts estimated number of days into date format.

To-do list:
* Calculate herd immunity for any country with the appropriate data.
* Train a machine learning model to predict herd immunity date based on changes in vaccination rates over time.

## Getting Started

To run this package, you'll need to install sbt by following the instructions here: https://scala-lang.org/download/.

Once you have Java and sbt set up, you'll need to download the repository. Open a terminal and change to the directory you want to clone the repo into:

`cd folder/to/clone-into/`

Then use:

`git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git`

to download the project as a whole into your working directory.

## Usage

In the terminal, change your directory to the `herdimmunity` directory inside the project. Assuming you're currently in the main repo's directory:

`cd herdimmunity`

Once there, run the program with `sbt run`. This will automatically pull the latest U.S. vaccination data and output the results to the console as well as an output file in the project's private data warehouse on AWS.

## Contributors

* [David Masterson](https://github.com/Shadow073180)
* [Rastal](https://github.com/therastal)
