package fr.cda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Course {
    private final int distanceTotale;
    private final List<Animal> participants;
    private final AtomicBoolean terminee;
    private Animal vainqueur;
    private final RaceDisplay display;

    public Course(int distanceTotale) {
        this.distanceTotale = distanceTotale;
        this.participants = new ArrayList<>();
        this.terminee = new AtomicBoolean(false);
        this.vainqueur = null;
        this.display = new RaceDisplay(this);
    }

    public int getDistanceTotale() {
        return distanceTotale;
    }

    public void ajouterParticipant(Animal animal) {
        participants.add(animal);
    }

    public List<Animal> getParticipants() {
        return new ArrayList<>(participants);
    }

    public synchronized void declareVictoire(Animal animal) {
        if (terminee.compareAndSet(false, true)) {
            this.vainqueur = animal;
            System.out.println("\n🏆 " + animal.getNom() + " remporte la course! 🏆\n");
        }
    }

    public boolean estTerminee() {
        return terminee.get();
    }

    public Animal getVainqueur() {
        return vainqueur;
    }

    public void demarrer() {
        System.out.println("🏁 La course commence! 🏁\n");

        List<Thread> threads = new ArrayList<>();
        for (Animal animal : participants) {
            Thread thread = new Thread((Runnable) animal);
            threads.add(thread);
            thread.start();
        }

        Thread displayThread = new Thread(display);
        displayThread.start();

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        try {
            displayThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        afficherClassement();
    }

    private void afficherClassement() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CLASSEMENT FINAL");
        System.out.println("=".repeat(50));

        List<Animal> classement = new ArrayList<>(participants);
        classement.sort((a1, a2) -> Integer.compare(a2.getPosition(), a1.getPosition()));

        int position = 1;
        for (Animal animal : classement) {
            String medaille = switch (position) {
                case 1 -> "🥇";
                case 2 -> "🥈";
                case 3 -> "🥉";
                default -> "  ";
            };

            System.out.printf("%s %d. %s %s - Distance: %d/%d%n",
                    medaille, position, animal.getEmoji(), animal.getNom(),
                    animal.getPosition(), distanceTotale);
            position++;
        }

        System.out.println("=".repeat(50));
    }
}
