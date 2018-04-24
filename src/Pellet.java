import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Pellet extends AquariumObject implements Edible {

  static ArrayList<Animation> anims = new ArrayList<>();
  static int speed, price;
  private boolean touchedGround;

  Pellet(Position pos, Aquarium aquarium, double now) {
    super(pos, aquarium, now);
    touchedGround = false;
    aquarium.gold -= price;
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
    animFrame = (int) ((now - timeSpawned) * 50 % 60) / 6;
    move(secSinceLast);
  }

  @Override
  public boolean touchingGround() {
    return touchedGround;
  }

  private void move(double secSinceLast) {
    double newY = position.y + secSinceLast * speed;
    if (newY <= Game.SCREEN_HEIGHT - 10) {
      position.y = newY;
    } else {
      touchedGround = true;
    }
  }
}
