import javax.swing.*;
import java.awt.*;

public class Engine extends JFrame {

    private Engine() {

        initUI();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Engine ex = new Engine();
            ex.setVisible(true);
        });
    }

    private void initUI() {

        add(new Game());

        setTitle("ArkavQuarium");
        setSize(640, 480);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}