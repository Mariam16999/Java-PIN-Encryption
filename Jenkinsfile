pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/Mariam16999/Java-PIN-Encryption.git'
        DOCKER_IMAGE = 'java-pin-encryption'
    }

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    // Clone the repository from GitHub
                    git branch: 'main', 
                        url: "${GIT_REPO}", 
                        credentialsId: 'git'
                }
            }
        }
        
        stage('Build JAR') {
            steps {
                script {
                    // Build the Java application JAR using Maven
                    sh 'mvn clean install'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image from the Dockerfile
                    sh 'docker build -t ${DOCKER_IMAGE} .'
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Run the container and map the ports
                    sh 'docker run -d -p 8082:8081 ${DOCKER_IMAGE}'
                }
            }
        }

        stage('Verify Application') {
            steps {
                script {
                    // Verify if the application is accessible on port 8082
                    sh 'curl http://localhost:8082'
                }
            }
        }
    }

    post {
        always {
            // Cleanup: Remove Docker containers after the build
            sh 'docker ps -aq | xargs docker rm -f || true'
        }
    }
}
