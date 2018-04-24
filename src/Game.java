import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {

  static Random random = new Random(2);
  static int SCREEN_WIDTH, SCREEN_HEIGHT;
  private static int EGG_PRICE;
  private double prevTime;
  private double now;
  private double secSinceLast;
  private double timeStart;
  private double offset; // Save the value of pause duration
  private int numOfEgg;
  private boolean running;
  private boolean gameOver;
  private boolean win;
  private Aquarium aquarium;
  private BufferedImage background;

  Game() {
    timeStart = (double) System.currentTimeMillis() / 1000;
    System.out.println("Game started");
    numOfEgg = 0;
    offset = 0;
    running = true;
    gameOver = false;
    win = false;
    start();
  }

  private void start() {
    aquarium = new Aquarium();
    // Load and set game resources
    loadResources();
    setStaticVariable();
    // Add listener
    addKeyListener(new TAdapter());
    addMouseListener(new MouseAdapter());
    // Set scene property
    setFocusable(true);
    setDoubleBuffered(true);
    try {
      background = ImageIO.read(new File("res/background.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    // Set font
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    try {
      Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/fb.TTF"));
      ge.registerFont(customFont);
      System.out.println(customFont.getName());
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
    // Create a timer
    int DELAY = 10;
    Timer timer = new Timer(DELAY, this);
    timer.start();

    aquarium.collectors
        .add(new Snail(new Position(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 60), aquarium, now));
  }

  /**
   * Set all the static variable used in this game
   */
  private void setStaticVariable() {
    Game.SCREEN_HEIGHT = Engine.SCREEN_HEIGHT;
    Game.SCREEN_WIDTH = Engine.SCREEN_WIDTH;
    EGG_PRICE = 1000;

    // Guppy static variable
    Guppy.foodForUpgrade = 1;
    Guppy.timeForCoin = 5;
    Guppy.speed = 50;
    Guppy.price = 200;

    // Piranha static variable
    Piranha.speed = 60;
    Piranha.price = 500;

    Fish.timeUntilHungry = 5;
    Fish.timeUntilDead = 10;

    Pellet.speed = 30;
    Pellet.price = 20;

    Snail.speed = 30;

    Coin.speed = 30;

    prevTime = timeSinceStart();
  }

  /**
   * Redraw the screen
   *
   * @param g graphic where to draw the objects
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    doDrawing(g);

    Toolkit.getDefaultToolkit().sync();
  }

  /**
   * Draw all the objects that exist in aquarium
   *
   * @param g graphic where to draw the objects
   */
  private void doDrawing(Graphics g) {

    Graphics2D g2d = (Graphics2D) g;

    g2d.drawImage(background, 0, 0, this);

    g2d.setFont(new Font("04b_19", Font.PLAIN, 20));
    g2d.setColor(Color.white);
    g2d.drawString("Gold: " + aquarium.gold, 10, 30);
    g2d.drawString("Egg: " + numOfEgg, SCREEN_WIDTH - 100, 30);

    g2d.setFont(new Font("04b_19", Font.PLAIN, 14));
    g2d.setColor(Color.white);
    g2d.drawString("Guppy (g)  Piranha(p)  Egg(e)", 10, SCREEN_HEIGHT - 50);

    for (int i = 0; i < aquarium.edibles.length(); i++) {
      Edible edible = aquarium.edibles.get(i);
      drawObject(g2d, (AquariumObject) edible);
    }

    for (int i = 0; i < aquarium.valuables.length(); i++) {
      Valuable valuable = aquarium.valuables.get(i);
      drawObject(g2d, (AquariumObject) valuable);
    }

    for (int i = 0; i < aquarium.collectors.length(); i++) {
      Collector collector = aquarium.collectors.get(i);
      drawObject(g2d, (AquariumObject) collector);
    }

    for (int i = 0; i < aquarium.fishes.length(); i++) {
      Fish fish = aquarium.fishes.get(i);
      drawObject(g2d, fish);
    }

    if (!running && gameOver) {
      g2d.setFont(new Font("04b_19", Font.PLAIN, 40));
      g2d.setColor(Color.white);
      g2d.drawString("Game Over", SCREEN_WIDTH / 2 - 60, SCREEN_HEIGHT / 2);
    } else if (!running && win) {
      g2d.setFont(new Font("04b_19", Font.PLAIN, 40));
      g2d.setColor(Color.white);
      g2d.drawString("You win!", SCREEN_WIDTH / 2 - 60, SCREEN_HEIGHT / 2);
    } else if (!running) {
      g2d.setFont(new Font("04b_19", Font.PLAIN, 40));
      g2d.setColor(Color.white);
      g2d.drawString("Paused", SCREEN_WIDTH / 2 - 60, SCREEN_HEIGHT / 2);
    }
  }

  /**
   * Draw an object into the graphic object position will become the center of image
   *
   * @param g2d where to draw image
   * @param object object that is going to be drawn
   */
  private void drawObject(Graphics2D g2d, AquariumObject object) {
    int x = (int) object.position.x - object.getFrame().getWidth() / 2;
    int y = (int) object.position.y - object.getFrame().getHeight() / 2;

    g2d.drawImage(object.getFrame(), x, y, this);
  }

  /**
   * Define the action performed each time the timer fired off Call all the update method for each
   * entity and remove those that has flag destroyed
   *
   * @param e event that happens in the time span
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    repaint(new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT));
    if (running) {
      updateGameTime();
      for (int i = 0; i < aquarium.fishes.length(); i++) {
        Fish fish = aquarium.fishes.get(i);
        if (!fish.getDestroyed()) {
          fish.update(now, secSinceLast);
        } else {
          aquarium.fishes.remove(fish);
        }
      }
      for (int i = 0; i < aquarium.collectors.length(); i++) {
        Collector collector = aquarium.collectors.get(i);
        collector.update(now, secSinceLast);
      }
      for (int i = 0; i < aquarium.valuables.length(); i++) {
        Valuable valuable = aquarium.valuables.get(i);
        valuable.update(now, secSinceLast);
      }
      for (int i = 0; i < aquarium.edibles.length(); i++) {
        Edible edible = aquarium.edibles.get(i);
        if (!edible.touchingGround()) {
          edible.update(now, secSinceLast);
        } else {
          aquarium.edibles.remove(edible);
        }
      }
      if (numOfEgg >= 3) {
        running = false;
        // As the game need currentTimeMilis to determine animation and hunger
        // and System.currentTimeMillis() keeps running in the background
        // we need an offset so that the game will be resumed to it's normal state
        // (before paused) after it gots resumed
        offset = (double) System.currentTimeMillis() / 1000;
        win = true;
      }
      if (aquarium.fishes.length() == 0 && aquarium.gold < Guppy.price
          && aquarium.valuables.length() == 0) {
        running = false;
        gameOver = true;
      }
    }
  }

  /**
   * Update the current in-game clock
   */
  private void updateGameTime() {
    now = timeSinceStart() - offset;
    secSinceLast = now - prevTime;
    prevTime = now;
  }

  /**
   * @return time spent since the game started
   */
  private double timeSinceStart() {
    return (double) System.currentTimeMillis() / 1000 - timeStart;
  }

  /**
   * Load resources (sprite) for each object in ArkavQuarium Add the loaded animation into each
   * object
   */
  private void loadResources() {

    // Add small guppy animation
    Guppy.anims.add(new Animation("res/smallswim.png", 80, 0));
    Guppy.anims.add(new Animation("res/smalleat.png", 80, 0));
    Guppy.anims.add(new Animation("res/smallturn.png", 80, 0));
    Guppy.anims.add(new Animation("res/hungryswim.png", 80, 0));
    Guppy.anims.add(new Animation("res/hungryeat.png", 80, 0));
    Guppy.anims.add(new Animation("res/hungryturn.png", 80, 0));
    Guppy.anims.add(new Animation("res/smallswimflip.png", 80, 0));
    Guppy.anims.add(new Animation("res/smalleatflip.png", 80, 0));
    Guppy.anims.add(new Animation("res/smallturnflip.png", 80, 0));
    Guppy.anims.add(new Animation("res/hungryswimflip.png", 80, 0));
    Guppy.anims.add(new Animation("res/hungryeatflip.png", 80, 0));
    Guppy.anims.add(new Animation("res/hungryturnflip.png", 80, 0));

    // Add medium guppy animation
    Guppy.anims.add(new Animation("res/smallswim.png", 80, 1));
    Guppy.anims.add(new Animation("res/smalleat.png", 80, 1));
    Guppy.anims.add(new Animation("res/smallturn.png", 80, 1));
    Guppy.anims.add(new Animation("res/hungryswim.png", 80, 1));
    Guppy.anims.add(new Animation("res/hungryeat.png", 80, 1));
    Guppy.anims.add(new Animation("res/hungryturn.png", 80, 1));
    Guppy.anims.add(new Animation("res/smallswimflip.png", 80, 1));
    Guppy.anims.add(new Animation("res/smalleatflip.png", 80, 1));
    Guppy.anims.add(new Animation("res/smallturnflip.png", 80, 1));
    Guppy.anims.add(new Animation("res/hungryswimflip.png", 80, 1));
    Guppy.anims.add(new Animation("res/hungryeatflip.png", 80, 1));
    Guppy.anims.add(new Animation("res/hungryturnflip.png", 80, 1));

    // Add large guppy animation
    Guppy.anims.add(new Animation("res/smallswim.png", 80, 2));
    Guppy.anims.add(new Animation("res/smalleat.png", 80, 2));
    Guppy.anims.add(new Animation("res/smallturn.png", 80, 2));
    Guppy.anims.add(new Animation("res/hungryswim.png", 80, 2));
    Guppy.anims.add(new Animation("res/hungryeat.png", 80, 2));
    Guppy.anims.add(new Animation("res/hungryturn.png", 80, 2));
    Guppy.anims.add(new Animation("res/smallswimflip.png", 80, 2));
    Guppy.anims.add(new Animation("res/smalleatflip.png", 80, 2));
    Guppy.anims.add(new Animation("res/smallturnflip.png", 80, 2));
    Guppy.anims.add(new Animation("res/hungryswimflip.png", 80, 2));
    Guppy.anims.add(new Animation("res/hungryeatflip.png", 80, 2));
    Guppy.anims.add(new Animation("res/hungryturnflip.png", 80, 2));

    // Add king guppy animation
    Guppy.anims.add(new Animation("res/smallswim.png", 80, 3));
    Guppy.anims.add(new Animation("res/smalleat.png", 80, 3));
    Guppy.anims.add(new Animation("res/smallturn.png", 80, 3));
    Guppy.anims.add(new Animation("res/hungryswim.png", 80, 3));
    Guppy.anims.add(new Animation("res/hungryeat.png", 80, 3));
    Guppy.anims.add(new Animation("res/hungryturn.png", 80, 3));
    Guppy.anims.add(new Animation("res/smallswimflip.png", 80, 3));
    Guppy.anims.add(new Animation("res/smalleatflip.png", 80, 3));
    Guppy.anims.add(new Animation("res/smallturnflip.png", 80, 3));
    Guppy.anims.add(new Animation("res/hungryswimflip.png", 80, 3));
    Guppy.anims.add(new Animation("res/hungryeatflip.png", 80, 3));
    Guppy.anims.add(new Animation("res/hungryturnflip.png", 80, 3));

    // Add guppy die animation
    Guppy.anims.add(new Animation("res/smalldie.png", 80, 0));
    Guppy.anims.add(new Animation("res/smalldieflip.png", 80, 0));
    Guppy.anims.add(new Animation("res/smalldie.png", 80, 1));
    Guppy.anims.add(new Animation("res/smalldieflip.png", 80, 1));
    Guppy.anims.add(new Animation("res/smalldie.png", 80, 2));
    Guppy.anims.add(new Animation("res/smalldieflip.png", 80, 2));
    Guppy.anims.add(new Animation("res/smalldie.png", 80, 3));
    Guppy.anims.add(new Animation("res/smalldieflip.png", 80, 3));

    // Add piranha animation
    Piranha.anims.add(new Animation("res/smallswim.png", 80, 4));
    Piranha.anims.add(new Animation("res/smalleat.png", 80, 4));
    Piranha.anims.add(new Animation("res/smallturn.png", 80, 4));
    Piranha.anims.add(new Animation("res/hungryswim.png", 80, 4));
    Piranha.anims.add(new Animation("res/hungryeat.png", 80, 4));
    Piranha.anims.add(new Animation("res/hungryturn.png", 80, 4));
    Piranha.anims.add(new Animation("res/smallswimflip.png", 80, 4));
    Piranha.anims.add(new Animation("res/smalleatflip.png", 80, 4));
    Piranha.anims.add(new Animation("res/smallturnflip.png", 80, 4));
    Piranha.anims.add(new Animation("res/hungryswimflip.png", 80, 4));
    Piranha.anims.add(new Animation("res/hungryeatflip.png", 80, 4));
    Piranha.anims.add(new Animation("res/hungryturnflip.png", 80, 4));

    // Add piranha dying animation
    Piranha.anims.add(new Animation("res/smalldie.png", 80, 4));
    Piranha.anims.add(new Animation("res/smalldieflip.png", 80, 4));

    Snail.anims.add(new Animation("res/stinky.png", 80, 0));
    Snail.anims.add(new Animation("res/stinky.png", 80, 2));
    Snail.anims.add(new Animation("res/stinky.png", 80, 1));
    Snail.anims.add(new Animation("res/stinkyflip.png", 80, 0));
    Snail.anims.add(new Animation("res/stinkyflip.png", 80, 2));
    Snail.anims.add(new Animation("res/stinkyflip.png", 80, 1));

    Pellet.anims.add(new Animation("res/food.png", 40, 1));

    Coin.anims.add(new Animation("res/money.png", 72, 0));
    Coin.anims.add(new Animation("res/money.png", 72, 1));
    Coin.anims.add(new Animation("res/money.png", 72, 2));
    Coin.anims.add(new Animation("res/money.png", 72, 3));
  }

  private class TAdapter extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {
      if (e.getKeyChar() == 'g' && aquarium.gold >= Guppy.price && running) {
        Guppy guppy = new Guppy(new Position(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2), aquarium, now);
        aquarium.fishes.add(guppy);
      } else if (e.getKeyChar() == 'p' && aquarium.gold >= Piranha.price && running) {
        Piranha piranha = new Piranha(new Position(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2), aquarium,
            now);
        aquarium.fishes.add(piranha);
      } else if (e.getKeyChar() == 'e' && aquarium.gold >= EGG_PRICE && running) {
        numOfEgg++;
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !running && !gameOver) {
        running = true;
        offset = (double) System.currentTimeMillis() / 1000 - offset;
      } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && running) {
        running = false;
        offset = (double) System.currentTimeMillis() / 1000;
      }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
  }

  private class MouseAdapter implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1 && aquarium.gold >= Pellet.price) {
        Edible edible = new Pellet(new Position(e.getX(), e.getY()), aquarium, now);
        aquarium.edibles.add(edible);
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
  }

}
