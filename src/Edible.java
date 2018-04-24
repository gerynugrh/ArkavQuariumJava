public interface Edible {

  Position getPosition();

  /**
   * Define the action that Collector do every frame update
   *
   * @param now time now
   * @param secSinceLast time since last called
   */
  void update(double now, double secSinceLast);

  boolean touchingGround();

}
