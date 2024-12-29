pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/Mariam16999/Java-PIN-Encryption.git'
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
                    // Map container's port 8081 to host's port 8082
                    sh 'docker run -d -p 8082:8081 java-pin-encryption'
                }
            }
        }
    }
}
