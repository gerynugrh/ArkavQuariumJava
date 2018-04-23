import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Game extends JPanel implements ActionListener {

    static Random random = new Random(2);
    static int SCREEN_WIDTH, SCREEN_HEIGHT;
    private double prevTime;
    private double now;
    private double secSinceLast;
    private double timeStart;
    private Aquarium aquarium;
    private BufferedImage background;

    Game() {
        timeStart = (double) System.currentTimeMillis() / 1000;
        System.out.println("Game started");
        start();
    }

    private void start() {
        aquarium = new Aquarium();
        loadResources();
        setStaticVariable();
        addKeyListener(new TAdapter());
        addMouseListener(new MouseAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        try {
            background = ImageIO.read(new File("res/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int DELAY = 10;
        Timer timer = new Timer(DELAY, this);
        timer.start();

        aquarium.collectors.add(new Snail(new Position(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 60), aquarium, now));
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

        Pellet.speed = 30;
        Pellet.price = 20;

        Snail.speed = 30;

        Coin.speed = 30;

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
    }

    private void drawObject(Graphics2D g2d, AquariumObject object) {
        int x = (int) object.position.x - object.getFrame().getWidth() / 2;
        int y = (int) object.position.y - object.getFrame().getHeight() / 2;

        g2d.drawImage(object.getFrame(), x, y, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGameTime();
        repaint(new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT));
        for (int i = 0; i < aquarium.fishes.length(); i++) {
            Fish fish = aquarium.fishes.get(i);
            if (!fish.getDestroyed())
                fish.update(now, secSinceLast);
            else
                aquarium.fishes.remove(fish);
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
            if (!edible.touchingGround())
                edible.update(now, secSinceLast);
            else
                aquarium.edibles.remove(edible);
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
            if (e.getKeyChar() == 'g') {
                Guppy guppy = new Guppy(new Position(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2), aquarium, now);
                aquarium.fishes.add(guppy);
            } else if (e.getKeyChar() == 'p') {
                Piranha piranha = new Piranha(new Position(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2), aquarium, now);
                aquarium.fishes.add(piranha);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
    }

    private class MouseAdapter implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
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
