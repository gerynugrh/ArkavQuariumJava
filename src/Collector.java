public interface Collector {

    Position getPosition();

    void update(double now, double secSinceLast);

}
