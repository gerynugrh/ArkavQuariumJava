import java.awt.EventQueue;
import javax.swing.JFrame;

public class Engine extends JFrame {

    public Engine() {

        initUI();
    }

    private void initUI() {

        add(new Game());

        setTitle("ArkavQuarium");
        setSize(640, 480);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Engine ex = new Engine();
            ex.setVisible(true);
        });
    }
}