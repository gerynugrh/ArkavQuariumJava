import static java.lang.Math.sqrt;

public class Position {

    public int x, y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceFrom(Position pos) {
        return sqrt((x - pos.x) * (x - pos.x) + (y - pos.y) * (y - pos.y));
    }

}
