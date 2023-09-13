pipeline{
    agent any

    tools{
        jdk "jdk-17"
        maven "M3"
    }

    stages {
        stage('Build'){
            steps {

            git 'https://github.com/jeleniasty/bet-app'

            sh "mvn clean compile"

            }
        }

        stage('Test'){
            steps {

            sh "mvn test"

            }
        }
    }
}