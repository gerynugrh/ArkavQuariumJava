import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Animation {

    private BufferedImage[] frames;

    public Animation(String path, int size, int index) {
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

    public BufferedImage getFrame(int index) {
        return frames[index];
    }
}
