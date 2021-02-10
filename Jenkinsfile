// Declarative Pipeline

// Top-most element in a scripted pipeline would be `node`

pipeline {
    // Required, tells us what machine should be running this build.
    agent any

    // Define our pipeline into stages
    stages {
        stage("Lint") {
            steps {
                echo "this is linting."
            }
        }
        stage("Compile") {
            steps {
                echo "this is a build."

                
                cd infection-mortality/CovidLiveUpdateApp
                sbt compile
                sbt test
                cd ../..
                
            }
        }
        stage("Test") {
            steps {
                echo "this is a test."
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