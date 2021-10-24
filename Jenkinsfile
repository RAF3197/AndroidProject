pipeline {
    agent {
        docker { image 'raf97/androidprojectbuilder:latest' }
    }

    stages {
        stage('Checkout') {
            steps {
                // Get Github repo using Github credentials (previously added to Jenkins credentials)
                checkout scm
            }
        }

        /*stage('Clean and Build') {
            steps {
                sh './gradlew clean assembleDebug'
            }
        }*/

        stage('scan') {
            environment {
                // Previously defined in the Jenkins "Global Tool Configuration"
                scannerHome = tool 'sonar'
            }
            steps {
                // "sonarqube" is the server configured in "Configure System"
                withSonarQubeEnv('sonar') {
                    // Execute the SonarQube scanner with desired flags
                    sh "${scannerHome}/bin/sonar-scanner \
                          -Dsonar.projectKey=SocialOpenDataAndroid:Test \
                          -Dsonar.projectName=SocialOpenDataAndroid \
                          -Dsonar.host.url=http://sonarQube:9000 \
                          -Dsonar.login=admin \
                          -Dsonar.password=19970331 \
                          -Dsonar.exclusions=dependency-check-*,report-*"
                }
                //timeout(time: 5, unit: 'MINUTES') {
                    // In case of SonarQube failure or direct timeout exceed, stop Pipeline
                //    waitForQualityGate abortPipeline: qualityGateValidation(waitForQualityGate())
               // }
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
