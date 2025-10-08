package fr.cda;

import java.util.Random;

public abstract sealed class AbstractAnimal implements Animal, Runnable permits Tortue, Lapin, Cheval {
    protected final String nom;
    protected final String emoji;
    protected int position;
    protected final int distanceTotale;
    protected final Course course;
    protected final Random random;
    protected final int vitesseMin;
    protected final int vitesseMax;
    protected final int pauseMin;
    protected final int pauseMax;

    public AbstractAnimal(String nom, String emoji, Course course,
                         int vitesseMin, int vitesseMax,
                         int pauseMin, int pauseMax) {
        this.nom = nom;
        this.emoji = emoji;
        this.position = 0;
        this.distanceTotale = course.getDistanceTotale();
        this.course = course;
        this.random = new Random();
        this.vitesseMin = vitesseMin;
        this.vitesseMax = vitesseMax;
        this.pauseMin = pauseMin;
        this.pauseMax = pauseMax;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getEmoji() {
        return emoji;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void avancer() {
        if (!course.estTerminee() && !estArrive()) {
            int pas = random.nextInt(vitesseMax - vitesseMin + 1) + vitesseMin;
            position = Math.min(position + pas, distanceTotale);

            if (estArrive()) {
                course.declareVictoire(this);
            }
        }
    }

    @Override
    public boolean estArrive() {
        return position >= distanceTotale;
    }

    @Override
    public void run() {
        while (!course.estTerminee() && !estArrive()) {
            avancer();
            try {
                int pause = random.nextInt(pauseMax - pauseMin + 1) + pauseMin;
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
