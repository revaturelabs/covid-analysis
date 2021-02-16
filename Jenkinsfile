@Library('github.com/releaseworks/jenkinslib') _
// Declarative Pipeline

// Top-most element in a scripted pipeline would be `node`

pipeline {
    // Required, tells us what machine should be running this build.
    agent any

    // Define our pipeline into stages
    stages {

        // //Infection-mortality group
        // //infection-mortality/CovidLiveUpdateApp Compile
        // stage("Compile infection-mortality/CovidLiveUpdateApp") {
        //     steps {
        //         echo "Compile CovidLiveUpdateApp"

        //         sh '''
        //             cd infection-mortality/CovidLiveUpdateApp
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //infection-mortality/CovidLiveUpdateApp Test
        // stage("Test infection-mortality/CovidLiveUpdateApp") {
        //     steps {
        //         echo "Test CovidLiveUpdateApp"

        //         sh '''
        //             cd infection-mortality/CovidLiveUpdateApp
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        //infection-mortality/CovidLiveUpdateApp Package
        stage("Package infection-mortality/CovidLiveUpdateApp") {
            steps {
                echo "Package CovidLiveUpdateApp"

                sh '''
                    cd infection-mortality/CovidLiveUpdateApp
                    sbt package
                    cd ../..
                '''
            }
        }



        // //infection-mortality/RegionalInfectionRates Compile
        // stage("Compile infection-mortality/RegionalInfectionRates") {
        //     steps {
        //         echo "Compile RegionalInfectionRates"

        //         sh '''
        //             cd infection-mortality/RegionalInfectionRates
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //infection-mortality/RegionalInfectionRates Test
        // stage("Test infection-mortality/RegionalInfectionRates") {
        //     steps {
        //         echo "Test RegionalInfectionRates"

        //         sh '''
        //             cd infection-mortality/RegionalInfectionRates
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        //infection-mortality/RegionalInfectionRates Package
        stage("Package infection-mortality/RegionalInfectionRates") {
            steps {
                echo "Package RegionalInfectionRates"

                sh '''
                    cd infection-mortality/RegionalInfectionRates
                    sbt package
                    cd ../..
                '''
            }
        }



        // // Group group-econRepsponse 
        // // group-econResponse/CorrelateInfectionGDP Compile
        // stage("Compile group-econResponse/CorrelateInfectionGDP") {
        //     steps{
        //         echo "Compile CorrelateInfectionGDP"
        //         sh '''
        //             cd group-econResponse/CorrelateInfectionGDP
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        //group-econResponse/CorrelateInfectionGDP Test
        stage("Test group-econResponse/CorrelateInfectionGDP") {
            steps{
                echo "Test CorrelateInfectionGDP"
                sh '''
                    cd group-econResponse/CorrelateInfectionGDP
                    sbt test
                    cd ../..
                '''
            }
        }
        //group-econResponse/CorrelateInfectionGDP Package
        stage("Package group-econResponse/CorrelateInfectionGDP") {
            steps{
                echo "Package CorrelateInfectionGDP"
                sh '''
                    cd group-econResponse/CorrelateInfectionGDP
                    sbt package
                    cd ../..
                '''
            }
        }



        // //group-econResponse/CountryBorders Compile
        // stage("Compile group-econResponse/CountryBorders") {
        //     steps{
        //         echo "Compile CountryBorders"
        //         sh '''
        //             cd group-econResponse/CountryBorders
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        //group-econResponse/CountryBorders Test
        stage("Test group-econResponse/CountryBorders") {
            steps{
                echo "Test CountryBorders"
                sh '''
                    cd group-econResponse/CountryBorders
                    sbt test
                    cd ../..
                '''
            }
        }
        //group-econResponse/CountryBorders Package
        stage("Package group-econResponse/CountryBorders") {
            steps{
                echo "Package CountryBorders"
                sh '''
                    cd group-econResponse/CountryBorders
                    sbt package
                    cd ../..
                '''
            }
        }


        
        // //group-econResponse/CovidResponse Compile
        // stage("Compile group-econResponse/CovidResponse") {
        //     steps{
        //         echo "Compile CovidResponse"
        //         sh '''
        //             cd group-econResponse/CovidResponse
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        //group-econResponse/CovidResponse Test
        stage("Test group-econResponse/CovidResponse") {
            steps{
                echo "Test CovidResponse"
                sh '''
                    cd group-econResponse/CovidResponse
                    sbt test
                    cd ../..
                '''
            }
        }
        //group-econResponse/CovidResponse Package
        stage("Package group-econResponse/CovidResponse") {
            steps{
                echo "Package CovidResponse"
                sh '''
                    cd group-econResponse/CovidResponse
                    sbt package
                    cd ../..
                '''
            }
        }



        // //group-econResponse/FirstRegionalPeaks Compile
        // stage("Compile group-econResponse/FirstRegionalPeaks") {
        //     steps{
        //         echo "Compile FirstRegionalPeaks"
        //         sh '''
        //             cd group-econResponse/FirstRegionalPeaks
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        //group-econResponse/FirstRegionalPeaks Test
        stage("Test group-econResponse/FirstRegionalPeaks") {
            steps{
                echo "Test FirstRegionalPeaks"
                sh '''
                    cd group-econResponse/FirstRegionalPeaks
                    sbt test
                    cd ../..
                '''
            }
        }
        //group-econResponse/FirstRegionalPeaks Package
        stage("Package group-econResponse/FirstRegionalPeaks") {
            steps{
                echo "Package FirstRegionalPeaks"
                sh '''
                    cd group-econResponse/FirstRegionalPeaks
                    sbt package
                    cd ../..
                '''
            }
        }



        // //twitter-covid Group
        // //twitter-covid/age-spikes-discussion Compile
        // stage("Compile twitter-covid/age-spikes-discussion") {
        //     steps {
        //         echo "Compile age-spikes-discussion"

        //         sh '''
        //             cd twitter-covid/age-spikes-discussion
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //twitter-covid/age-spikes-discussion Test
        // stage("Test twitter-covid/age-spikes-discussion") {
        //     steps {
        //         echo "Test age-spikes-discussion"

        //         sh '''
        //             cd twitter-covid/age-spikes-discussion
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        // //twitter-covid/age-spikes-discussion Package
        // stage("Package twitter-covid/age-spikes-discussion") {
        //     steps {
        //         echo "Package age-spikes-discussion"

        //         sh '''
        //             cd twitter-covid/age-spikes-discussion
        //             sbt package
        //             cd ../..
        //         '''
        //     }
        // }



        // //twitter-covid/HashtagByRegion Compile
        // stage("Compile twitter-covid/HashtagByRegion") {
        //     steps {
        //         echo "Compile HashtagByRegion"

        //         sh '''
        //             cd twitter-covid/HashtagByRegion
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //twitter-covid/HashtagByRegion Test
        // stage("Test twitter-covid/HashtagByRegion") {
        //     steps {
        //         echo "Test HashtagByRegion"

        //         sh '''
        //             cd twitter-covid/HashtagByRegion
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        // //twitter-covid/HashtagByRegion Package
        // stage("Package twitter-covid/HashtagByRegion") {
        //     steps {
        //         echo "Package HashtagByRegion"

        //         sh '''
        //             cd twitter-covid/HashtagByRegion
        //             sbt package
        //             cd ../..
        //         '''
        //     }
        // }



        // //Group Twitter-General
        // //Twitter-General/data-cleaner Compile
        // stage("Compile Twitter-General/data-cleaner") {
        //     steps {
        //         echo "Compile data-cleaner"

        //         sh '''
        //             cd twitter-general/data-cleaner
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/data-cleaner Test DIDNT PASS
        // stage("Test Twitter-General/data-cleaner") {
        //     steps {
        //         echo "Test data-cleaner"

        //         sh '''
        //             cd twitter-general/data-cleaner
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/data-cleaner Package
        // stage("Package Twitter-General/data-cleaner") {
        //     steps {
        //         echo "Package data-cleaner"

        //         sh '''
        //             cd twitter-general/data-cleaner
        //             sbt package
        //             cd ../..
        //         '''
        //     }
        // }



        // //Twitter-General/hashtag-count-comparison Compile
        // stage("Compile Twitter-General/hashtag-count-comparison") {
        //     steps {
        //         echo "Compile hashtag-count-comparison"

        //         sh '''
        //             cd twitter-general/hashtag-count-comparison
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/hashtag-count-comparison Test DIDNT PASS
        // stage("Test Twitter-General/hashtag-count-comparison") {
        //     steps {
        //         echo "Test hashtag-count-comparison"

        //         sh '''
        //             cd twitter-general/hashtag-count-comparison
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        //Twitter-General/hashtag-count-comparison Package
        // stage("Package Twitter-General/hashtag-count-comparison") {
        //     steps {
        //         echo "Package hashtag-count-comparison"

        //         sh '''
        //             cd twitter-general/hashtag-count-comparison
        //             sbt package
        //             cd ../..
        //         '''
        //     }
        // }



        //Twitter-General/tweet-covid19-words Compile
        // stage("Compile Twitter-General/tweet-covid19-words") {
        //     steps {
        //         echo "Compile tweet-covid19-words"

        //         sh '''
        //             cd twitter-general/tweet-covid19-words
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/tweet-covid19-words Test DIDNT PASS
        // stage("Test Twitter-General/tweet-covid19-words") {
        //     steps {
        //         echo "Test tweet-covid19-words"

        //         sh '''
        //             cd twitter-general/tweet-covid19-words
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/tweet-covid19-words Package
        // stage("Package Twitter-General/tweet-covid19-words") {
        //     steps {
        //         echo "Package tweet-covid19-words"

        //         sh '''
        //             cd twitter-general/tweet-covid19-words
        //             sbt package
        //             cd ../..
        //         '''
        //     }
        // }



        // //Twitter-General/tweet-covid19-emoji Compile
        // stage("Compile Twitter-General/tweet-covid19-emoji") {
        //     steps {
        //         echo "Compile tweet-covid19-emoji"

        //         sh '''
        //             cd twitter-general/tweet-covid19-emoji
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/tweet-covid19-emoji Test
        // stage("Test Twitter-General/tweet-covid19-emoji") {
        //     steps {
        //         echo "Test tweet-covid19-emoji"

        //         sh '''
        //             cd twitter-general/tweet-covid19-emoji
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/tweet-covid19-emoji Assembly
        // stage("Assembly Twitter-General/tweet-covid19-emoji") {
        //     steps {
        //         echo "Assembly tweet-covid19-emoji"

        //         sh '''
        //             cd twitter-general/tweet-covid19-emoji
        //             sbt assembly
        //             cd ../..
        //         '''
        //     }
        // }



        // //Twitter-General/tweet-covid19-percentage Compile
        // stage("Compile Twitter-General/tweet-covid19-percentage") {
        //     steps {
        //         echo "Compile tweet-covid19-percentage"

        //         sh '''
        //             cd twitter-general/tweet-covid19-percentage
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/tweet-covid19-percentage Test
        // stage("Test Twitter-General/tweet-covid19-percentage") {
        //     steps {
        //         echo "Test tweet-covid19-percentage"

        //         sh '''
        //             cd twitter-general/tweet-covid19-percentage
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        // //Twitter-General/tweet-covid19-percentage Package
        // stage("Package Twitter-General/tweet-covid19-percentage") {
        //     steps {
        //         echo "Package tweet-covid19-percentage"

        //         sh '''
        //             cd twitter-general/tweet-covid19-percentage
        //             sbt package
        //             cd ../..
        //         '''
        //     }
        // }



        
        



        // //twitter-general/tweet-positive-negative Compile
        // stage("Compile twitter-general/tweet-positive-negative") {
        //     steps {
        //         echo "Compile tweet-positive-negative"

        //         sh '''
        //             cd twitter-general/tweet-positive-negative
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        //twitter-general/tweet-positive-negative Test
        // stage("Test Twitter-General/tweet-positive-negative") {
        //     steps {
        //         echo "Test tweet-positive-negative"

        //         sh '''
        //             cd twitter-general/tweet-positive-negative
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        // //twitter-general/tweet-positive-negative Assembly
        // stage("Assembly Twitter-General/tweet-positive-negative") {
        //     steps {
        //         echo "Assembly tweet-positive-negative"

        //         sh '''
        //             cd twitter-general/tweet-positive-negative
        //             sbt assembly
        //             cd ../..
        //         '''
        //     }
        // }



        // //Stockmarket Group
        // //stockmarket Compile
        // stage("Compile stockmarket") {
        //     steps {
        //         echo "Compile stockmarket"

        //         sh '''
        //             cd stockmarket
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // //stockmarket Test FAILED
        // stage("Test stockmarket") {
        //     steps {
        //         echo "Test stockmarket"

        //         sh '''
        //             cd stockmarket
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
        // //stockmarket Package
        // stage("Package stockmarket") {
        //     steps {
        //         echo "Package stockmarket"

        //         sh '''
        //             cd stockmarket
        //             sbt package
        //             cd ../..
        //         '''
        //     }
        // }



        // //stockmarket Compile
        // stage("Compile stockmarket-data") {
        //     steps {
        //         echo "Compile stockmarket-data"

        //         sh '''
        //             cd stockmarket-data
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        //stockmarket Test
        stage("Test stockmarket-data") {
            steps {
                echo "Test stockmarket-data"

                sh '''
                    cd stockmarket-data
                    sbt test
                    cd ../..
                '''
            }
        }
        //stockmarket Package
        stage("Package stockmarket-data") {
            steps {
                echo "Package stockmarket-data"

                sh '''
                    cd stockmarket-data
                    sbt package
                    cd ../..
                '''
            }
        }



        // // RelatedHashtags Compile
        // stage("Compile twitter-covid RelatedHashtags") {
        //     steps {
        //         echo "Compile RelatedHashtags"

        //         sh '''
        //             cd twitter-covid/RelatedHashtags
        //             sbt compile
        //             cd ../..
        //         '''
        //     }
        // }
        // RelatedHashtags Test
        stage("Test twitter-covid RelatedHashtags") {
            steps {
                echo "Test RelatedHashtags"

                sh '''
                    cd twitter-covid/RelatedHashtags
                    sbt test
                    cd ../..
                '''
            }
        }
        // RelatedHashtags Package
        stage("Package twitter-covid RelatedHashtags") {
            steps {
                echo "Package RelatedHashtags"

                sh '''
                    cd twitter-covid/RelatedHashtags
                    sbt package
                    cd ../..
                '''
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

            script {
                if( env.BRANCH == 'main' || env.BRANCH == 'develop' ){

                    //If aws cli works on jenkins
                    //sh "aws s3 cp infection-mortality/CovidLiveUpdateApp/target/scala-2.12/covidliveupdate_2.12-1.jar s3://covid-analysis-p3/covidliveupdate_2.12-1.jar"
                    //sh "aws s3 cp infection-mortality/RegionalInfectionRates/target/scala-2.12/regionalinfectionrates_2.12-1.jar s3://covid-analysis-p3/regionalinfectionrates_2.12-1.jar"

                    //AWS cli with github 3rd party library
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'aws-key', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                        AWS("s3 cp infection-mortality/CovidLiveUpdateApp/target/scala-2.12/covidliveupdate_2.12-1.jar s3://covid-analysis-p3/covidliveupdate_2.12-1.jar")
                        AWS("s3 cp infection-mortality/RegionalInfectionRates/target/scala-2.12/regionalinfectionrates_2.12-1.jar s3://covid-analysis-p3/regionalinfectionrates_2.12-1.jar")
                    }

                    // Rebuild the react project here (only if the branch of the react project is changed): Strech Goal

                }
            }
        }

        unstable {
            echo "Unstable build."
            echo "'Unstable' email has been sent to: Everyone"
        }

        failure {
            echo "Something didn't pass."
            echo "'Failure' email has been sent to: Everyone"
        }

        changed {
            echo "Changes have been made."
        }

    }
}