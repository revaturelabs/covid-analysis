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
            }
        }
    }
}