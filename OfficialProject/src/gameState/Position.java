package gameState;

/**
 * 
 * Axial representation of a Hex
 *
 */
public class Position {

  /** Row of the hex with row number x */
  private int x;
  /** Column of the hex with column number y */
  private int z;

  public Position(int row, int col) {
    this.x = row;
    this.z = col;
  }

  /**
   * Returns the X coordinate from the current Position
   * 
   * @return The current X coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Returns the X coordinate from the current Position
   * 
   * @return The current X coordinate
   */
  public int getZ() {
    return z;
  }

  /**
   * Return the tile distance between this and other as an integer.
   * 
   * @param other The other Position for which the tile distance is desired.
   * 
   * @return The tile distance between this Position and other.
   */
  public int getTileDistance(Position other) {
    return this.getCubicalPosition().distance(other.getCubicalPosition());
  }

  /**
   * @return cubical representation of Position
   */
  public CubicalPosition getCubicalPosition() {
    return new CubicalPosition(x, (-x - z), z);
  }

  @Override
  public String toString() {
    return x + ":" + z;
  }

  /**
   * Checks if an object is equal to the current position
   * 
   * @param o The object that is being compared to the current position
   * @return returns true if o is equal to the current position false if otherwise
   */
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Position)) {
      return false;
    }

    Position p = (Position) o;

    if (p.getX() == this.getX() && p.getZ() == this.getZ()) {
      return true;
    }
    return false;
  }
}
