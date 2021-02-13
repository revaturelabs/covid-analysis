# Hashtag By Region

## Data
The data source for this module is a large set of hydrated tweets in JSON format.  However, as can it be difficult to find such a source, it is very likely that you will need to build the data set yourself.  These are the steps you will need to take in order to build your data set:
* Download `full_dataset_clean.tsv.gz` from https://doi.org/10.5281/zenodo.3723939 and extract the TSV file.
* The Tweet IDs will need to be extracted from the TSV file you downloaded in the previous step and stored in a text file. A small utility Scala application has already be created to do this for you.  It can be found here: https://github.com/cjohn281/tweet-id-extractor
* Once you have a text file of just Tweet IDs, you will need to hydrate them.  The easiest way to do this will be to use twarc, which can be found here: https://github.com/DocNow/twarc. Please note that you will need to have Python installed on your system.  You will also need to register an application at apps.twitter.com and have access to your own Twitter API credentials. Depending on how many Tweets you intend to hydrate, this may be an extremely time consuming task.  The number of calls to the Twitter API that can be made within a 15 minute period is limited. Start on this as early as possible to ensure you have enough time to gather your data set.  It is recommended that you hydrate tweets in batches, which will result in multiple JSON files.
* After hydrating your tweets, assuming that you hydrated in batches and now have multiple JSON files, you can use this small Scala application to merge your files into a single JSON file: https://github.com/cjohn281/json-file-merger
* You now have a set of Tweets in a single JSON file which can be uploaded to an S3 bucket and used for analysis.

## Deployment - EMR
This project is currently set up to run on an AWS EMR cluster. Steps to run the application are as follows:
* Run `sbt assembly` to create a jar file of the application in target/scala-2.12.
* Upload the assembled jar file to the S3 bucket that you are using.
* From EMR, run `spark-submit s3://your-path/your-jar.jar`.
* Your output results will be stored to S3.