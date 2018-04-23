import static java.lang.Math.sqrt;

public class Position {

    double x, y;

    public Position() {
        x = 0;
        y = 0;
    }

    Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double distanceFrom(Position pos) {
        return sqrt((x - pos.x) * (x - pos.x) + (y - pos.y) * (y - pos.y));
    }

}
