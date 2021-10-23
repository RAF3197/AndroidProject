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
                sh "export ANDROID_SDK_ROOT=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/"
                sh "export SDK_ROOT=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/"
                sh "export ANDROID_HOME=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/"
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ --update"
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ --licenses"
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ "+'"patcher;v4"'
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ "+'"build-tools;30.0.2"'
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ "+'"tools"'
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ "+'"platform-tools"'
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ "+'"emulator"'
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ "+'"platforms;android-30"'
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ --update"
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --sdk_root=/var/jenkins_home/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ANDROID_SDK/cmdline-tools/ --licenses"
                sh "${env.ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --licenses"
                
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