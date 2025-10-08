## Description

Trois animaux (🐢 Tortue, 🐇 Lapin, 🐎 Cheval) s'affrontent dans une course.
Chaque animal avance à son propre rythme avec des pauses aléatoires.
Le premier à franchir la ligne d'arrivée remporte la victoire.

La progression s'affiche en temps réel dans la console.

## Prérequis

- Java 21
- Maven

## Lancer l'application

### Compilation et exécution

```bash
mvn compile
mvn exec:java -Dexec.mainClass="fr.cda.Main"
```

### Ou en une seule commande

```bash
mvn compile exec:java -Dexec.mainClass="fr.cda.Main"
```

### Package et exécution du JAR

```bash
mvn package
java -cp target/TPJava21Maven-1.0-SNAPSHOT.jar fr.cda.Main
```

## Architecture du projet

```
src/main/java/fr/cda/
├── Animal.java              # Interface sealed définissant le contrat de base
├── AbstractAnimal.java      # Classe abstraite sealed avec logique commune (Thread, avancement)
├── Tortue.java             # Animal lent (vitesse 1-2, pause 300-500ms)
├── Lapin.java              # Animal rapide (vitesse 3-5, pause 100-300ms)
├── Cheval.java             # Animal moyen (vitesse 2-4, pause 150-350ms)
├── Course.java             # Gère la synchronisation, victoire et classement
├── RaceDisplay.java        # Thread d'affichage temps réel de la course
└── Main.java               # Point d'entrée de l'application
```

### Rôle des fichiers

#### `Animal.java`
Interface sealed qui définit le contrat pour tous les animaux. Utilise les sealed classes de Java 21 pour restreindre l'implémentation à `AbstractAnimal` uniquement.

#### `AbstractAnimal.java`
Classe abstraite sealed qui implémente la logique commune à tous les animaux :
- Gestion du thread (Runnable)
- Calcul de l'avancement avec vitesse aléatoire
- Vérification de l'arrivée et déclaration de victoire

#### `Tortue.java`, `Lapin.java`, `Cheval.java`
Classes finales représentant chaque type d'animal. Chaque classe définit ses constantes spécifiques :
- Vitesse minimale et maximale (nombre de pas par mouvement)
- Pause minimale et maximale (temps de repos en millisecondes)

#### `Course.java`
Classe centrale qui orchestre la course :
- Gère la liste des participants
- Assure la synchronisation avec `AtomicBoolean` pour éviter les double-victoires
- Démarre tous les threads (animaux + affichage)
- Affiche le classement final avec médailles

#### `RaceDisplay.java`
Thread dédié à l'affichage en temps réel :
- Rafraîchit la console toutes les 200ms
- Affiche la progression de chaque animal sous forme de ligne avec emoji
- Gère le nettoyage de la console (Windows et Unix)

#### `Main.java`
Point d'entrée de l'application :
- Crée une course avec distance totale de 100
- Instancie les trois animaux
- Lance la course
