package fr.cda;

public final class Lapin extends AbstractAnimal {
    private static final int VITESSE_MIN = 3;
    private static final int VITESSE_MAX = 5;
    private static final int PAUSE_MIN = 100;
    private static final int PAUSE_MAX = 300;

    public Lapin(String nom, Course course) {
        super(nom, "🐇", course, VITESSE_MIN, VITESSE_MAX, PAUSE_MIN, PAUSE_MAX);
    }
}
