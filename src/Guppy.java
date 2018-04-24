import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Guppy extends Fish {

  static int foodForUpgrade, timeForCoin, speed, price;
  static ArrayList<Animation> anims = new ArrayList<>();
  private double timeSinceLastCoin;
  private int amountOfFood;

  Guppy(Position pos, Aquarium aquarium, double now) {
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
    Edible nearestEdible = null;
    double minDistance = Double.MAX_VALUE;
    for (int i = 0; i < aquarium.edibles.length(); i++) {
      Edible edible = aquarium.edibles.get(i);
      if (position.distanceFrom(edible.getPosition()) < minDistance) {
        minDistance = position.distanceFrom(edible.getPosition());
        nearestEdible = edible;
      }
    }
    return nearestEdible;
  }

  private void produceCoin(double now) {
    Coin coin = new Coin(new Position(position.x, position.y), aquarium, now, 40 * (stage + 1));
    aquarium.valuables.add(coin);
  }

  private void upgrade() {
    if (amountOfFood >= Guppy.foodForUpgrade && stage < 3) {
      amountOfFood = 0;
      stage++;
    }
  }

  @Override
  protected boolean eat(double now) {
    Edible edible = (Edible) findNearestFood();
    if (hungry && edible != null && position.distanceFrom(edible.getPosition()) <= 20) {
      aquarium.edibles.remove(edible);
      amountOfFood++;
      return true;
    } else if (hungry && edible != null && position.distanceFrom(edible.getPosition()) <= 60) {
      animMode = 1 + 3 * (hungry ? 1 : 0) + 6 * (right ? 1 : 0) + 12 * stage;
      timeStamp = now;
    }
    return false;
  }

}
