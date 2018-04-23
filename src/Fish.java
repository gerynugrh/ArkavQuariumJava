import java.awt.image.BufferedImage;

public abstract class Fish extends AquariumObject {

    protected enum Type {GUPPY, PIRANHA};
    protected final Type type;
    protected boolean alive, hungry, destroyed;
    protected int stage;
    protected long timeHungry, timeEat, timeStamp;
    protected static long timeUntilHungry, timeUntilDead;

    public Fish(Type type, Position pos, Aquarium aquarium, double now) {
        super(pos, aquarium, now);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public Position getPosition() {
        return position;
    }

    public abstract int getStage();
    public abstract void update(double now, double secSinceLast);
    public abstract BufferedImage getFrame();
    protected abstract void move(double secSinceLast);

}
