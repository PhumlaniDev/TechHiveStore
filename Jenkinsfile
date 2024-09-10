pipeline {
    agent {
        // Define custom Docker agent with Java Azul Zulu 17, Maven, PostgreSQL, and Docker CLI
        docker {
            image 'aphumlanidev/docker-jenkins-agent:latest' // Replace with the actual custom Docker agent image
            args '-v /var/run/docker.sock:/var/run/docker.sock' // Enable Docker-in-Docker
        }
    }

    environment {
        POSTGRES_DB = 'tech_hive_db'
        POSTGRES_USER = 'postgres'
        POSTGRES_PASSWORD = 'postgres'
        SPRING_DATASOURCE_URL = 'jdbc:postgresql://localhost:5432/tech_hive_db'
        SPRING_DATASOURCE_USERNAME = 'postgres'
        SPRING_DATASOURCE_PASSWORD = 'postgres'
        DOCKERHUB_USERNAME = credentials('DOCKERHUB_USERNAME')
        DOCKERHUB_PASSWORD = credentials('DOCKERHUB_PASSWORD')
        SONAR_TOKEN = credentials('SONAR_TOKEN')
        IMAGE_NAME = 'tech-hive-store'
    }

    stages {

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Unit Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Dockerize and Push') {
            steps {
                script {
                    sh """
                    docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .
                    docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}
                    docker push ${IMAGE_NAME}:${IMAGE_TAG}
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
