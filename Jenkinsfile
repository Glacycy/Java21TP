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
          sudo apt install -y apache2 curl
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
          set -eu

          # Remise à blanc du docroot
          sudo rm -rf /var/www/html/*

          # Démo: on génère une page de test (ton repo est un projet Java, pas un site statique)
          sudo tee /var/www/html/index.html >/dev/null <<'HTML'
          <!doctype html>
          <html lang="fr">
            <head><meta charset="utf-8"><title>Deploy OK</title></head>
            <body>
              <h1>Déploiement OK ✅</h1>
              <p>Commit: ${GIT_COMMIT}</p>
              <p>Date: $(date)</p>
            </body>
          </html>
          HTML

          sudo chown -R www-data:www-data /var/www/html/
          sudo systemctl reload apache2 || sudo systemctl restart apache2 || true
        '''
      }
    }

    stage('Test') {
      steps {
        echo '=== Vérification du déploiement ==='
        sh '''
          set -eu
          code=$(curl -sS -o /dev/null -w "%{http_code}" http://localhost/)
          echo "HTTP code: $code"
          case "$code" in
            2*|3*) echo "Site up ✅";;
            *) echo "Erreur: site down ❌ (code=$code)"; exit 1;;
          esac
          curl -sS http://localhost/ | grep -qi '<html'
        '''
      }
    }
  }

  post {
    success { echo '✅ Déploiement réussi !' }
    failure { echo '❌ Le pipeline a échoué.' }
    always {
      echo '=== Nettoyage léger (pas de purge Apache) ==='
      sh '''
        set -eu
        # Rien à nettoyer agressivement ici, on garde Apache pour le prochain run
        true
      '''
    }
  }
}
