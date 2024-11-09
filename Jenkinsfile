pipeline {
    agent {
        docker {
            image 'docker-agent:latest' // Your custom Jenkins agent image
            args '-v /var/run/docker.sock:/var/run/docker.sock' // Mount Docker socket for docker commands
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
        stage('Checkout') {
            steps {
                // Checkout your repository from GitHub
                git 'https://github.com/PhumlaniDev/TechHiveStore.git'
            }
        }
        
        stage('Build') {
            steps {
                // Clean and build the project using Maven
                sh 'mvn clean install'
            }
        }

         stage('Unite Test') {
            steps {
                // Clean and build the project using Maven
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
