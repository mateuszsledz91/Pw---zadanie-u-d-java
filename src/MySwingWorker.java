import javax.swing.*;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

class MySwingWorker extends SwingWorker<Void, Void> {
    private final Kwadrat kwadrat;
    private final LinkedList<Kwadrat> listaKwadratow;
    private final LinkedList<Kwadrat> listaPracownikow; // Dodano listę pracowników


    public MySwingWorker(Kwadrat kwadrat, LinkedList<Kwadrat> listaKwadratow, LinkedList<Kwadrat> listaPracownikow) {
        this.kwadrat = kwadrat;
        this.listaKwadratow = listaKwadratow;
        this.listaPracownikow = listaPracownikow; // Inicjalizacja listy pracowników
    }

    Random random = new Random();
    int pomoc = 1;
    int pomoc2 = -1;
    int kasa = -1;
    private static Semaphore mutex = new Semaphore(1);


    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            Thread.sleep(kwadrat.getSleepTime());

            synchronized (this) {
                if (kwadrat.getTypKwadratu() == TypKwadratu.klient) {
                    handleClient();
                }
                if (kwadrat.getTypKwadratu() == TypKwadratu.pracownik) {
                    handleWorker();
                }

            }
        }
    }

    private void handleWorker() throws InterruptedException {
        int randomWaitTime = random.nextInt(5000) + 4000;
        for (Kwadrat pracownik : listaPracownikow) {

            if (pracownik.getColor() == Color.red) {
                int index = listaPracownikow.indexOf(pracownik); // Pobierz indeks pracownika

                if(index == 0){
                    pomoc2 = 130;

                }else if(index == 1){
                    pomoc2 = 95;

                }else if(index == 2){
                    pomoc2 = 60;

                }



                Thread.sleep((long) (randomWaitTime * 1.5));





                for (int i = 0; i < 20; i++) {
                    pracownik.idz_w_lewo();
                    Thread.sleep(pracownik.getSleepTime());
                }

                for (int i = 0; i < pomoc2; i++) {
                    pracownik.idz_w_dol();
                    Thread.sleep(pracownik.getSleepTime());
                }




                Thread.sleep(pracownik.getSleepTime() + 100);
                changeWorkerColor(index, Color.orange);
                Thread.sleep(pracownik.getSleepTime() + 200);
                changeWorkerColor(index, Color.yellow);
                Thread.sleep(pracownik.getSleepTime() + 300);
                changeWorkerColor(index, Color.green);
                Thread.sleep(pracownik.getSleepTime() + 200);








                for (int i = 0; i < pomoc2; i++) {
                    pracownik.idz_w_gore();
                    Thread.sleep(pracownik.getSleepTime());
                }

                for (int i = 0; i < 20; i++) {
                    pracownik.idz_w_prawo();
                    Thread.sleep(pracownik.getSleepTime());
                }
                pomoc2 = 0;
            }
        }
    }






    private void handleClient() throws InterruptedException {
        int randomWaitTime = random.nextInt(5000) + 4000;
        if (pomoc == 0) {

            for (int i = 0; i < 270; i++) {
                while (CollisionDetector.collision_prawo(kwadrat, listaKwadratow)) {
                    mutex.acquire();
                }
                mutex.release();
                kwadrat.idz_w_prawo();
                Thread.sleep(kwadrat.getSleepTime());
            }
            Thread.sleep(kwadrat.getSleepTime() + 200);
            for (int i = 0; i < 60; i++) {
                kwadrat.idz_w_prawo();
                Thread.sleep(kwadrat.getSleepTime());
            }
            for (int i = 0; i < 50; i++) {
                kwadrat.idz_w_dol();
                Thread.sleep(kwadrat.getSleepTime());
            }
            for (int i = 0; i < 300; i++) {
                kwadrat.idz_w_prawo();
                Thread.sleep(kwadrat.getSleepTime());
            }
        } else if (pomoc == 1) {
            while (CollisionDetector.collision_prawo(kwadrat, listaKwadratow)) {
                mutex.acquire();
            }
            mutex.release();
            kwadrat.idz_w_prawo();

            if (kwadrat.getPosition().getWs_x() == 500) {
                while (CollisionDetector.kolejka_zapchana(kwadrat, listaKwadratow)) {
                    mutex.acquire();
                }
                mutex.release();
                Thread.sleep(kwadrat.getSleepTime() + 500);

                if (kwadrat.getPosition().getWs_x() == 500) {
                    while (CollisionDetector.ktos_bedzie_wychodzic(kwadrat, listaKwadratow)) {
                        mutex.acquire();
                    }
                    mutex.release();
                    pomoc = 2; // Wejdź do urzędu
                }
            }
        } else if (pomoc == 2) {


            Thread.sleep(kwadrat.getSleepTime() + 200);
            for (int i = 0; i < 60; i++) {
                kwadrat.idz_w_prawo();
                Thread.sleep(kwadrat.getSleepTime());
            }

            for (int i = 0; i < 350; i++) {
                while (CollisionDetector.isSquareInFront(kwadrat, listaKwadratow)) {
                    Thread.sleep(kwadrat.getSleepTime() + 500);
                }
                kwadrat.idz_w_gore();
                Thread.sleep(kwadrat.getSleepTime());
            }


            for (int i = 0; i < 150; i++) {
                while (CollisionDetector.isSquareInFront(kwadrat, listaKwadratow)) {
                    Thread.sleep(kwadrat.getSleepTime() + 500);
                }
                kwadrat.idz_w_lewo();
                Thread.sleep(kwadrat.getSleepTime());
                if (kwadrat.getPosition().getWs_x() == 410) {
                    pomoc = 5;
                }
            }










            if (pomoc == 5) {
                Thread.sleep(kwadrat.getSleepTime() + 500);
                int freeStanowisko = -1;
                while ((freeStanowisko = findFreeStanowisko()) == -1) {
                    Thread.sleep(kwadrat.getSleepTime() + 500);
                }
                kasa = freeStanowisko;
                occupyStanowisko(kasa);


                if(getColorOfWorker(kasa) == Color.green) {
                    changeWorkerColor(kasa, Color.yellow);
                }else if(getColorOfWorker(kasa) == Color.yellow) {
                    changeWorkerColor(kasa, Color.orange);
                }else if(getColorOfWorker(kasa) == Color.orange) {
                    changeWorkerColor(kasa, Color.red);
                }


                Thread.sleep(kwadrat.getSleepTime() + 500);
            }

            if (kasa == 0) {
                for (int i = 0; i < 100; i++) {
                    kwadrat.idz_w_gore();
                    Thread.sleep(kwadrat.getSleepTime());
                }
                for (int i = 0; i < 180; i++) {
                    kwadrat.idz_w_lewo();
                    Thread.sleep(kwadrat.getSleepTime());
                }

                while (kwadrat.getPosition().getWs_x() == 145 && kwadrat.getPosition().getWs_x() == 150)  {
                    Thread.sleep(kwadrat.getSleepTime());
                }

                Thread.sleep(randomWaitTime);
                for (int i = 0; i < 400; i++) {
                    kwadrat.idz_w_dol();
                    Thread.sleep(kwadrat.getSleepTime());
                    if (i > 50) {
                        releaseStanowisko(kasa);
                        kasa = -1;
                    }
                }
            }

            if (kasa == 1) {
                for (int i = 0; i < 180; i++) {
                    kwadrat.idz_w_lewo();
                    Thread.sleep(kwadrat.getSleepTime());
                }


                while (kwadrat.getPosition().getWs_x() == 145 && kwadrat.getPosition().getWs_x() == 250)  {
                    Thread.sleep(kwadrat.getSleepTime());
                }
                Thread.sleep(randomWaitTime);
                for (int i = 0; i < 300; i++) {
                    kwadrat.idz_w_dol();
                    Thread.sleep(kwadrat.getSleepTime());
                    if (i > 50) {
                        releaseStanowisko(kasa);
                        kasa = -1;
                    }

                }
            }

            if (kasa == 2) {
                for (int i = 0; i < 100; i++) {
                    kwadrat.idz_w_dol();
                    Thread.sleep(kwadrat.getSleepTime());
                }

                while (kwadrat.getPosition().getWs_x() == 145 && kwadrat.getPosition().getWs_x() == 350)  {
                    Thread.sleep(kwadrat.getSleepTime());
                }

                for (int i = 0; i < 180; i++) {
                    kwadrat.idz_w_lewo();
                    Thread.sleep(kwadrat.getSleepTime());
                }
                Thread.sleep(randomWaitTime);
                for (int i = 0; i < 200; i++) {
                    kwadrat.idz_w_dol();
                    Thread.sleep(kwadrat.getSleepTime());
                    if (i > 50) {
                        releaseStanowisko(kasa);
                        kasa = -1;
                    }
                }
            }
            pomoc = 0;
        }
    }

    private synchronized int findFreeStanowisko() {
        Random random = new Random();
        int randomIndex = random.nextInt(MyPanel.stanowiska.length);
        for (int i = 0; i < MyPanel.stanowiska.length; i++) {
            if (MyPanel.stanowiska[randomIndex]) {
                return randomIndex;
            }
            randomIndex = (randomIndex + 1) % MyPanel.stanowiska.length;
        }
        return -1;
    }

    private synchronized void occupyStanowisko(int index) {
        if (index >= 0 && index < MyPanel.stanowiska.length) {
            MyPanel.stanowiska[index] = false;
        }
    }

    private synchronized void releaseStanowisko(int index) {
        if (index >= 0 && index < MyPanel.stanowiska.length) {
            MyPanel.stanowiska[index] = true;
        }
    }


    public synchronized Color getColorOfWorker(int index) {
        if (index >= 0 && index < listaPracownikow.size()) {
            return listaPracownikow.get(index).getColor();
        } else {
            throw new IllegalArgumentException("Index out of bounds for listaPracownikow");
        }
    }

    private synchronized void changeWorkerColor(int index, Color color) {
        if (index >= 0 && index < listaPracownikow.size()) {
            listaPracownikow.get(index).setColor(color);
            Color currentColor = getColorOfWorker(index); // Pobierz obecny kolor pracownika
        }
    }
}