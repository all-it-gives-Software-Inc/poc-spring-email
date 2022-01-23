pipeline {
    agent any
    tools {
        maven 'Maven 3.8.4'
        jdk 'jdk11'
    }
    stages {
        stage('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn clean compile'
                def pom = readMavenPom file:'pom.xml'
                print pom.version
                env.version = pom.version
                }
        }

        stage('Image') {
            dir ('discovery-service') {
                def app = docker.build "localhost:5000/discovery-service:${env.version}"
                app.push()
            }
        }

        stage ('Run') {
            docker.image("localhost:5000/discovery-service:${env.version}").run('-p 8761:8761 -h discovery --name discovery')
        }
    }
}

