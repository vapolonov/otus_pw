pipeline {
    agent any

    tools {
        maven 'maven 3.6.3'
    }

    environment {
        PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD = "1"
    }

    stages {

        stage('Install Playwright browsers') {
            steps {
                sh 'mvn -Pplaywright-browsers exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Allure report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}