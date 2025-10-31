pipeline {
  agent { label 'NodeName' }
  environment {
    DEBIAN_FRONTEND = 'noninteractive'
    LANG = 'C.UTF-8'
  }

  stages {

    stage('Dependances') {
      steps {
        echo '=== Installation des dépendances Apache ==='
        sh '''
          set -eu
          sudo apt update -y
          sudo apt install -y apache2
          sudo systemctl enable apache2 || true
          sudo systemctl start apache2 || true
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
          set -euxo pipefail

          # Nettoie le docroot sans exploser Apache
          sudo rm -rf /var/www/html/*

          # Si tu avais un site statique, tu copierais ici tes fichiers buildés
          # sudo cp -r dist/* /var/www/html/

          # Pour la démo, on génère une page de test
          sudo tee /var/www/html/index.html >/dev/null <<'HTML'
          <!doctype html>
          <html lang="fr">
            <head><meta charset="utf-8"><title>Deploy OK</title></head>
            <body>
              <h1>Déploiement OK</h1>
              <p>Commit: ${GIT_COMMIT}</p>
              <p>Date: $(date)</p>
            </body>
          </html>
          HTML

          # Droits
          sudo chown -R www-data:www-data /var/www/html/

          # Recharge Apache si besoin
          sudo systemctl reload apache2 || sudo systemctl restart apache2 || true
        '''
      }
    }

    stage('Test') {
      steps {
        echo '=== Vérification du déploiement ==='
        sh '''
          set -euo pipefail
          # On vérifie un code HTTP 200-399
          code=$(curl -sS -o /dev/null -w "%{http_code}" http://localhost/)
          echo "HTTP code: $code"
          case "$code" in
            2*|3*) echo "Site up ✅";;
            *) echo "Erreur: site down ❌ (code=$code)"; exit 1;;
          esac

          # Sanity check contenu
          curl -sS http://localhost/ | grep -qi '<html' || { echo "Pas de balise <html>"; exit 1; }
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
      echo '=== Nettoyage léger (pas de purge Apache) ==='
      sh '''
        set -euxo pipefail
        # On ne désinstalle pas Apache, on ne flingue pas le serveur après chaque run
        # Si tu veux remettre à blanc le docroot entre les builds:
        # sudo rm -rf /var/www/html/*
        true
      '''
    }
  }
}
