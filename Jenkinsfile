pipeline {
    tools {
        dockerTool 'docker'
    }

    agent {
        docker { image 'raf97/androidprojectbuilder:latest' }
    }

    stages {
        stage('userAdd') {
            steps {
                sh 'sudo usermod -a -G docker jenkins'
            }
        }

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
