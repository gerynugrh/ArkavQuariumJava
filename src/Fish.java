import java.awt.image.BufferedImage;

public abstract class Fish extends AquariumObject {

  static double timeUntilHungry, timeUntilDead;


  private final Type type;
  boolean hungry;
  int stage;
  double timeStamp;
  boolean right;
  private boolean alive, destroyed;
  private final int speed;
  private double timeEat, timeHungry;

  Fish(Type type, Position pos, Aquarium aquarium, double now, int speed) {
    super(pos, aquarium, now);
    this.type = type;
    this.speed = speed;
    stage = 0;
    right = false;
    timeStamp = (long) now;
    timeEat = (long) now;
    alive = true;
  }

  Type getType() {
    return type;
  }

  boolean getDestroyed() {
    return destroyed;
  }

  Position getPosition() {
    return position;
  }

  protected abstract int getStage();

  protected abstract boolean eat(double now);

  public abstract BufferedImage getFrame();

  protected abstract Object findNearestFood();

  private void move(double secSinceLast) {
    if (moveDuration < 0) {
      moveDuration = Game.random.nextDouble() * 4 + 1;
      direction = Game.random.nextDouble() * 360;
    } else {
      moveDuration -= secSinceLast;
    }
    if (hungry && findNearestFood() != null) {
      AquariumObject nearestFood = (AquariumObject) findNearestFood();
      double deltaX, deltaY;
      deltaX = nearestFood.position.x - position.x;
      deltaY = nearestFood.position.y - position.y;

      direction = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
    } else {
      double newX, newY;
      newX = position.x + Math.cos(direction * Math.PI / 180.0) * secSinceLast * speed;
      newY = position.y + Math.cos(direction * Math.PI / 180.0) * secSinceLast * speed;

      if (newX >= Game.SCREEN_WIDTH - 60) {
        direction = 90 + Game.random.nextDouble() * 180;
      } else if (newX <= 60) {
        direction = 270 + Game.random.nextDouble() * 180;
      } else if (newY <= 60) {
        direction = 0 + Game.random.nextDouble() * 180;
      } else if (newY >= Game.SCREEN_HEIGHT - 60) {
        direction = 180 + Game.random.nextDouble() * 180;
      }
    }
    position.x += Math.cos(direction * Math.PI / 180.0) * secSinceLast * speed;
    position.y += Math.sin(direction * Math.PI / 180.0) * secSinceLast * speed;
  }

  public void update(double now, double secSinceLast) {
    if (alive) {
      if (animMode % 3 == 1 && animFrame == 9) {
        animMode = 3 * (hungry ? 1 : 0) + 6 * (right ? 1 : 0) + 12 * stage;
        animFrame = 0;
        timeStamp = now;
      } else if (right && (animMode % 6) % 3 == 2 && animFrame == 9) {
        animMode = 3 * (hungry ? 1 : 0) + 6 + 12 * stage;
        animFrame = 0;
        timeStamp = now;
      } else if (!right && (animMode % 6) % 3 == 2) {
        animFrame = 9 - (int) ((now - timeStamp) * 50 % 60) / 6;

        if (animFrame == 0) {
          animMode = 3 * (hungry ? 1 : 0) + 12 * stage;
          animFrame = 0;
          timeStamp = now;
        }
      } else {
        animFrame = (int) ((now - timeStamp) * 50 % 60) / 6;
      }
      move(secSinceLast);
      if (eat(now)) {
        timeEat = now;
        timeHungry = timeEat + timeUntilHungry;
        hungry = false;
        animMode = animMode % 3 + 6 * (right ? 1 : 0) + 12 * stage;
      }
      if (hungry && now - timeHungry >= timeUntilDead) {
        alive = false;
        animMode = (type == Type.GUPPY ? 48 : 12) + 2 * stage + (right ? 1 : 0);
        animFrame = 0;
        timeStamp = now;
      } else if (!hungry && now - timeEat >= timeUntilHungry) {
        timeHungry = now;
        hungry = true;
        animMode = animMode % 3 + 3 + 6 * (right ? 1 : 0) + 12 * stage;
      }
      if (!right && (direction <= 90 || direction >= 270)) {
        animMode = 2 + 3 * (hungry ? 1 : 0) + 12 * stage;
        timeStamp = now;
        right = true;
      } else if (right && (direction >= 90 && direction <= 270)) {
        animMode = 2 + 3 * (hungry ? 1 : 0) + 6 + 12 * stage;
        animFrame = 9;
        timeStamp = now;
        right = false;
      }
    } else {
      if (animFrame == 9) {
        destroyed = true;
      } else {
        position.y += secSinceLast * 30;
        animFrame = (int) ((now - timeStamp) * 50 % 60) / 6;
      }
    }
  }

  protected enum Type {GUPPY, PIRANHA}

}
