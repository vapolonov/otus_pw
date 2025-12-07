pipeline {
    agent any

    tools {
        jdk 'jdk-17'
        maven 'maven-3.9'
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
                    jdk: 'jdk-17',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}