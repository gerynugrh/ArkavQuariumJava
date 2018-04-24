import static java.lang.Math.abs;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Snail extends AquariumObject implements Collector {

  static ArrayList<Animation> anims = new ArrayList<>();
  static int speed;
  private boolean idle, right;

  Snail(Position pos, Aquarium aquarium, double now) {
    super(pos, aquarium, now);
    idle = false;
    right = false;
  }

  @Override
  public BufferedImage getFrame() {
    return anims.get(animMode).getFrame(animFrame);
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public void update(double now, double secSinceLast) {
    // Unless the snail is at idle state, keep playing animation
    if (right && animMode % 3 == 2) {
      animMode = 3;
      animFrame = 0;
      timeSpawned = now;
    } else if (!right && animMode % 3 == 2) {
      animFrame = 9 - (int) ((now - timeSpawned) * 50 % 60) / 6;
      if (animFrame == 0) {
        animMode = 0;
        animFrame = 0;
        timeSpawned = now;
      }
    } else if (!(animMode % 3 == 1 && animFrame == 9)) {
      animFrame = (int) ((now - timeSpawned) * 50 % 60) / 6;
    }
    if (!idle && findNearestCoin() == null) {
      direction = 0;
      animMode = 1 + 3 * (right ? 1 : 0);
      animFrame = 0;
      idle = true;
      timeSpawned = now;
    } else if (findNearestCoin() != null) {
      if (idle) {
        idle = false;
        animMode = 3 * (right ? 1 : 0);
        animFrame = 0;
        timeSpawned = now;
      }
      move(secSinceLast);
      takeCoin();
    }
    if (direction == 1 && !right) {
      animMode = 2;
      timeSpawned = now;
      right = true;
    } else if (direction == -1 && right) {
      animMode = 2 + 3;
      animFrame = 9;
      timeSpawned = now;
      right = false;
    }
  }

  private void move(double secSinceLast) {
    Valuable nearestValuable = findNearestCoin();
    if (nearestValuable.getPosition().x < position.x
        && position.x - nearestValuable.getPosition().x >= 20) {
      direction = -1;
    } else if (nearestValuable.getPosition().x > position.x
        && nearestValuable.getPosition().x - position.x >= 20) {
      direction = 1;
    } else if (abs(nearestValuable.getPosition().x - position.x) <= 10) {
      animFrame = 0;
      direction = 0;
    }
    position.x += direction * speed * secSinceLast;
  }

  private Valuable findNearestCoin() {
    Valuable nearestValuable = null;
    double minDistance = 9999;
    for (int i = 0; i < aquarium.valuables.length(); i++) {
      Valuable valuable = aquarium.valuables.get(i);
      if (position.distanceFrom(valuable.getPosition()) < minDistance) {
        minDistance = position.distanceFrom(valuable.getPosition());
        nearestValuable = valuable;
      }
    }
    return nearestValuable;
  }

  private void takeCoin() {
    Valuable valuable = findNearestCoin();
    if (position.distanceFrom(valuable.getPosition()) <= 20) {
      valuable.removeCoin();
      aquarium.valuables.remove(valuable);
    }
  }

}
