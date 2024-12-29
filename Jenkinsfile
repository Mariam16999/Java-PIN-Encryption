pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/Mariam16999/Java-PIN-Encryption.git'
    }

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    git branch: 'main', 
                        url: "${GIT_REPO}", 
                        credentialsId: 'git'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t java-pin-encryption .'
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                script {
                    sh 'docker run -d -p 8082:8081 java-pin-encryption'
                }
            }
        }
    }
}
