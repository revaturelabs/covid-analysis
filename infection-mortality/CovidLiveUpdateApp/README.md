# CovidLiveUpdateApp
Takes in data from somewhere and does calculations compairing today's data verses yesterday's data
Live update by Region of current relevant totals from COVID-19 data.

# Setup

You need to set the environment variables for the bucket S3 bucket
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

# Run
```bash
sh q3_data_update.sh
```

# Test
```bash
sbt test
```