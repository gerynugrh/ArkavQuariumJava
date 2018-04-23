public interface Edible {

    Position getPosition();

    void update(double now, double secSinceLast);

    boolean touchingGround();

}
