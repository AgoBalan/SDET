pipeline {
    agent any  // This specifies that the pipeline can run on any available agent,Agent means any conncted machine
    tools {
      maven 'Maven1' // Match the name from Global Tool Configuration
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                bat 'mvn clean' // Example build command for a Java project using Maven
            }
        }
         stage('Deploy to QA') {
            steps {
                echo 'Deploying to QA...'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                bat 'mvn test' // Example test command for a Java project using Maven
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }
}