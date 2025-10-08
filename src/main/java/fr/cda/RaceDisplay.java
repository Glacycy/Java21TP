package fr.cda;

public class RaceDisplay implements Runnable {
    private final Course course;
    private static final int REFRESH_INTERVAL = 200;

    public RaceDisplay(Course course) {
        this.course = course;
    }

    @Override
    public void run() {
        while (!course.estTerminee()) {
            afficherProgression();
            try {
                Thread.sleep(REFRESH_INTERVAL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        afficherProgression();
    }

    private void afficherProgression() {
        clearConsole();
        System.out.println("🏁 COURSE EN COURS 🏁\n");

        for (Animal animal : course.getParticipants()) {
            afficherLigne(animal);
        }

        System.out.println("\n" + "-".repeat(50));
    }

    private void afficherLigne(Animal animal) {
        int distanceTotale = course.getDistanceTotale();
        int position = animal.getPosition();

        StringBuilder ligne = new StringBuilder();
        ligne.append(String.format("%-8s : ", animal.getNom()));

        int positionAffichage = (int) ((double) position / distanceTotale * 40);

        for (int i = 0; i < 40; i++) {
            if (i == positionAffichage) {
                ligne.append(animal.getEmoji());
            } else if (i < 40) {
                ligne.append("-");
            }
        }

        ligne.append(String.format(" [%d/%d]", position, distanceTotale));

        System.out.println(ligne);
    }

    private void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                //Unix, Linux, MacOS
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
