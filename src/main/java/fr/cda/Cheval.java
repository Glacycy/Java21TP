package fr.cda;

public final class Cheval extends AbstractAnimal {
    private static final int VITESSE_MIN = 2;
    private static final int VITESSE_MAX = 4;
    private static final int PAUSE_MIN = 150;
    private static final int PAUSE_MAX = 350;

    public Cheval(String nom, Course course) {
        super(nom, "🐎", course, VITESSE_MIN, VITESSE_MAX, PAUSE_MIN, PAUSE_MAX);
    }
}
