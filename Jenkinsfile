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

        stage('Clean and Build') {
            steps {
                sh './gradlew clean assembleDebug'
            }
        }

        stage('dependencyCheck') {
            environment {
                checker = tool 'Depen-Check'
            }
            steps {
                sh "${checker}/bin/dependency-check.sh -s /var/jenkins_home/workspace/SocialOpenDataAndroid -f ALL"
                dependencyCheckPublisher pattern: 'dependency-check-report.xml'
            }
        }

        stage('scan') {
            environment {
                // Previously defined in the Jenkins "Global Tool Configuration"
                scannerHome = tool 'sonar'
            }
            steps {
                //sh './gradlew clean'
                // "sonarqube" is the server configured in "Configure System"
                withSonarQubeEnv('sonar') {
                    // Execute the SonarQube scanner with desired flags
                    sh "${scannerHome}/bin/sonar-scanner \
                          -Dsonar.projectKey=SocialOpenDataAndroid:Test \
                          -Dsonar.projectName=SocialOpenDataAndroid \
                          -Dsonar.host.url=http://192.168.1.44:9000 \
                          -Dsonar.login=admin \
                          -Dsonar.sources=app/src/main \
                          -Dsonar.password=19970331 \
                          -Dsonar.java.binaries=app/build \
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
