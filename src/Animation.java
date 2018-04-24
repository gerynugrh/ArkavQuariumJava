import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Animation {

  private BufferedImage[] frames;

  /**
   * Create a list of buffered image symbolizing each sprite
   *
   * @param path Path to the sprite group
   * @param size Size of each sprite
   * @param index index of row to be pushed into animation
   */
  Animation(String path, int size, int index) {
    try {
      BufferedImage sprite = ImageIO.read(new File(path));
      frames = new BufferedImage[sprite.getWidth() / size];
      for (int i = 0; i < sprite.getWidth() / size; i++) {
        BufferedImage frame = sprite.getSubimage(i * size, index * size, size, size);
        frames[i] = frame;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param index element in the list
   * @return Sprite in the animation in the form BufferedImage
   */
  BufferedImage getFrame(int index) {
    return frames[index];
  }
}
