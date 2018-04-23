import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Guppy extends Fish {

    static int foodForUpgrade, timeForCoin, speed, price;
    private double timeSinceLastCoin;
    static ArrayList<Animation> anims = new ArrayList<>();
    private int amountOfFood;

    public Guppy (Position pos, Aquarium aquarium, double now) {
        super(Fish.Type.GUPPY, pos, aquarium, now, Guppy.speed);
        direction = Game.random.nextDouble() * 360;
        amountOfFood = 0;
        aquarium.gold -= Guppy.price;
        stage = 0;
        timeSinceLastCoin = now;
        hungry = false;
    }

    @Override
    public int getStage() {
        return stage;
    }

    @Override
    public void update(double now, double secSinceLast) {
        super.update(now, secSinceLast);
        if (now - timeSinceLastCoin >= timeForCoin) {
            produceCoin(now);
            timeSinceLastCoin = now;
        }
        upgrade();
    }

    @Override
    public BufferedImage getFrame() {
        return anims.get(animMode).getFrame(animFrame);
    }

    protected Object findNearestFood() {
        Food nearestFood = null;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < aquarium.foods.length(); i++) {
            Food food = aquarium.foods.get(i);
            if (position.distanceFrom(food.getPosition()) < minDistance) {
                minDistance = position.distanceFrom(food.getPosition());
                nearestFood = food;
            }
        }
        return nearestFood;
    }

    private void produceCoin(double now) {
        // TODO Construct coin and add into aquarium
    }

    private void upgrade() {
        if (amountOfFood >= Guppy.foodForUpgrade && stage < 3) {
            amountOfFood = 0;
            stage++;
        }
    }

    @Override
    protected boolean eat(double now) {
        Food food = (Food) findNearestFood();
        if (hungry && food != null && position.distanceFrom(food.getPosition()) <= 20) {
            aquarium.foods.remove(food);
            amountOfFood++;
            return true;
        } else if (hungry && food != null && position.distanceFrom(food.getPosition()) <= 60) {
            animMode = 1 + 3 * (hungry ? 1 : 0) + 6 * (right ? 1 : 0) + 12 * stage;
            timeStamp = now;
        }
        return false;
    }

}
