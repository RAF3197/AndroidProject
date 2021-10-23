pipeline {
    agent {
        docker { image 'raf97/androidprojectbuilder:latest' }
    }

    tools {
        dockerTool 'docker'
    }

    stages {
        stage('Checkout') {
            steps {
                // Get Github repo using Github credentials (previously added to Jenkins credentials)
                checkout scm
            }
        }

        stage('Clean and Build') {
            steps {
                sh 'docker -v'
                sh './gradlew clean assembleDebug'
            }
        }

        stage('Post-build') {
            steps {
                echo 'Tearing down...'
                cleanWs()
            }
        }
    }
}
