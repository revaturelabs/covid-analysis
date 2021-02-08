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
        stage("Test") {
            steps {
                echo "this is a test."
            }
        }

        stage("Build") {
            steps {
                echo "this is a build."
                sbt compile
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