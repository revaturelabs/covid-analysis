@Library('github.com/releaseworks/jenkinslib') _
// Declarative Pipeline

// Top-most element in a scripted pipeline would be `node`

pipeline {
    // Required, tells us what machine should be running this build.
    agent any

    // Define our pipeline into stages
    stages {

        // //Infection-mortality group


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
                            AWS("--region=us-east-1 s3 cp infection-mortality/CovidLiveUpdateApp/target/scala-2.12/covidliveupdate_2.12-2.jar s3://covid-analysis-p3/modules/covidliveupdate_2.12-2.jar")
                        }
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
                            branch 'deploy/RegionalInfectionRates'
                        }
                    }
                    steps {
                        echo "Package RegionalInfectionRates"

                        sh '''
                            cd infection-mortality/RegionalInfectionRates
                            sbt package
                            cd ../..
                        '''
                        // withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                        //     AWS("--region=us-east-1 s3 cp infection-mortality/RegionalInfectionRates/target/scala-2.12/regionalinfectionrates_2.12-2.jar s3://covid-analysis-p3/modules/regionalinfectionrates_2.12-2.jar")
                        // }
                    }
                }
            }
        }



        // // Group group-econRepsponse 
        // stage("Group-EconResponse"){
        //     when {
        //         // If any of these branches then run the stages
        //         anyOf{
        //             branch 'main';
        //             branch 'develop';
        //             branch 'group-econResponse';
        //             branch '*/group-econResponse'

        //         }
        //     }
        //     stages{
        //         // group-econResponse/CorrelateInfectionGDP Compile
        //         stage("Compile group-econResponse") {
        //             steps{
        //                 echo "Compile group-econResponse"
        //                 sh '''
        //                     cd group-econResponse
        //                     sbt compile
        //                     cd ..
        //                 '''
        //             }
        //         }
        //         //group-econResponse/CorrelateInfectionGDP Test
        //         stage("Test group-econResponse") {
        //             steps{
        //                 echo "Test group-econResponse"
        //                 sh '''
        //                     cd group-econResponse
        //                     sbt test
        //                     cd ..
        //                 '''
        //             }
        //         }
        //         //group-econResponse/CorrelateInfectionGDP Package
        //         stage("Package group-econResponse") {
        //             when {
        //                 // If any of these branches then run the stages
        //                 anyOf{
        //                     branch 'main';
        //                     branch 'develop';
        //                     branch 'deploy/group-econResponse'
        //                 }
        //             }
        //             steps{
        //                 echo "Package group-econResponse"
        //                 sh '''
        //                     cd group-econResponse
        //                     sbt package
        //                     cd ..
        //                 '''
        //                 withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
        //                     AWS("--region=us-east-1 s3 cp group-econResponse/target/scala-2.12/covid-econ-grp_2.12-1.0.jar s3://covid-analysis-p3/modules/covid-econ-grp_2.12-1.0.jar")
        //                 }
        //             }
        //         }
        //     }
        // }


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
                        //withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            //AWS("--region=us-east-1 s3 cp JARFILE s3://covid-analysis-p3/modules/")
                        //}
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
                            cd group-econResponse/CountryBorders
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
                        // withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            //AWS("--region=us-east-1 s3 cp JARFILE s3://covid-analysis-p3/modules/")
                        // }
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
                        // withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            //AWS("--region=us-east-1 s3 cp JARFILE s3://covid-analysis-p3/modules/")
                        // }
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
                        // withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            //AWS("--region=us-east-1 s3 cp JARFILE s3://covid-analysis-p3/modules/")
                        // }
                    }
                }
            }
        }
        


        // //twitter-covid Group        
        // stage("age-spikes-discussion"){
        //     when {
        //         // If any of these branches then run the stages
        //         anyOf{
        //             branch 'main';
        //             branch 'develop';
        //             branch 'age-spikes-discussion';
        //             branch '*/age-spikes-discussion'
        //         }
        //     }
        //     stages {
        //         //twitter-covid/age-spikes-discussion Compile
        //         stage("Compile twitter-covid/age-spikes-discussion") {
        //             steps {
        //                 echo "Compile age-spikes-discussion"

        //                 sh '''
        //                     cd twitter-covid/age-spikes-discussion
        //                     sbt compile
        //                     cd ../..
        //                 '''
        //             }
        //         }
        //         //twitter-covid/age-spikes-discussion Test
        //         stage("Test twitter-covid/age-spikes-discussion") {
        //             steps {
        //                 echo "Test age-spikes-discussion"

        //                 sh '''
        //                     cd twitter-covid/age-spikes-discussion
        //                     sbt test
        //                     cd ../..
        //                 '''
        //             }
        //         }
        //         //twitter-covid/age-spikes-discussion Package
        //         stage("Package twitter-covid/age-spikes-discussion") {
        //             when {
        //                 // If any of these branches then run the stages
        //                 anyOf{
        //                     branch 'main';
        //                     branch 'develop';
        //                     branch 'deploy/FirstRegionalPeaks'
        //                 }
        //             }
        //             steps {
        //                 echo "Package age-spikes-discussion"

        //                 sh '''
        //                     cd twitter-covid/age-spikes-discussion
        //                     sbt package
        //                     cd ../..
        //                 '''
        //                 withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
        //                     //AWS("--region=us-east-1 s3 cp JARFILE s3://covid-analysis-p3/modules/")
        //                 }
        //             }
        //         }
        //     }
        // }

        
        // stage("HashtagByRegion"){
        //     when {
        //         // If any of these branches then run the stages
        //         anyOf{
        //             branch 'main';
        //             branch 'develop';
        //             branch 'HashtagByRegion';
        //             branch '*/HashtagByRegion'

        //         }
        //     }
        //     stages{
        //         //twitter-covid/HashtagByRegion Compile
        //         stage("Compile twitter-covid/HashtagByRegion") {
        //             steps {
        //                 echo "Compile HashtagByRegion"

        //                 sh '''
        //                     cd twitter-covid/HashtagByRegion
        //                     sbt compile
        //                     cd ../..
        //                 '''
        //             }
        //         }
        //         //twitter-covid/HashtagByRegion Test
        //         stage("Test twitter-covid/HashtagByRegion") {
        //             steps {
        //                 echo "Test HashtagByRegion"

        //                 sh '''
        //                     cd twitter-covid/HashtagByRegion
        //                     sbt test
        //                     cd ../..
        //                 '''
        //                 withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
        //                     //AWS("--region=us-east-1 s3 cp JARFILE s3://covid-analysis-p3/modules/")
        //                 }
        //             }
        //         }
        //         //twitter-covid/HashtagByRegion Package
        //         stage("Package twitter-covid/HashtagByRegion") {
        //             when {
        //                 // If any of these branches then run the stages
        //                 anyOf{
        //                     branch 'main';
        //                     branch 'develop';
        //                     branch 'deploy/FirstRegionalPeaks'
        //                 }
        //             }
        //             steps {
        //                 echo "Package HashtagByRegion"

        //                 sh '''
        //                     cd twitter-covid/HashtagByRegion
        //                     sbt package
        //                     cd ../..
        //                 '''
        //             }
        //         }
        //     }
        // }

        // stage("RelatedHashtags"){
        //     when {
        //         // If any of these branches then run the stages
        //         anyOf{
        //             branch 'main';
        //             branch 'develop';
        //             branch 'RelatedHashtags';
        //             branch '*/RelatedHashtags'
        //         }
        //     }
        //     stages {
        //         // RelatedHashtags Compile
        //         stage("Compile twitter-covid RelatedHashtags") {
        //             steps {
        //                 echo "Compile RelatedHashtags"

        //                 sh '''
        //                     cd twitter-covid/RelatedHashtags
        //                     sbt compile
        //                     cd ../..
        //                 '''
        //             }
        //         }
        //         // RelatedHashtags Test
        //         stage("Test twitter-covid RelatedHashtags") {
        //             steps {
        //                 echo "Test RelatedHashtags"

        //                 sh '''
        //                     cd twitter-covid/RelatedHashtags
        //                     sbt test
        //                     cd ../..
        //                 '''
        //             }
        //         }
        //         // RelatedHashtags Package
        //         stage("Package twitter-covid RelatedHashtags") {
        //             when {
        //                 // If any of these branches then run the stages
        //                 anyOf{
        //                     branch 'main';
        //                     branch 'develop';
        //                     branch 'deploy/FirstRegionalPeaks'
        //                 }
        //             }
        //             steps {
        //                 echo "Package RelatedHashtags"

        //                 sh '''
        //                     cd twitter-covid/RelatedHashtags
        //                     sbt package
        //                     cd ../..
        //                 '''
        //                 withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
        //                     AWS("--region=us-east-1 s3 cp herdimmunity-stockmarket/stockmarket-data/target/scala-2.12/stock_market_data_downloader_2.12-1.0.jar s3://covid-analysis-p3/modules/stock_market_data_downloader_2.12-1.0.jar")
        //                 }
        //             }
        //         }
        //     }
        // }
        


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
                            AWS("--region=us-east-1 s3 cp twitter-general/data-cleaner/target/scala-2.12/datacleaner_2.12-1.jar s3://covid-analysis-p3/modules/datacleaner_2.12-1.jar")
                        }
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
                            AWS("--region=us-east-1 s3 cp twitter-general/hashtag-count-comparison/target/scala-2.12/hashtagcountcomparison_2.12-1.jar s3://covid-analysis-p3/modules/hashtagcountcomparison_2.12-1.jar")
                        }
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
                            AWS("--region=us-east-1 s3 cp twitter-general/tweet-covid19-words/target/scala-2.12/twitter-general-word-count.jar s3://covid-analysis-p3/modules/twitter-general-word-count.jar")
                        }
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
                            AWS("--region=us-east-1 s3 cp twitter-general/tweet-covid19-emoji/target/scala-2.12/TweetCovid19Emoji-assembly-1.jar s3://covid-analysis-p3/modules/TweetCovid19Emoji-assembly-1.jar")
                        }
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
                            AWS("--region=us-east-1 s3 cp twitter-general/tweet-covid19-percentage/target/scala-2.12/twittercovid19percentageanalysis_2.12-1.jar s3://covid-analysis-p3/modules/twittercovid19percentageanalysis_2.12-1.jar")
                        }
                    }
                }
            }
        }



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
                            AWS("--region=us-east-1 s3 cp herdimmunity-stockmarket/stockmarket/target/scala-2.12/stock_market_composite_index_change_calculator_2.12-1.jar s3://covid-analysis-p3/modules/stock_market_composite_index_change_calculator_2.12-1.jar")
                        }
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
                //stockmarket-data Package
                stage("Package stockmarket-data") {
                    when {
                        // If any of these branches then run the stages
                        anyOf{
                            branch 'main';
                            branch 'develop';
                            branch 'deploy/FirstRegionalPeaks'
                        }
                    }
                    steps {
                        echo "Package stockmarket-data"

                        sh '''
                            cd herdimmunity-stockmarket/stockmarket-data
                            sbt package
                            cd ../..
                        '''
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                            AWS("--region=us-east-1 s3 cp herdimmunity-stockmarket/stockmarket-data/target/scala-2.12/stock_market_data_downloader_2.12-1.0.jar s3://covid-analysis-p3/modules/stock_market_data_downloader_2.12-1.0.jar")
                        }
                    }
                }
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
                //             AWS("--region=us-east-1 s3 cp herdimmunity-stockmarket/herdimmunity/target/scala-2.12/herdimmunity_2.12-1.0.jar s3://covid-analysis-p3/modules/herdimmunity_2.12-1.0.jar")
                //         }
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