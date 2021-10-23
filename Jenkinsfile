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
                sh "export ANDROID_SDK_ROOT=${env.ANDROID_SDK_ROOT}/cmdline-tools/"
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