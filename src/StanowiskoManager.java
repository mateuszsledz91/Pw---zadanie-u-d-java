import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

public class StanowiskoManager {
    private final LinkedList<Kwadrat> listaPracownikow;
    private final boolean[] stanowiska;

    public StanowiskoManager(LinkedList<Kwadrat> listaPracownikow, boolean[] stanowiska) {
        this.listaPracownikow = listaPracownikow;
        this.stanowiska = stanowiska;
    }

    public synchronized int findFreeStanowisko() {
        Random random = new Random();
        int randomIndex = random.nextInt(stanowiska.length);
        for (int i = 0; i < stanowiska.length; i++) {
            if (stanowiska[randomIndex]) {
                return randomIndex;
            }            randomIndex = (randomIndex + 1) % stanowiska.length;
        }
        return -1;
    }

    public synchronized void occupyStanowisko(int index) {
        if (index >= 0 && index < stanowiska.length) {
            stanowiska[index] = false;
        }
    }

    public synchronized void releaseStanowisko(int index) {
        if (index >= 0 && index < stanowiska.length) {
            stanowiska[index] = true;
        }
    }

    public synchronized Color getColorOfWorker(int index) {
        if (index >= 0 && index < listaPracownikow.size()) {
            return listaPracownikow.get(index).getColor();
        } else {
            throw new IllegalArgumentException("Index out of bounds for listaPracownikow");
        }
    }

    public synchronized void changeWorkerColor(int index, Color color) {
        if (index >= 0 && index < listaPracownikow.size()) {
            listaPracownikow.get(index).setColor(color);
        }
    }
}
