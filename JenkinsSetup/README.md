# Jenkins Introduction
Jenkins is our tool for CI/CD similair to github actions or travis. It allows us to continously integrate and continously deploy our modules.

We do this by having Jenkins run `stages` where it attempts to compile our code, run our tests, or attempt to package our code into jars and deploy them. We can do many things in our `stages` but the ones we used were the ones mentioned above.

## Setup
This is reccomended to be setup on an ec2 linux distribution with this script file

LINK HERE

You can then connect to it with a URL based on your ec2 instance for configuration, the url should look something like

`http://ec2-12-34-56-789.compute-1.amazonaws.com:8080/jenkins/`

On this you can configure your Jenkins to connect to the repo of your choice, you can do this by going to your github repo and getting this when you have access to the settings of the repo

`Settings > Developer Settings > Personal Access Tokens > Generate New`

`Check [repo:status] and [public repo]`

Next go to your Jenkins connection and go to

`Dashboard > Manage Jenkins > Configure System > Github`

set the following

```md
<Name> YOUR_REPO
<API URL> https://api.github.com
<Credentials> Add
    <Kind> Secret Text
    <Secret> PASTE_YOUR_PERSONAL_ACCESS_TOKEN
    <ID> SOMETHING_YOU_CAN_REMEMBER (This will be only visible within the dashboard.)
```

Next go to your Github settings and under webhook add a webhook with
```md
<Payload URL> http://ec2-12-34-56-789.compute-1.amazonaws.com:8080/jenkins/github-webhook/
<Secret> PASTE_YOUR_PERSONAL_ACCESS_TOKEN
```

Next under your jenkins dashboard go to

`Dashboard > New Item`

Then name it and pick which best describes the repo you want 

Under Source Code mangement select git and fill out

```md
<Repository URL> YOUR_GITHUB_REPO_CLONE_URL_HTTPS
<Credentials> - none -
<Branches to build> MAKE_SURE_THIS_IS_MAIN_OR_MASTER_DEPENDING_ON_YOUR_REPO
```

Under Build Triggers

`check Github hook trigger for GITScm polling`

Should you need to import plugins (IE sbt)

`Dashboard > Manage Jenkins > Manage Plugins`

Then search and install everything you need, we used
* Default Plugins
* Docker Pipeline
* SBT
* JDK

Add JDK and SBT in

`Dashboard > manage Jenkin's > Global Tool Configure`

with sbt version: `1.4.7`