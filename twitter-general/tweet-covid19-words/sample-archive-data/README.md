# Sample Archive Data #
- The main batch data that was used for twitter-general's analysis mainly came from uploads by the famous American archivist [Jason Scott](https://en.wikipedia.org/wiki/Jason_Scott) on [archive.org](https://archive.org/details/twitterstream?and[]=year%3A%222020%22&and[]=year%3A%222021%22)
- This directory contains a sample of this data and its directory structure
- This sample only contains actual data within 1-01/2021/01/01/00, and only contains one json.bz2 file in this directory
- The ACTUAL data would contain 60 json.bz2 files in each directory within the 1-01/2021/01/01 subdirectory.
- Spark SQL can work directly with these json.bz2 files; they do not need to be extracted.
- To clean this sample data, you would run JsonCleaner.cleanAndSave("1-01")