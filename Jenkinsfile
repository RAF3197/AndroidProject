pipeline {
    
    agent any

    tools{
        gradle 'gradle'
    }

    environment {
        ANDROID_SDK_ROOT = tool name: 'ANDROID_SDK', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
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
                sh "ls -la"
                sh "ls -la /var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/licenses"
                sh "cp -rf ./licenses /var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/"
                sh "chmod -R 777 /var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/"
                sh "ls -la /var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/licenses"
                sh "export ANDROID_SDK_ROOT=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/"
                sh "export SDK_ROOT=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/"
                sh "export ANDROID_HOME=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/"
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/sdkmanager --update"
                sh "yes | ${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/sdkmanager --licenses"
                sh "ls -la /var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/licenses"
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