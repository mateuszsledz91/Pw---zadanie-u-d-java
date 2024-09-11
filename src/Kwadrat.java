import java.awt.*;

public class Kwadrat {
    private int x;
    private int y;
    private int size;
    private Color color;
    private int sleepTime;
    private TypKwadratu typKwadratu;

    public Kwadrat(int x, int y, int size, Color color, int sleepTime, TypKwadratu typKwadratu) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.sleepTime = sleepTime;
        this.typKwadratu = typKwadratu;
    }

    public synchronized Para<Integer> getPosition() {
        return new Para<>(x, y);
    }

    public synchronized void idz_w_prawo() {
        x++;
    }

    public synchronized void idz_w_lewo() {
        x--;
    }

    public synchronized void idz_w_gore() {
        y--;
    }

    public synchronized void idz_w_dol() {
        y++;
    }


    public Color getColor() {
        return color;
    }

    public synchronized void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public TypKwadratu getTypKwadratu() {
        return typKwadratu;
    }
}
