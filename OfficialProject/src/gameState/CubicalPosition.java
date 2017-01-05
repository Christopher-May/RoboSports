package gameState;

import java.util.ArrayList;

/**
 * Will be using to find the range, view. analyse the robot shoot, move numbers
 */
public class CubicalPosition {
  /** cubical x representation of hex */
  private int x;
  /** cubical y representation of hex */
  private int y;
  /** cubical z representation of hex */
  private int z;
  /**
   * directions store the 6 directions around the hex
   */
  public static final int HEXSIZE = 6;
  /**
   * 6 directions around the hex, 0 index, contain, the center left direction
   */
  private final static ArrayList<CubicalPosition> directions = new ArrayList<CubicalPosition>() {
    private static final long serialVersionUID = 1L;

    {
      add(new CubicalPosition(1, 0, -1));
      add(new CubicalPosition(1, -1, 0));
      add(new CubicalPosition(0, -1, 1));
      add(new CubicalPosition(-1, 0, 1));
      add(new CubicalPosition(-1, 1, 0));
      add(new CubicalPosition(0, 1, -1));
    }
  };

  /**
   * Sets the cubical position of an hex given the x, y and z coordinates
   * 
   * @param x cubical x representation of an hex
   * @param y cubical y representation of an hex
   * @param z cubical z representation of an hex
   */
  public CubicalPosition(int x, int y, int z) {

    if (x + y + z != 0)
      throw new IllegalCubicalPosition("x+y+z has to be zero");

    this.x = x;
    this.y = y;
    this.z = z;

  }

  /**
   * 
   * returns a direction from current facing direction
   * 
   * @param directionFacing current direction of the robot
   * @param direction relative to current position
   * @return CubicalPosition of the desired direction
   */
  public static CubicalPosition direction(int directionFacing, int direction) {

    return CubicalPosition.directions.get((directionFacing + direction) % HEXSIZE);
  }

  /**
   * accessor method for direction
   * 
   * @param direction desired
   * @return CubicalPosition of the desired direction
   */
  public static CubicalPosition direction(int direction) {

    return CubicalPosition.directions.get(direction);
  }

  /***
   * @return x position of the Hex
   */
  public int getX() {
    return x;
  }

  /**
   * 
   * @return y position of the Hex
   */
  public int getY() {
    return y;
  }

  /**
   * 
   * @return z position of the Hex
   */
  public int getZ() {
    return z;
  }

  /**
   * @return Position representation of the current Cubical Position
   */
  public Position getPosition() {
    return new Position(x, z);
  }

  /**
   * Adds the current CubicalPosition with a given CubicalPosition
   * 
   * @param cub: the given CubicalPosition
   * @return sum of two positions
   */
  public CubicalPosition addCubicalPosition(CubicalPosition cub) {
    return new CubicalPosition(this.getX() + cub.getX(), this.getY() + cub.getY(),
        this.getZ() + cub.getZ());
  }

  /**
   * Subtracts the current CubicalPosition with a given CubicalPosition
   * 
   * @param cub: the given CubicalPosition
   * @return difference of the current position - given position
   */
  public CubicalPosition subtractCubicalPosition(CubicalPosition cub) {
    return new CubicalPosition(this.getX() - cub.getX(), this.getY() - cub.getY(),
        this.getZ() - cub.getZ());
  }

  /**
   * Scales the Cubical Position by k
   * 
   * @param k : Scale number for CubicalPosition
   * @return the scaled cubical position
   */
  public CubicalPosition scaleCubicalPosition(int k) {
    return new CubicalPosition(k * this.getX(), k * this.getY(), k * this.getZ());
  }

  /**
   * returns the neighbor in a wanted direction
   * 
   * @param dir : which neighbor wanted?
   * @return desired neighbor
   */
  public CubicalPosition neighbourInDirection(int dir) {
    return this.addCubicalPosition(CubicalPosition.direction(dir));
  }


  /**
   * length from origin from the current hex
   * 
   * @return the length from the (0,0,0)
   */
  public int length() {
    return (int) ((Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z)) / 2);
  }

  /**
   * calculates the distance between the current hex and a given hex
   * 
   * @param b another hex
   * @return distance between two hexes
   */
  public int distance(CubicalPosition b) {
    return this.subtractCubicalPosition(b).length();
  }

  @Override
  public String toString() {
    return x + ":" + y + ":" + z;
  }

  @Override
  public boolean equals(Object e) {
    if (this == e)
      return true;

    if (!(e instanceof CubicalPosition))
      return false;

    CubicalPosition p = (CubicalPosition) e;

    return p.getX() == this.getX() && p.getY() == this.getY() && p.getZ() == this.getZ();
  }

}
