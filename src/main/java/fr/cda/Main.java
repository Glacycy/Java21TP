package fr.cda;

public class Main {
    public static void main(String[] args) {
        Course course = new Course(100);

        course.ajouterParticipant(new Tortue("Tortue", course));
        course.ajouterParticipant(new Lapin("Lapin", course));
        course.ajouterParticipant(new Cheval("Cheval", course));

        course.demarrer();
    }
}
