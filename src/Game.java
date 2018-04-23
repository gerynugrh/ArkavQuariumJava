import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {

    public static Random random = new Random(2);
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;
    public int framesPassed;
    public double fpcStart, cy, cx, prevTime, now, secSinceLast, timeStart;
    public boolean running, quit = false;
    public Aquarium aquarium;
    private final int DELAY = 10;
    private Timer timer;
    private BufferedImage background;

    public Game() {
        timeStart = (double) System.currentTimeMillis() / 1000;
        System.out.println("Game started");
        start();
    }

    private void start() {
        aquarium = new Aquarium();
        loadResources();
        setStaticVariable();
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        try {
            background = ImageIO.read(new File("res/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void setStaticVariable() {
        Game.SCREEN_HEIGHT = 480;
        Game.SCREEN_WIDTH = 640;

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

        running = true;
        prevTime = timeSinceStart();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(background, 0, 0, this);

        for (int i = 0; i < aquarium.fishes.length(); i++) {
            Fish fish = aquarium.fishes.get(i);
            int x = (int) fish.getPosition().x - fish.getFrame().getWidth() / 2;
            int y = (int) fish.getPosition().y - fish.getFrame().getHeight() / 2;
            g2d.drawImage(fish.getFrame(), x, y, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGameTime();
        repaint(new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT));
        for (int i = 0; i < aquarium.fishes.length(); i++) {
            Fish fish = aquarium.fishes.get(i);
            if (!fish.getDestroyed())
                aquarium.fishes.get(i).update(now, secSinceLast);
            else
                aquarium.fishes.remove(fish);
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'g') {
                Guppy guppy = new Guppy(new Position(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2), aquarium, now);
                aquarium.fishes.add(guppy);
            }
            else if (e.getKeyChar() == 'p') {
                Piranha piranha = new Piranha(new Position(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2), aquarium, now);
                aquarium.fishes.add(piranha);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
    }



    private void updateGameTime() {
        now = timeSinceStart();
        secSinceLast = now - prevTime;
        prevTime = now;
    }

    private double timeSinceStart() {
        return (double) System.currentTimeMillis() / 1000 - timeStart;
    }

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

        Animation animSnailMove = new Animation("res/stinky.png", 80, 0);
        Animation animSnailIdle = new Animation("res/stinky.png", 80, 2);
        Animation animSnailTurn = new Animation("res/stinky.png", 80, 1);
        Animation animSnailFlipMove = new Animation("res/stinkyflip.png", 80, 0);
        Animation animSnailFlipIdle = new Animation("res/stinkyflip.png", 80, 2);
        Animation animSnailFlipTurn = new Animation("res/stinkyflip.png", 80, 1);

        Animation animFoodMove = new Animation("res/food.png", 40, 1);

        Animation animCoinMove = new Animation("res/money.png", 72, 0);
        Animation animGoldCoinMove = new Animation("res/money.png", 72, 1);
        Animation animStarCoinMove = new Animation("res/money.png", 72, 2);
        Animation animDiamondCoinMove = new Animation("res/money.png", 72, 3);
    }

}
