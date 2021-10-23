pipeline {
    tools {
        dockerTool 'docker'
    }

    stage('Initialize') {
        def dockerHome = tool 'docker'
        env.PATH = "${dockerHome}/bin:${env.PATH}"
    }

    agent {
        docker { image 'androidsdk/android-30:latest' }
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
