public class AquariumObject {

    protected final Aquarium aquarium;
    protected Position position;
    protected double timeSpawned, moveDuration, direction;
    protected int animMode, animFrame;

    public AquariumObject(Position pos, Aquarium aquarium, double now) {
        this.position = pos;
        this.aquarium = aquarium;
        timeSpawned = now;
    }

}
