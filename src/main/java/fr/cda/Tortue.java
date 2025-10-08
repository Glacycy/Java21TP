package fr.cda;

public final class Tortue extends AbstractAnimal {
    private static final int VITESSE_MIN = 1;
    private static final int VITESSE_MAX = 2;
    private static final int PAUSE_MIN = 300;
    private static final int PAUSE_MAX = 500;

    public Tortue(String nom, Course course) {
        super(nom, "🐢", course, VITESSE_MIN, VITESSE_MAX, PAUSE_MIN, PAUSE_MAX);
    }
}
