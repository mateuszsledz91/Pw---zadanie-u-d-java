import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyPanel extends JPanel {
    private LinkedList<Kwadrat> listaKwadratow = new LinkedList<>();
    private LinkedList<Kwadrat> listaPracownikow = new LinkedList<>();

    static boolean[] stanowiska = {true, true, true}; // Tablica reprezentująca dostępność stanowisk

    private ExecutorService executorService = Executors.newFixedThreadPool(100); // Pula wątków
    private int squareCount = 0; // Liczba dodanych kwadratów
    private Timer addSquareTimer;

    public MyPanel() {




        addNewWorker(145, 150, 50, Color.GREEN); // Initial worker
        addNewWorker(145, 250, 50, Color.ORANGE); // Initial worker
        addNewWorker(145, 350, 50, Color.yellow); // Initial worker












        // Uruchom timer do dodawania nowych kwadratów co 1 sekundę
        addSquareTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewSquare(); // Dodaj nowy kwadrat
                squareCount++;
                if (squareCount == 12) {
                    addSquareTimer.setDelay(3000); // Zmień opóźnienie na 6 sekund po dodaniu 12 kwadratów
                }
            }
        });
        addSquareTimer.start(); // Uruchom timer

        // Uruchom timer do odświeżania panelu co 50 ms
        Timer repaintTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(); // Odśwież panel
            }
        });
        repaintTimer.start(); // Uruchom timer do odświeżania
    }

    private void addNewSquare() {
        Random random = new Random();
        int randomX = (int) (Math.random() * 650); // Losowa pozycja x w zakresie 0-600
        Color randomColor = new Color((int) (Math.random() * 0x1000000)); // Losowy kolor
        Kwadrat newKwadrat = new Kwadrat(-50, 600, 50, randomColor, 4, TypKwadratu.klient);
        listaKwadratow.add(newKwadrat);
        executorService.execute(new MySwingWorker(newKwadrat, listaKwadratow,listaPracownikow)); // Uruchom zadanie w puli wątków
    }


    private void addNewWorker(int x, int y, int size, Color color) {
        Kwadrat newPracownik = new Kwadrat(x, y, size, color, 4, TypKwadratu.pracownik);
        listaPracownikow.add(newPracownik);
        executorService.execute(new MySwingWorker(newPracownik, listaPracownikow, listaPracownikow)); // Zmodyfikowane wywołanie
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Rysowanie klientów
        for (Kwadrat kwadrat : listaKwadratow) {
            g.setColor(kwadrat.getColor());
            int size = kwadrat.getSize();
            Para<Integer> position = kwadrat.getPosition();
            g.fillRect(position.getWs_x(), position.getWs_y(), size, size);
        }




        // Rysowanie pracowników
        for (Kwadrat kwadrat : listaPracownikow) {
            g.setColor(kwadrat.getColor());
            int size = kwadrat.getSize();
            Para<Integer> position = kwadrat.getPosition();
            g.fillRect(position.getWs_x(), position.getWs_y(), size, size);
        }

        // Rysuj planszę i inne elementy (poza pętlą, aby nie rysować ich za każdym razem)
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(50, 100, 700, 100);
        g2d.drawLine(50, 600, 550, 600);
        g2d.drawLine(650, 600, 700, 600);
        g2d.drawLine(50, 100, 50, 600);
        g2d.drawLine(700, 100, 700, 600);

        g.setColor(Color.RED);
        g2d.drawString("Urząd taktak", 350, 40);

        g2d.setColor(Color.BLUE);
        g2d.drawLine(200, 120, 200, 200);
        g2d.drawLine(200, 220, 200, 300);
        g2d.drawLine(200, 320, 200, 400);

        //g2d.fillRect(230,250,50,50);

        g2d.setColor(Color.GREEN);
        for (int i = 0; i < stanowiska.length; i++) {
            if (stanowiska[i]) { // Jeśli stanowisko jest wolne
                g.fillOval(250, 160 + i * 100, 10, 10); // Rysuj stanowisko
            }
        }



    }
}
