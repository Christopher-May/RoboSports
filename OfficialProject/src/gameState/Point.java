package gameState;

/**
 * Class used for Pixel location
 * 
 */
public class Point {
  /**
   * x represent coordinate of the pixel
   */
  double x;
  /**
   * y represent coordinate of the pixel
   */
  double y;

  /**
   * 
   * @param x x coordinate
   * @param y y coordinate
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * 
   * @return x coordinate
   */
  public double getX() {
    return x;
  }

  /**
   * 
   * @return y coordinate
   */
  public double getY() {
    return y;
  }

  @Override
  /**
   * Used for testing purposes
   */
  public String toString() {
    return x + ":" + y;
  }
}
