// Declarative Pipeline

// Top-most element in a scripted pipeline would be `node`

pipeline {
    // Required, tells us what machine should be running this build.
    agent any

    // Define our pipeline into stages
    stages {

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
    }

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