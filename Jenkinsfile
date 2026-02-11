pipeline {
    agent any

    tools {
        // Utilise exactement les noms que tu as configurés dans "Administrer Jenkins > Tools"
        jdk 'Java21' 
        maven 'M2_HOME' 
    }

    stages {
        stage('Nettoyage') {
            steps {
                echo 'Nettoyage de l’espace de travail...'
                sh 'mvn clean'
            }
        }

        stage('Checkout GIT') {
            steps {
                echo 'Récupération du code depuis GitHub...'
                // Remplace par l'URL de TON dépôt personnel
                git branch: 'main', url: 'https://github.com/Yasmine-esprit/Mission-Entreprise.git'
            }
        }

        stage('Compilation') {
            steps {
                echo 'Compilation du projet en cours...'
                sh 'mvn compile'
            }
        }

        stage('Tests') {
            steps {
                echo 'Exécution des tests unitaires...'
                sh 'mvn test'
            }
        }
    }

    post {
        success {
            echo 'Félicitations ! Le build est terminé avec succès (Icône verte).'
        }
        failure {
            echo 'Le build a échoué. Vérifie la console pour comprendre pourquoi.'
        }
    }
}
