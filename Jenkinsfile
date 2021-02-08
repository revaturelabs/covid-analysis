// Declarative Pipeline

// Top-most element in a scripted pipeline would be `node`

pipeline {
    // Required, tells us what machine should be running this build.
    agent {
        docker { image "mozilla/docker-sbt" }
    }

    // Define our pipeline into stages
    stages {
        stage("Lint") {
            steps {
                echo "this is linting."
                sbt help
            }
        }
        stage("Test") {
            steps {
                echo "this is a test."
                sbt test
            }
        }

        stage("Build") {
            steps {
                echo "this is a build."
                sbt compile
            }
        }
    }
}