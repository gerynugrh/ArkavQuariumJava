public interface Collector {

  Position getPosition();

  /**
   * Define the action that Collector do every frame update
   *
   * @param now time now
   * @param secSinceLast time since last update
   */
  void update(double now, double secSinceLast);

}
