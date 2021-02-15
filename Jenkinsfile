// Declarative Pipeline

// Top-most element in a scripted pipeline would be `node`

pipeline {
    // Required, tells us what machine should be running this build.
    agent any

    // Define our pipeline into stages
    stages {

        //Infection-mortality group
        //CovidLiveUpdateApp Compile and Test
        stage("Compile infection-mortality CovidLiveUpdateApp") {
            steps {
                echo "Compile CovidLiveUpdateApp"

                sh '''
                    cd infection-mortality/CovidLiveUpdateApp
                    sbt compile
                    cd ../..
                '''
            }
        }
        
        stage("Test infection-mortality CovidLiveUpdateApp") {
            steps {
                echo "Test CovidLiveUpdateApp"

                sh '''
                    cd infection-mortality/CovidLiveUpdateApp
                    sbt test
                    cd ../..
                '''
            }
        }

        //RegionalInfectionRates Compile and Test
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

        //twitter-covid Group
        //age-spikes-discussion Compile and Test
        stage("Compile twitter-covid age-spikes-discussion") {
            steps {
                echo "Compile age-spikes-discussion"

                sh '''
                    cd twitter-covid/age-spikes-discussion
                    sbt compile
                    cd ../..
                '''
            }
        }

        stage("Test twitter-covid age-spikes-discussion") {
            steps {
                echo "Test age-spikes-discussion"

                sh '''
                    cd twitter-covid/age-spikes-discussion
                    sbt test
                    cd ../..
                '''
            }
        }

        //HashtagByRegion Compile and Test
        stage("Compile twitter-covid HashtagByRegion") {
            steps {
                echo "Compile HashtagByRegion"

                sh '''
                    cd twitter-covid/HashtagByRegion
                    sbt compile
                    cd ../..
                '''
            }
        }

        stage("Test twitter-covid HashtagByRegion") {
            steps {
                echo "Test HashtagByRegion"

                sh '''
                    cd twitter-covid/HashtagByRegion
                    sbt test
                    cd ../..
                '''
            }
        }

        //RelatedHashtags Compile and Test
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

        // stage("Test twitter-covid RelatedHashtags") {
        //     steps {
        //         echo "Test RelatedHashtags"

        //         sh '''
        //             cd twitter-covid/RelatedHashtags
        //             sbt test
        //             cd ../..
        //         '''
        //     }
        // }
    }

    // After action of testing
    post {
        always {
            echo "This will always be invoked."
        }

        // If the build passes
        success {
            echo "This will only run when the build passes."
        }

        failure {
            echo "This build failed. Ping the slack channel."
        }

        // Unstable, Changed, Always
    }
}