pipeline {
  agent any
  environment { AWS_REGION = 'sa-east-1' }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build com Maven') {
      steps {
        dir('unipdi-notificacao') {
          bat 'mvn -B clean package -DskipTests'
        }
      }
    }

    stage('Renomear JAR') {
      steps {
        dir('unipdi-notificacao') {
          bat 'rename target\\unipdi*.jar unipdi-notificacao.jar'
        }
      }
    }

    stage('Deploy na AWS') {
      steps {
        dir('unipdi-notificacao') {
          withCredentials([usernamePassword(credentialsId: 'aws-creds', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
            bat 'aws lambda update-function-code --function-name unipdi-notificacao --zip-file fileb://target\\\\unipdi-notificacao.jar --publish --region %AWS_REGION%'
          }
        }
      }
    }
  }
}
