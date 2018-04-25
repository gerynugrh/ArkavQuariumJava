import java.awt.EventQueue;
import javax.swing.JFrame;

class Engine extends JFrame {

  static final int SCREEN_WIDTH = 640;
  static int SCREEN_HEIGHT = 480;

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
    setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}