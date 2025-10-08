package fr.cda;

public sealed interface Animal permits AbstractAnimal {
    String getNom();
    String getEmoji();
    int getPosition();
    void avancer();
    boolean estArrive();
}
