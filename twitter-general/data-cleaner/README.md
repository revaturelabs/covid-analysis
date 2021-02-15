# DATA CLEANER
## Project Description
This directory contains the methods used to format out twitter data, for both archived and streamed in data. You will notice many empty folders here, and this is because they contained gigabytes worth of data when run locally. These were files that were pushed to S3 after they were created here. As such, these files were not pushed to github.

## Technologies Used
- Spark version 3.0.1
- Spark RDDs
- Spark SQL

## Features
- Allows a user to clean both streamed in Twitter data as well as archived Twitter data taken from [archive.org](https://archive.org/details/twitterstream?and[]=year%3A%222020%22&and[]=year%3A%222021%22) so only the text is grabbed from the Tweet.

## Folder Structure

### Empty Folders 
- test-output - used by the ArchiveCleaner.clean method to store the initial results of the file cleaning process
- test-output-rdd - used by the ArchiveCleaner.clean method to store the intermediary results of the file cleaning process
- test-output-stream - used by the StreamCleaner.clean method to store the initial results of the file cleaning process

### Other Important Folders 
- sample-archive-data 
    - 01-01 - contains a sample of the archived data folder structure, which can be used to do a test run of ArchiveCleaner.clean
- twitter-stream-example-files - contains two methods that were used in a separate program to stream in twitter data, so data could be gathered beyond the last available date in the archived data. These methods do not run; they are just meant to illustrate what was done to get our streamed in twitter data.
    - Main.scala - This method was used to gather the streaming data.
    - saveData.scala - This method was used to make a copy of the streaming data every hour, so that we would have a new data file every hour
- unit-test-data - small bit of output data that will be used to test the ArchiveCleaner.scala method and StreamCleaner.scala method.
- cleanedArchiveFiles - contained the final results of all the files that were cleaned by the ArchiveCleaner.clean method. Files that were saved here were then immediately pushed to S3.
- cleanedStreamFiles - contained the final results of all the files that were cleaned by the StreamCleaner.clean method. Files that were saved here were then immediately pushed to S3.

### Classes
- ArchiveCleaner.scala - Formats archive data from a user specified day (view the scaladocs for more information)
- StreamCleaner.scala - Formats archive data from a user specified day (view the scaladocs for more information)op players they wish to analyze as well as the specific bracket. 

## Requirements
- [JDK version 8 or 11](https://adoptopenjdk.net/).
- [Scala and SBT](https://www.scala-lang.org/download/2.12.8.html).
- [Spark](https://spark.apache.org/downloads.html)

## Usage
1. Ensure all above requirements are met
2. Navigate to your desired work folder and run the shell command:
```bash
git clone https://github.com/891-MehrabRahman-CovidAnalysis/covid-analysis-1.git
```
3. Open your desired shell of choice, navigate to the downloaded "../covid-analysis-1/twitter-general/data-cleaner" folder.
4. For archive data: 
    - Download a day's worth of data from [archive.org](https://archive.org/details/twitterstream?and[]=year%3A%222020%22&and[]=year%3A%222021%22) and extract the .zip file **to a folder given a name matching your downloaded file. THIS IS IMPORTANT. For example, the file "twitter-stream-2021-01-05.zip" must be extracted to a folder named "01-05"** Do not worry about extracting the inner .bz2 files; Spark can handle this for us.
    - Replace "C:/Users/Cody/Desktop/extracts/" on line 41 of ArchiveCleaner.scala with the path to your extracted directory
    - Type sbt "run [mm-dd]" (example: sbt "run 02-03") to grab all data from your extracted directory, filter out everything except tweet text, and combine the data into a single text file. Results should pop up in cleanedArchiveFiles directory matching your input (i.e. mm-dd.txt).
5. For streaming data:
    - Set up your own stream, using the twitter-stream-example-files as your guide.
    - Replace "C:/Users/Cody/Desktop/Revature Training Stuff/scalawags-group-5/twitter-stream/HourlyResults/" on line 53 of StreamCleaner.scala with the path to your hourly streaming files.
    - Type sbt "run [mm-dd]" (example: sbt "run 02-03") to grab all hourly data from the specified day, merge it all into a single file, and format it be uniform on every line. Results should pop up in cleanedStreamFiles directory matching your input (i.e. mm-dd.txt).

