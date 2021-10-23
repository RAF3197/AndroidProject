pipeline {
    
    agent any

    tools{
        gradle 'gradle'
    }

    environment {
        ANDROID_SDK_ROOT = tool name: 'ANDROID_SDK', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
    }

    stages {

        stage('Clean and Build') {
            steps {
                sh "ls -la ${env.ANDROID_SDK_ROOT}"
                sh "ls -la ${env.ANDROID_SDK_ROOT}/bin"
                sh "yes | ${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --licenses"
                sh './gradlew assembleDebug'
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