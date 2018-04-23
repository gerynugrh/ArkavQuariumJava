import java.util.Random;

public class Game {

    private long timeStart;
    public static Random random;
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;

    public Game() {
        timeStart = System.currentTimeMillis();
        System.out.println("Game started");
    }

    private void start() {
        loadResources();

    }

    private void setStaticVariable() {
        // Guppy static variable
        Guppy.foodForUpgrade = 1;
        Guppy.timeForCoin = 5;
        Guppy.speed = 50;
        Guppy.price = 200;

        // Piranha static variable
        Piranha.speed = 60;
        Piranha.price = 500;


    }

    private void loadResources() {

        // Add small guppy animation
        Guppy.anims.add(new Animation("../res/smallswim.png", 80, 0));
        Guppy.anims.add(new Animation("../res/smalleat.png", 80, 0));
        Guppy.anims.add(new Animation("../res/smallturn.png", 80, 0));
        Guppy.anims.add(new Animation("../res/hungryswim.png", 80, 0));
        Guppy.anims.add(new Animation("../res/hungryeat.png", 80, 0));
        Guppy.anims.add(new Animation("../res/hungryturn.png", 80, 0));
        Guppy.anims.add(new Animation("../res/smallswimflip.png", 80, 0));
        Guppy.anims.add(new Animation("../res/smalleatflip.png", 80, 0));
        Guppy.anims.add(new Animation("../res/smallturnflip.png", 80, 0));
        Guppy.anims.add(new Animation("../res/hungryswimflip.png", 80, 0));
        Guppy.anims.add(new Animation("../res/hungryeatflip.png", 80, 0));
        Guppy.anims.add(new Animation("../res/hungryturnflip.png", 80, 0));

        // Add medium guppy animation
        Guppy.anims.add(new Animation("../res/smallswim.png", 80, 1));
        Guppy.anims.add(new Animation("../res/smalleat.png", 80, 1));
        Guppy.anims.add(new Animation("../res/smallturn.png", 80, 1));
        Guppy.anims.add(new Animation("../res/hungryswim.png", 80, 1));
        Guppy.anims.add(new Animation("../res/hungryeat.png", 80, 1));
        Guppy.anims.add(new Animation("../res/hungryturn.png", 80, 1));
        Guppy.anims.add(new Animation("../res/smallswimflip.png", 80, 1));
        Guppy.anims.add(new Animation("../res/smalleatflip.png", 80, 1));
        Guppy.anims.add(new Animation("../res/smallturnflip.png", 80, 1));
        Guppy.anims.add(new Animation("../res/hungryswimflip.png", 80, 1));
        Guppy.anims.add(new Animation("../res/hungryeatflip.png", 80, 1));
        Guppy.anims.add(new Animation("../res/hungryturnflip.png", 80, 1));

        // Add large guppy animation
        Guppy.anims.add(new Animation("../res/smallswim.png", 80, 2));
        Guppy.anims.add(new Animation("../res/smalleat.png", 80, 2));
        Guppy.anims.add(new Animation("../res/smallturn.png", 80, 2));
        Guppy.anims.add(new Animation("../res/hungryswim.png", 80, 2));
        Guppy.anims.add(new Animation("../res/hungryeat.png", 80, 2));
        Guppy.anims.add(new Animation("../res/hungryturn.png", 80, 2));
        Guppy.anims.add(new Animation("../res/smallswimflip.png", 80, 2));
        Guppy.anims.add(new Animation("../res/smalleatflip.png", 80, 2));
        Guppy.anims.add(new Animation("../res/smallturnflip.png", 80, 2));
        Guppy.anims.add(new Animation("../res/hungryswimflip.png", 80, 2));
        Guppy.anims.add(new Animation("../res/hungryeatflip.png", 80, 2));
        Guppy.anims.add(new Animation("../res/hungryturnflip.png", 80, 2));

        // Add king guppy animation
        Guppy.anims.add(new Animation("../res/smallswim.png", 80, 3));
        Guppy.anims.add(new Animation("../res/smalleat.png", 80, 3));
        Guppy.anims.add(new Animation("../res/smallturn.png", 80, 3));
        Guppy.anims.add(new Animation("../res/hungryswim.png", 80, 3));
        Guppy.anims.add(new Animation("../res/hungryeat.png", 80, 3));
        Guppy.anims.add(new Animation("../res/hungryturn.png", 80, 3));
        Guppy.anims.add(new Animation("../res/smallswimflip.png", 80, 3));
        Guppy.anims.add(new Animation("../res/smalleatflip.png", 80, 3));
        Guppy.anims.add(new Animation("../res/smallturnflip.png", 80, 3));
        Guppy.anims.add(new Animation("../res/hungryswimflip.png", 80, 3));
        Guppy.anims.add(new Animation("../res/hungryeatflip.png", 80, 3));
        Guppy.anims.add(new Animation("../res/hungryturnflip.png", 80, 3));

        // Add guppy die animation
        Guppy.anims.add(new Animation("../res/smalldie.png", 80, 0));
        Guppy.anims.add(new Animation("../res/smalldieflip.png", 80, 0));
        Guppy.anims.add(new Animation("../res/smalldie.png", 80, 1));
        Guppy.anims.add(new Animation("../res/smalldieflip.png", 80, 1));
        Guppy.anims.add(new Animation("../res/smalldie.png", 80, 2));
        Guppy.anims.add(new Animation("../res/smalldieflip.png", 80, 2));
        Guppy.anims.add(new Animation("../res/smalldie.png", 80, 3));
        Guppy.anims.add(new Animation("../res/smalldieflip.png", 80, 3));

        // Add piranha animation
        Piranha.anims.add(new Animation("../res/smallswim.png", 80, 4));
        Piranha.anims.add(new Animation("../res/smalleat.png", 80, 4));
        Piranha.anims.add(new Animation("../res/smallturn.png", 80, 4));
        Piranha.anims.add(new Animation("../res/hungryswim.png", 80, 4));
        Piranha.anims.add(new Animation("../res/hungryeat.png", 80, 4));
        Piranha.anims.add(new Animation("../res/hungryturn.png", 80, 4));
        Piranha.anims.add(new Animation("../res/smallswimflip.png", 80, 4));
        Piranha.anims.add(new Animation("../res/smalleatflip.png", 80, 4));
        Piranha.anims.add(new Animation("../res/smallturnflip.png", 80, 4));
        Piranha.anims.add(new Animation("../res/hungryswimflip.png", 80, 4));
        Piranha.anims.add(new Animation("../res/hungryeatflip.png", 80, 4));
        Piranha.anims.add(new Animation("../res/hungryturnflip.png", 80, 4));

        // Add piranha dying animation
        Piranha.anims.add(new Animation("../res/smalldie.png", 80, 4));
        Piranha.anims.add(new Animation("../res/smalldieflip.png", 80, 4));

        Animation animSnailMove = new Animation("../res/stinky.png", 80, 0);
        Animation animSnailIdle = new Animation("../res/stinky.png", 80, 2);
        Animation animSnailTurn = new Animation("../res/stinky.png", 80, 1);
        Animation animSnailFlipMove = new Animation("../res/stinkyflip.png", 80, 0);
        Animation animSnailFlipIdle = new Animation("../res/stinkyflip.png", 80, 2);
        Animation animSnailFlipTurn = new Animation("../res/stinkyflip.png", 80, 1);

        Animation animFoodMove = new Animation("../res/food.png", 40, 1);

        Animation animCoinMove = new Animation("../res/money.png", 72, 0);
        Animation animGoldCoinMove = new Animation("../res/money.png", 72, 1);
        Animation animStarCoinMove = new Animation("../res/money.png", 72, 2);
        Animation animDiamondCoinMove = new Animation("../res/money.png", 72, 3);
    }

}
