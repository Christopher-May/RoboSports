package gameState;

import java.util.ArrayList;

import units.Unit;
/**
 * A hex on the game board
 */
public class GameBoardHex {
  /**
   * List of units on hex
   */
  private ArrayList<Unit> myUnits;
  /**
   * position of the hex
   */
  private Position myPosition;
  /**
   * boolean to check if hex is highlighted
   */
  private Boolean highlighted;

  /**
   * test variable text
   */
  public String text;

  public GameBoardHex(Position p) {
    highlighted = false;
    myPosition = p;
    myUnits = new ArrayList<Unit>();
  }

  /**
   * change the highlight
   */
  public void changeHiglight() {
    highlighted = !highlighted;
  }

  /**
   * highlight the hex
   */
  public void highlight() {
    highlighted = true;
  }

  /**
   * unhighlight the hex
   */
  public void unhighlight() {
    highlighted = false;
  }

  /**
   * 
   * @param size of hex
   * @return the pixel position of the hex
   */
  public Point getPoint(int size) {
    double x = size * Math.sqrt(3) * (myPosition.getX() + 0.5 * (myPosition.getZ()));
    double y = size * 3 / 2 * myPosition.getZ();
    return new Point(x, y);
  }

  /** Get a list of all the units which are on this tile.
   * @return The Units on this tile
   */
  public ArrayList<Unit> getUnits() {
    return this.myUnits;
  }
  
  /**
   * Get a list of all the alive Units on this tile
   * @return The alive Units on this tile
   */
  public ArrayList<Unit> getAliveUnits() {
    ArrayList<Unit> aliveUnits = new ArrayList<Unit>();
    for(Unit u : this.myUnits) {
    	if(u.alive) {
    		aliveUnits.add(u);
    	}
    }
    return aliveUnits;
  }
  
  /**
   * Get a list of all the dead Units on this tile
   * @return The dead Units on this tile
   */
  public ArrayList<Unit> getDeadUnits() {
  	ArrayList<Unit> deadUnits = new ArrayList<Unit>();
  	for(Unit u : this.myUnits) {
    	if(!u.alive) {
    		deadUnits.add(u);
    	}
    }
  	return deadUnits;
  }

  /**
   * @return the position representation of the GameBoardHex
   */
  public Position getPosition() {
    return myPosition;
  }

  /**
   * @return if GameBoardHex is highlighted
   */
  public Boolean isHighlighted() {
    return highlighted;

  }

  /**
   * u is appended myUnits
   * 
   * @param u unit to be added on the hex
   */
  public void addUnit(Unit u) {
    if (!u.getPosition().equals(this.myPosition))
      throw new IllegalArgumentException("Position of unit should be equal to position of hex");
    if(myUnits.contains(u))
      throw new IllegalArgumentException("Unit already present on hex, this should never happen");
    
    myUnits.add(u);
    
  }

  /**
   * u is removed from myUnits, nothing is done 
   * 
   * @param u unit to be removed from the hex
   */
  public void removeUnit(Unit u) {

    myUnits.remove(u);
  }
}
