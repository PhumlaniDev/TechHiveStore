pipeline {
    agent any

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
    }

    stages {
        stage('Setup') {
            steps {
                script {
                    checkout scm
                    // Use your prebuilt Docker image with Java 17 and Maven
                    docker.image('aphumlanidev/docker-jenkins-agent:latest').inside {
                        sh 'mvn dependency:go-offline'
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    docker.image('postgres:latest').inside('-p 5432:5432') {
                        // Use the prebuilt image for Maven build
                        docker.image('aphumlanidev/docker-jenkins-agent:latest').inside {
                            sh 'mvn clean install'
                        }

                        sh '''
                        docker build -t $DOCKERHUB_USERNAME/tech-hive-store:${BUILD_NUMBER} .
                        docker tag $DOCKERHUB_USERNAME/tech-hive-store:${BUILD_NUMBER} $DOCKERHUB_USERNAME/tech-hive-store:latest
                        echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin
                        docker push $DOCKERHUB_USERNAME/tech-hive-store:${BUILD_NUMBER}
                        docker push $DOCKERHUB_USERNAME/tech-hive-store:latest
                        '''
                    }
                }
            }
        }

        stage('Unit Tests') {
            steps {
                script {
                    docker.image('postgres:latest').inside('-p 5432:5432') {
                        docker.image('aphumlanidev/docker-jenkins-agent:latest').inside {
                            sh 'mvn test'
                        }
                    }
                }
            }
        }

        stage('Code Analysis') {
            steps {
                script {
                    docker.image('postgres:latest').inside('-p 5432:5432') {
                        docker.image('aphumlanidev/docker-jenkins-agent:latest').inside {
                            sh 'mvn sonar:sonar -Dsonar.projectKey=PhumlaniDev_TechHiveStore -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_TOKEN'
                        }
                    }
                }
            }
        }

        stage('Vulnerability Scan') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'DOCKERHUB_CREDENTIALS') {
                        sh '''
                        docker pull $DOCKERHUB_USERNAME/tech-hive-store:${BUILD_NUMBER}
                        trivy image --format json --output trivy-report.json $DOCKERHUB_USERNAME/tech-hive-store:${BUILD_NUMBER}
                        '''
                    }
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
