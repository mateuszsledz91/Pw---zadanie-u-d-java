import javax.swing.*;
import java.awt.*;

public class Okno extends JFrame {

    public Okno(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setLocationRelativeTo(null);
        MyPanel myPanel = new MyPanel();
        add(myPanel);
        setVisible(true);
    }
}
