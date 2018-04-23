import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Piranha extends Fish {

    static int speed, price;
    static ArrayList<Animation> anims = new ArrayList<>();

    Piranha(Position pos, Aquarium aquarium, double now) {
        super(Type.PIRANHA, pos, aquarium, now, Piranha.speed);
        direction = Game.random.nextDouble() * 360;
        aquarium.gold -= Guppy.price;
        stage = 0;
        hungry = false;
    }

    private void produceCoin(double now, int value) {
        Coin coin = new Coin(new Position(position.x, position.y), aquarium, now, value);
        aquarium.valuables.add(coin);
    }

    @Override
    public int getStage() {
        return 0;
    }

    @Override
    protected boolean eat(double now) {
        Fish fish = (Fish) findNearestFood();
        if (hungry && fish != null && position.distanceFrom(fish.getPosition()) <= 20) {
            produceCoin(now, Guppy.price * (fish.getStage() + 1));
            aquarium.fishes.remove(fish);
            return true;
        } else if (hungry && fish != null && position.distanceFrom(fish.getPosition()) <= 60) {
            animMode = 1 + 3 * (hungry ? 1 : 0) + 6 * (right ? 1 : 0);
            timeStamp = now;
        }
        return false;
    }

    @Override
    public BufferedImage getFrame() {
        return anims.get(animMode).getFrame(animFrame);
    }

    @Override
    protected Object findNearestFood() {
        Fish nearestFood = null;
        double minDistance = 9999;
        for (int i = 0; i < aquarium.fishes.length(); i++) {
            Fish food = aquarium.fishes.get(i);
            if (aquarium.fishes.get(i).getType() == Fish.Type.GUPPY &&
                    position.distanceFrom(food.getPosition()) < minDistance) {
                minDistance = position.distanceFrom(food.getPosition());
                nearestFood = food;
            }
        }
        return nearestFood;
    }
}
