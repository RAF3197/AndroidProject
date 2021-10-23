pipeline {
    // Specify which agent to use
    agent any
    environment {
        ANDROID_SDK_HOME = tool name: 'ANDROID_SDK', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'

    }

    stages {

        stage('Clean and Build') {
            steps {
                //STEPS ARE BEING DUPLICATED BECAUSE ENV VAR SET FROM DIFFERENT STAGES DO NOT SAVE OVER
                // JAVA
                echo "Setting the JAVA_HOME env var..."
                sh "export JAVA_HOME=${env.JAVA_HOME}"
                echo "JAVA_HOME location is: ${env.JAVA_HOME}"

                // ANDROID SDK
                echo 'Setting the ANDROID_HOME env var...'
                sh "export ANDROID_HOME=${env.ANDROID_HOME}"
                echo "ANDROID_HOME location is: ${env.ANDROID_HOME}"

                // Gradle Android SDK location
                echo 'Creating a properties files for Gradle to identify which Android SDK to use...'
                sh "echo sdk.dir=${env.ANDROID_HOME} > local.properties" // Alternatively, you could set the Android home and let Gradle pick it up automatically
                
                //sh "export JAVA_OPTS='-XX:+IgnoreUnrecognizedVMOptions --add-modules java.se.ee'"

                echo "Cleaning the project using Gradle..."
                sh "${GRADLE_HOME}/bin/gradle clean --stacktrace"

                echo "Building the project..."
                sh "${GRADLE_HOME}/bin/gradle build --stacktrace"
                // For proxy escaping
                // -Dhttp.proxyHost=[proxyAddress] -Dhttp.proxyPort=[proxyPortNumber] -Dhttps.proxyHost=[proxyAddress] -Dhttps.proxyPort=[proxyPortNumber]
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