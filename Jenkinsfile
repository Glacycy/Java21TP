pipeline {
    agent { label 'NodeName' }

    stages {

        stage('Dependances') {
            steps {
                echo '=== Installation des dépendances ==='
                sh '''
                    sudo apt update -y
                    sudo apt install apache2 -y
                    sudo systemctl start apache2
                    sudo systemctl enable apache2
                '''
            }
        }

        stage('Checkout') {
            steps {
                echo '=== Récupération du code source ==='
                checkout scm
            }
        }

        stage('Deploy') {
            steps {
                echo '=== Déploiement des fichiers ==='
                sh '''
                    sudo cp -r * /var/www/html/
                    sudo chown -R www-data:www-data /var/www/html/
                '''
            }
        }

        stage('Test') {
            steps {
                echo '=== Vérification du déploiement ==='
                sh '''
                    if curl -s http://localhost | grep -q "<html>"; then
                        echo "Site déployé avec succès !"
                    else
                        echo "Erreur : le site ne répond pas correctement."
                        exit 1
                    fi
                '''
            }
        }
    }

    post {
        success {
            echo 'Déploiement réussi !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
        always {
            echo '=== Nettoyage du serveur ==='
            sh '''
                sudo rm -rf /var/www/html/*
                sudo apt remove --purge apache2 -y
                sudo apt autoremove -y
            '''
        }
    }
}
