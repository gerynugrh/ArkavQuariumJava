import java.awt.image.BufferedImage;

abstract class AquariumObject {

    final Aquarium aquarium;
    Position position;
    double moveDuration, direction, timeSpawned;
    int animMode, animFrame;

    AquariumObject(Position pos, Aquarium aquarium, double now) {
        this.position = pos;
        this.aquarium = aquarium;
        timeSpawned = now;
        animMode = 0;
        animFrame = 0;
    }

    public abstract BufferedImage getFrame();

}
