import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Coin extends AquariumObject implements Valuable {

  static ArrayList<Animation> anims = new ArrayList<>();
  static int speed;
  private int value;
  private boolean touchedGround;

  Coin(Position pos, Aquarium aquarium, double now, int value) {
    super(pos, aquarium, now);
    this.value = value;
    int type;
    if (value <= 100) {
      type = 0;
    } else if (value <= 200) {
      type = 1;
    } else if (value <= 400) {
      type = 2;
    } else {
      type = 3;
    }
    animFrame = 0;
    animMode = type;
    timeSpawned = now;
    touchedGround = false;
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
    // Keep playing animation until the frame when coin touch the ground is at the default state
    if (!touchedGround || !(animFrame == 0)) {
      animFrame = (int) ((now - timeSpawned) * 50 % 60) / 6;
      move(secSinceLast);
    }
  }

  private void move(double secSinceLast) {
    double newY = position.y + secSinceLast * speed;
    if (newY <= Game.SCREEN_HEIGHT - 50) {
      position.y = newY;
    } else {
      touchedGround = true;
    }
  }

  @Override
  public void removeCoin() {
    aquarium.gold += value;
  }
}
