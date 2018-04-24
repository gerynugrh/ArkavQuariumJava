public interface Valuable {

  Position getPosition();

  void update(double now, double secSinceLast);

  void removeCoin();

}
