import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Guppy extends Fish {

    public static int foodForUpgrade, timeForCoin, speed, price;
    public double timeSinceLastCoin;
    public static ArrayList<Animation> anims = new ArrayList<>();
    private int amountOfFood;
    private boolean right;

    public Guppy (Position pos, Aquarium aquarium, double now) {
        super(Fish.Type.GUPPY, pos, aquarium, now);
        super.timeStamp = (long) now;
        super.animFrame = 0;
        super.animMode = 0;
        super.timeEat = (long) now;
        amountOfFood = 0;
        aquarium.gold -= Guppy.price;
        stage = 0;
        timeSinceLastCoin = now;
        hungry = false;
        right = false;
    }

    @Override
    public int getStage() {
        return stage;
    }

    @Override
    public void update(double now, double secSinceLast) {

    }

    @Override
    public BufferedImage getFrame() {
        return anims.get(animMode).getFrame(animFrame);
    }

    @Override
    protected void move(double secSinceLast) {
        if (moveDuration < 0) {
            moveDuration = Game.random.nextDouble() * 4 + 1;
            direction = Game.random.nextDouble() * 360;
        }
        else {
            moveDuration -= secSinceLast;
        }
        if (hungry && findNearestFood() != null) {
            Food nearestFood = findNearestFood();
            int deltaX, deltaY;
            deltaX = nearestFood.getPosition().x - position.x;
            deltaY = nearestFood.getPosition().y - position.y;

            direction = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
        }
        else {
            double newX, newY;
            newX = position.x + Math.cos(direction * Math.PI / 180.0) * secSinceLast * speed;
            newY = position.y + Math.cos(direction * Math.PI / 180.0) * secSinceLast * speed;

            if (newX >= Game.SCREEN_WIDTH - 40) {
                direction = 90 + Game.random.nextDouble() * 180;
            }
            else if (newX <= 40) {
                direction = 270 + Game.random.nextDouble() * 180;
            }
            else if (newY <= 40) {
                direction = 0 + Game.random.nextDouble() * 180;
            }
            else if (newY >= Game.SCREEN_HEIGHT - 40) {
                direction = 180 + Game.random.nextDouble() * 180;
            }
        }
        position.x += Math.cos(direction * Math.PI / 180.0) * secSinceLast * speed;
        position.y += Math.sin(direction * Math.PI / 180.0) * secSinceLast * speed;
    }

    Food findNearestFood() {
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
}
