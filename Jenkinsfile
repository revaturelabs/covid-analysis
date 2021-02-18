@Library('github.com/releaseworks/jenkinslib') _

// Declarative Pipeline
// Top-most element in a scripted pipeline would be `node`
pipeline {
    // Required, tells us what machine should be running this build.
    agent any
    //check
    // Define our pipeline into stages
    stages {

        //Infection-mortality group
        stage("CovidLiveUpdateApp"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'CovidLiveUpdate';
                    branch '*/CovidLiveUpdate'
                }
            }
            stages{
                //infection-mortality/CovidLiveUpdateApp Compile
                stage("Compile infection-mortality/CovidLiveUpdateApp") {
                    steps {
                        echo "Compile CovidLiveUpdateApp"

                        sh '''
                            cd infection-mortality/CovidLiveUpdateApp
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //infection-mortality/CovidLiveUpdateApp Test
                stage("Test infection-mortality/CovidLiveUpdateApp") {
                    steps {
                        echo "Test CovidLiveUpdateApp"

                        sh '''
                            cd infection-mortality/CovidLiveUpdateApp
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //infection-mortality/CovidLiveUpdateApp Package
                stage("Package infection-mortality/CovidLiveUpdateApp") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/CovidLiveUpdate'
                        }
                    }
                    steps {
                        echo "Package CovidLiveUpdateApp"

                        sh '''
                            cd infection-mortality/CovidLiveUpdateApp
                            sbt package
                            cd ../..
                        '''
                        // AWS cli with github 3rd party library
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp infection-mortality/CovidLiveUpdateApp/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm infection-mortality/CovidLiveUpdateApp/target/scala-*/*.jar'

                    }
                }
            }
        }



        stage("RegionalInfectionRates"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'RegionalInfectionRates';
                    branch '*/RegionalInfectionRates'
                }
            }
            stages{
                //infection-mortality/RegionalInfectionRates Compile
                stage("Compile infection-mortality/RegionalInfectionRates") {
                    steps {
                        echo "Compile RegionalInfectionRates"

                        sh '''
                            cd infection-mortality/RegionalInfectionRates
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //infection-mortality/RegionalInfectionRates Test
                stage("Test infection-mortality/RegionalInfectionRates") {
                    steps {
                        echo "Test RegionalInfectionRates"

                        sh '''
                            cd infection-mortality/RegionalInfectionRates
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //infection-mortality/RegionalInfectionRates Package
                stage("Package infection-mortality/RegionalInfectionRates") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/RegionalInfectionRates';
                        }
                    }
                    steps {
                        echo "Package RegionalInfectionRates"

                        sh '''
                            cd infection-mortality/RegionalInfectionRates
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp infection-mortality/RegionalInfectionRates/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm infection-mortality/RegionalInfectionRates/target/scala-*/*.jar'

                    }
                }
            }
        }



        // Group group-econRepsponse 
        stage("CorrelateInfectionGDP"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'CorrelateInfectionGDP';
                    branch '*/CorrelateInfectionGDP'

                }
            }
            stages{
                // group-econResponse/CorrelateInfectionGDP Compile
                stage("Compile group-econResponse/CorrelateInfectionGDP") {
                    steps{
                        echo "Compile CorrelateInfectionGDP"
                        sh '''
                            cd group-econResponse
                            sbt "project CorrelateInfectionGDP; compile"
                            cd ../..
                        '''
                    }
                }
                //group-econResponse/CorrelateInfectionGDP Test
                stage("Test group-econResponse/CorrelateInfectionGDP") {
                    steps{
                        echo "Test CorrelateInfectionGDP"
                        sh '''
                            cd group-econResponse
                            sbt "project CorrelateInfectionGDP; test"
                            cd ../..
                        '''
                    }
                }
                //group-econResponse/CorrelateInfectionGDP Package
                stage("Package group-econResponse/CorrelateInfectionGDP") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/CorrelateInfectionGDP'
                        }
                    }
                    steps{
                        echo "Package CorrelateInfectionGDP"
                        sh '''
                            cd group-econResponse
                            sbt "project CorrelateInfectionGDP; assembly"
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp group-econResponse/CorrelateInfectionGDP/target/scala-*/CorrelateInfectionGDP*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm group-econResponse/CorrelateInfectionGDP/target/scala-*/*.jar'

                    }
                }
            }
        }
        
        

        stage("CountryBorders"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'CountryBorders';
                    branch '*/CountryBorders'
                }
            }
            stages{
                //group-econResponse/CountryBorders Compile
                stage("Compile group-econResponse/CountryBorders") {
                    steps{
                        echo "Compile CountryBorders"
                        sh '''
                            cd group-econResponse
                            sbt "project CountryBorders; compile"
                            cd ../..
                        '''
                    }
                }
                //group-econResponse/CountryBorders Test
                stage("Test group-econResponse/CountryBorders") {
                    steps{
                        echo "Test CountryBorders"
                        sh '''
                            cd group-econResponse
                            sbt "project CountryBorders; test"
                            cd ../..
                        '''
                    }
                }
                //group-econResponse/CountryBorders Package
                stage("Assembly of group-econResponse/CountryBorders") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/CountryBorders'
                        }
                    }
                    steps{
                        echo "Assembly of CountryBorders"
                        sh '''
                            cd group-econResponse
                            sbt "project CountryBorders; assembly"
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp group-econResponse/CountryBorders/target/scala-*/CountryBorders*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm group-econResponse/CountryBorders/target/scala-*/*.jar'

                    }
                }
            }
        }
        
        

        stage("CovidResponse"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'CovidResponse';
                    branch '*/CovidResponse'
                }
            }
            stages {
                //group-econResponse/CovidResponse Compile
                stage("Compile group-econResponse/CovidResponse") {
                    steps{
                        echo "Compile CovidResponse"
                        sh '''
                            cd group-econResponse
                            sbt "project CovidResponse; compile"
                            cd ../..
                        '''
                    }
                }
                //group-econResponse/CovidResponse Test
                stage("Test group-econResponse/CovidResponse") {
                    steps{
                        echo "Test CovidResponse"
                        sh '''
                            cd group-econResponse
                            sbt "project CovidResponse; test"
                            cd ../..
                        '''
                    }
                }
                //group-econResponse/CovidResponse Package
                stage("Assembly of group-econResponse/CovidResponse") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/CovidResponse'
                        }
                    }
                    steps{
                        echo "Assembly of CovidResponse"
                        sh '''
                            cd group-econResponse
                            sbt "project CovidResponse; assembly"
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp group-econResponse/CovidResponse/target/scala-*/CovidResponse*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm group-econResponse/CovidResponse/target/scala-*/CovidResponse*.jar'

                    }
                }
            }
        }

        
        stage("FirstRegionalPeaks"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'FirstRegionalPeaks';
                    branch '*/FirstRegionalPeaks'
                }
            }
            stages {
                //group-econResponse/FirstRegionalPeaks Compile
                stage("Compile group-econResponse/FirstRegionalPeaks") {
                    steps{
                        echo "Compile FirstRegionalPeaks"
                        sh '''
                            cd group-econResponse
                            sbt "project FirstRegionalPeaks; compile"
                            cd ../..
                        '''
                    }
                }
                //group-econResponse/FirstRegionalPeaks Test
                stage("Test group-econResponse/FirstRegionalPeaks") {
                    steps{
                        echo "Test FirstRegionalPeaks"
                        sh '''
                            cd group-econResponse
                            sbt "project FirstRegionalPeaks; test"
                            cd ../..
                        '''
                    }
                }
                //group-econResponse/FirstRegionalPeaks Package
                stage("Assembly of group-econResponse/FirstRegionalPeaks") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/FirstRegionalPeaks'
                        }
                    }
                    steps{
                        echo "Assembly of FirstRegionalPeaks"
                        sh '''
                            cd group-econResponse
                            sbt "project FirstRegionalPeaks; assembly"
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp group-econResponse/FirstRegionalPeaks/target/scala-*/FirstRegionalPeaks*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm group-econResponse/FirstRegionalPeaks/target/scala-*/FirstRegionalPeaks*.jar'

                    }
                }
            }
        }
        

        //twitter-covid Group     
        stage("age-spikes-discussion"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'age-spikes-discussion';
                    branch '*/age-spikes-discussion'
                }
            }
            stages {
                //twitter-covid/age-spikes-discussion Compile
                stage("Compile twitter-covid/age-spikes-discussion") {
                    steps {
                        echo "Compile age-spikes-discussion"

                        sh '''
                            cd twitter-covid/age-spikes-discussion
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //twitter-covid/age-spikes-discussion Test
                stage("Test twitter-covid/age-spikes-discussion") {
                    steps {
                        echo "Test age-spikes-discussion"

                        sh '''
                            cd twitter-covid/age-spikes-discussion
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //twitter-covid/age-spikes-discussion Package
                stage("Package twitter-covid/age-spikes-discussion") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/age-spikes-discussion'
                        }
                    }
                    steps {
                        echo "Package age-spikes-discussion"

                        sh '''
                            cd twitter-covid/age-spikes-discussion
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-covid/age-spikes-discussion/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm twitter-covid/age-spikes-discussion/target/scala-*/*.jar'

                    }
                }
            }
        }



        stage("hashtag-by-region"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'hashtag-by-region';
                    branch '*/hashtag-by-region'

                }
            }
            stages{
                //twitter-covid/hashtag-by-region Compile
                stage("Compile twitter-covid/hashtag-by-region") {
                    steps {
                        echo "Compile hashtag-by-region"

                        sh '''
                            cd twitter-covid/hashtag-by-region
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //twitter-covid/hashtag-by-region Test
                stage("Test twitter-covid/hashtag-by-region") {
                    steps {
                        echo "Test hashtag-by-region"

                        sh '''
                            cd twitter-covid/hashtag-by-region
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //twitter-covid/hashtag-by-region Assembly
                stage("Assembly twitter-covid/hashtag-by-region") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/hashtag-by-region'
                        }
                    }
                    steps {
                        echo "Assembly hashtag-by-region"

                        sh '''
                            cd twitter-covid/hashtag-by-region
                            sbt assembly
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-covid/hashtag-by-region/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm twitter-covid/hashtag-by-region/target/scala-*/*.jar'

                    }
                }
            }
        }



        stage("period-most-discussed"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'period-most-discussed';
                    branch '*/period-most-discussed'

                }
            }
            stages{
                //twitter-covid/period-most-discussed Compile
                stage("Compile twitter-covid/period-most-discussed") {
                    steps {
                        echo "Compile period-most-discussed"

                        sh '''
                            cd twitter-covid/period-most-discussed
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //twitter-covid/period-most-discussed Test
                stage("Test twitter-covid/period-most-discussed") {
                    steps {
                        echo "Test period-most-discussed"

                        sh '''
                            cd twitter-covid/period-most-discussed
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //twitter-covid/period-most-discussed Package
                stage("Package twitter-covid/period-most-discussed") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/period-most-discussed'
                        }
                    }
                    steps {
                        echo "Package period-most-discussed"

                        sh '''
                            cd twitter-covid/period-most-discussed
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-covid/period-most-discussed/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm twitter-covid/period-most-discussed/target/scala-*/*.jar'

                    }
                }
            }
        }



        stage("related-hashtags"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'related-hashtags';
                    branch '*/related-hashtags'

                }
            }
            stages{
                //twitter-covid/related-hashtags Compile
                stage("Compile twitter-covid/related-hashtags") {
                    steps {
                        echo "Compile related-hashtags"

                        sh '''
                            cd twitter-covid/related-hashtags
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //twitter-covid/related-hashtags Test
                stage("Test twitter-covid/related-hashtags") {
                    steps {
                        echo "Test related-hashtags"

                        sh '''
                            cd twitter-covid/related-hashtags
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //twitter-covid/related-hashtags Assembly
                stage("Assembly twitter-covid/related-hashtags") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/related-hashtags'
                        }
                    }
                    steps {
                        echo "Assembly related-hashtags"

                        sh '''
                            cd twitter-covid/related-hashtags
                            sbt assembly
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-covid/related-hashtags/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }
                        
                        sh 'rm twitter-covid/related-hashtags/target/scala-*/*.jar'

                    }
                }
            }
        }



        stage("twitter-covid-us"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'twitter-covid-us';
                    branch '*/twitter-covid-us'
                }
            }
            stages {
                // twitter-covid-us Compile
                stage("Compile twitter-covid twitter-covid-us") {
                    steps {
                        echo "Compile twitter-covid-us"

                        sh '''
                            cd twitter-covid/twitter-covid-us
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                // twitter-covid-us Test
                stage("Test twitter-covid twitter-covid-us") {
                    steps {
                        echo "Test twitter-covid-us"

                        sh '''
                            cd twitter-covid/twitter-covid-us
                            sbt test
                            cd ../..
                        '''
                    }
                }
                // RelatedHashtags Package
                stage("Package twitter-covid twitter-covid-us") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/twitter-covid-us'
                        }
                    }
                    steps {
                        echo "Package twitter-covid-us"

                        sh '''
                            cd twitter-covid/twitter-covid-us
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-covid/twitter-covid-us/target/scala-2.12/stock_market_data_downloader_2.12-1.0.jar s3://covid-analysis-p3/modules/stock_market_data_downloader_2.12-1.0.jar")
                        }

                        sh 'rm twitter-covid/twitter-covid-us/target/scala-*/*.jar'

                    }
                }
            }
        }
        


        //Group Twitter-General
        stage("data-cleaner"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'data-cleaner';
                    branch '*/data-cleaner'
                }
            }
            stages {
                //Twitter-General/data-cleaner Compile
                stage("Compile Twitter-General/data-cleaner") {
                    steps {
                        echo "Compile data-cleaner"

                        sh '''
                            cd twitter-general/data-cleaner
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/data-cleaner Test DIDNT PASS
                stage("Test Twitter-General/data-cleaner") {
                    steps {
                        echo "Test data-cleaner"

                        sh '''
                            cd twitter-general/data-cleaner
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/data-cleaner Package
                stage("Package Twitter-General/data-cleaner") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/FirstRegionalPeaks'
                        }
                    }
                    steps {
                        echo "Package data-cleaner"

                        sh '''
                            cd twitter-general/data-cleaner
                            sbt package
                            cd ../..
                        '''

                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-general/data-cleaner/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm twitter-general/data-cleaner/target/scala-*/*.jar'

                    }
                }
            }
        }

        
        
        stage("hashtag-count-comparison"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'hashtag-count-comparison';
                    branch '*/hashtag-count-comparison'
                }
            }
            stages {
                //Twitter-General/hashtag-count-comparison Compile
                stage("Compile Twitter-General/hashtag-count-comparison") {
                    steps {
                        echo "Compile hashtag-count-comparison"

                        sh '''
                            cd twitter-general/hashtag-count-comparison
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/hashtag-count-comparison Test DIDNT PASS
                stage("Test Twitter-General/hashtag-count-comparison") {
                    steps {
                        echo "Test hashtag-count-comparison"

                        sh '''
                            cd twitter-general/hashtag-count-comparison
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/hashtag-count-comparison Package
                stage("Package Twitter-General/hashtag-count-comparison") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/FirstRegionalPeaks'
                        }
                    }
                    steps {
                        echo "Package hashtag-count-comparison"

                        sh '''
                            cd twitter-general/hashtag-count-comparison
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-general/hashtag-count-comparison/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm twitter-general/hashtag-count-comparison/target/scala-*/*.jar'

                    }
                }
            }
        }



        stage("tweet-covid19-words"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'tweet-covid19-words';
                    branch '*/tweet-covid19-words'
                }
            }
            stages{
                //Twitter-General/tweet-covid19-words Compile
                stage("Compile Twitter-General/tweet-covid19-words") {
                    steps {
                        echo "Compile tweet-covid19-words"

                        sh '''
                            cd twitter-general/tweet-covid19-words
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/tweet-covid19-words Test DIDNT PASS
                stage("Test Twitter-General/tweet-covid19-words") {
                    steps {
                        echo "Test tweet-covid19-words"

                        sh '''
                            cd twitter-general/tweet-covid19-words
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/tweet-covid19-words Package
                stage("Package Twitter-General/tweet-covid19-words") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/FirstRegionalPeaks'
                        }
                    }
                    steps {
                        echo "Package tweet-covid19-words"

                        sh '''
                            cd twitter-general/tweet-covid19-words
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-general/tweet-covid19-words/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm twitter-general/tweet-covid19-words/target/scala-*/*.jar'

                    }
                }
            }
        }



        stage("tweet-covid19-emoji"){
            when {
                    // If any of these branches then run the stages
                    anyOf{
                        branch 'main';
                        branch 'develop';
                        branch 'tweet-covid19-emoji';
                        branch '*/tweet-covid19-emoji'
                    }
            }
            stages {
                //Twitter-General/tweet-covid19-emoji Compile
                stage("Compile Twitter-General/tweet-covid19-emoji") {
                    steps {
                        echo "Compile tweet-covid19-emoji"

                        sh '''
                            cd twitter-general/tweet-covid19-emoji
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/tweet-covid19-emoji Test
                stage("Test Twitter-General/tweet-covid19-emoji") {
                    steps {
                        echo "Test tweet-covid19-emoji"

                        sh '''
                            cd twitter-general/tweet-covid19-emoji
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/tweet-covid19-emoji Assembly
                stage("Assembly Twitter-General/tweet-covid19-emoji") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/FirstRegionalPeaks'
                        }
                    }
                    steps {
                        echo "Assembly tweet-covid19-emoji"

                        sh '''
                            cd twitter-general/tweet-covid19-emoji
                            sbt assembly
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-general/tweet-covid19-emoji/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm twitter-general/tweet-covid19-emoji/target/scala-*/*.jar'

                    }
                }
            }
        }
        
        

        stage("tweet-covid19-percentage"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'tweet-covid19-percentage';
                    branch '*/tweet-covid19-percentage'
                }
            }
            stages{
                //Twitter-General/tweet-covid19-percentage Compile
                stage("Compile Twitter-General/tweet-covid19-percentage") {
                    steps {
                        echo "Compile tweet-covid19-percentage"

                        sh '''
                            cd twitter-general/tweet-covid19-percentage
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/tweet-covid19-percentage Test
                stage("Test Twitter-General/tweet-covid19-percentage") {
                    steps {
                        echo "Test tweet-covid19-percentage"

                        sh '''
                            cd twitter-general/tweet-covid19-percentage
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //Twitter-General/tweet-covid19-percentage Package
                stage("Package Twitter-General/tweet-covid19-percentage") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/FirstRegionalPeaks'
                        }
                    }
                    steps {
                        echo "Package tweet-covid19-percentage"

                        sh '''
                            cd twitter-general/tweet-covid19-percentage
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp twitter-general/tweet-covid19-percentage/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm twitter-general/tweet-covid19-percentage/target/scala-*/*.jar'

                    }
                }
            }
        }



        //This project uses Tensorflow - Jenkins doesn't have the ability to load and run tensorflow 
        //User seems to only be capable of running this locally.
        // stage("tweet-positive-negative"){
        //     when {
        //         // If any of these branches then run the stages
        //         anyOf{
        //             branch 'main';
        //             branch 'develop';
        //             branch 'tweet-positive-negative';
        //             branch '*/tweet-positive-negative'
        //         }
        //     }
        //     stages {
        //         //twitter-general/tweet-positive-negative Compile
        //         stage("Compile twitter-general/tweet-positive-negative") {
        //             steps {
        //                 echo "Compile tweet-positive-negative"

        //                 sh '''
        //                     cd twitter-general/tweet-positive-negative
        //                     sbt compile
        //                     cd ../..
        //                 '''
        //             }
        //         }
        //         //twitter-general/tweet-positive-negative Test
        //         stage("Test Twitter-General/tweet-positive-negative") {
        //             steps {
        //                 echo "Test tweet-positive-negative"

        //                 sh '''
        //                     cd twitter-general/tweet-positive-negative
        //                     sbt test
        //                     cd ../..
        //                 '''
        //             }
        //         }
        //     }
        // }



        //Stockmarket Group
        stage("stockmarket"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'stockmarket';
                    branch '*/stockmarket'
                }
            }
            stages {
                //stockmarket Compile
                stage("Compile stockmarket") {
                    steps {
                        echo "Compile stockmarket"

                        sh '''
                            cd herdimmunity-stockmarket/stockmarket
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //stockmarket Test
                stage("Test stockmarket") {
                    steps {
                        echo "Test stockmarket"

                        sh '''
                            cd herdimmunity-stockmarket/stockmarket
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //stockmarket Package
                stage("Package stockmarket") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/FirstRegionalPeaks'
                        }
                    }
                    steps {
                        echo "Package stockmarket"

                        sh '''
                            cd herdimmunity-stockmarket/stockmarket
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp herdimmunity-stockmarket/stockmarket/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                        }

                        sh 'rm herdimmunity-stockmarket/stockmarket/target/scala-*/*.jar'

                    }
                }
            }
        }
        


        stage("stockmarket-data"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'stockmarket-data';
                    branch '*/stockmarket-data'
                }
            }
            stages {
                //stockmarket-data Compile
                stage("Compile stockmarket-data") {
                    steps {
                        echo "Compile stockmarket-data"

                        sh '''
                            cd herdimmunity-stockmarket/stockmarket-data
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //stockmarket-data Test
                stage("Test stockmarket-data") {
                    steps {
                        echo "Test stockmarket-data"

                        sh '''
                            cd herdimmunity-stockmarket/stockmarket-data
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //This project isn't spark-submitted at any point.
                // //stockmarket-data Package
                // stage("Package stockmarket-data") {
                //     when {
                //         // If any of these branches then run the stages
                //         anyOf{
                //             branch 'main';
                //             branch 'develop';
                //             branch 'deploy/FirstRegionalPeaks'
                //         }
                //     }
                //     steps {
                //         echo "Package stockmarket-data"

                //         sh '''
                //             cd herdimmunity-stockmarket/stockmarket-data
                //             sbt package
                //             cd ../..
                //         '''
                //         withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                //             AWS("--region=us-east-1 s3 cp herdimmunity-stockmarket/stockmarket-data/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                //         }
                //         sh 'rm herdimmunity-stockmarket/stockmarket-data/target/scala-*/*.jar'
                //
                //     }
                // }
            }
        }



        stage("herdimmunity"){
            when {
                // If any of these branches then run the stages
                anyOf{
                    branch 'main';
                    branch 'develop';
                    branch 'herdimmunity';
                    branch '*/herdimmunity'
                }
            }
            stages {
                //herdimmunity Compile
                stage("Compile herdimmunity") {
                    steps {
                        echo "Compile herdimmunity"

                        sh '''
                            cd herdimmunity-stockmarket/herdimmunity
                            sbt compile
                            cd ../..
                        '''
                    }
                }
                //herdimmunity Test
                stage("Test herdimmunity") {
                    steps {
                        echo "Test herdimmunity"

                        sh '''
                            cd herdimmunity-stockmarket/herdimmunity
                            sbt test
                            cd ../..
                        '''
                    }
                }
                //This project isn't spark-submitted at any point.
                //herdimmunity Package
                // stage("Package herdimmunity") {
                //     when {
                //         // If any of these branches then run the stages
                //         anyOf{
                //             branch 'main';
                //             branch 'develop';
                //             branch 'deploy/FirstRegionalPeaks'
                //         }
                //     }
                //     steps {
                //         echo "Package herdimmunity"

                //         sh '''
                //             cd herdimmunity-stockmarket/herdimmunity
                //             sbt package
                //             cd ../..
                //         '''
                //         withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                //             AWS("--region=us-east-1 s3 cp herdimmunity-stockmarket/herdimmunity/target/scala-*/*.jar s3://covid-analysis-p3/modules/")
                //         }
                //
                //         sh 'rm herdimmunity-stockmarket/herdimmunity/target/scala-*/*.jar'
                //     }
                // }
            }
        }
    }



    // After action of testing
    post {
        always {
            echo "This will always be invoked."
        }

        // If the build passes
        success {
            echo "All test, build, and package has passed."

            // Rebuild the react project here (only if the branch of the react project is changed): Strech Goal

        }

        unstable {
            echo "Unstable build."

            emailext body: 'Unstable in the commmit.', 
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], 
            [$class: 'RequesterRecipientProvider']], 
            subject: 'Please check your code and make sure you have added all the files.'

        }

        failure {
            echo "Something didn't pass. Email is being sent."

            emailext body: "Failure in commit: Job '${env.JOB_NAME}'", 
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], 
            [$class: 'RequesterRecipientProvider']], 
            subject: 'Please check that your code can compile, test, and package/assemble. If it can and still getting it to not pass please contact CI_CD Team.'

        }

        changed {
            echo "Changes have been made."
        }

    }
}