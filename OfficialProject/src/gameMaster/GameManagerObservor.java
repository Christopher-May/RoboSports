package gameMaster;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import gameState.IdentifyClass;
import gameState.Position;
import interpreter.ForthString;
import interpreter.Value;
import units.Unit;

/**
 * Interface created to have interactions between game manager and game master
 */
public interface GameManagerObservor {

  /**
   * Game master that display can be updated
   */
  public void updateDisplay();

  /**
   * To tell game master that mouse is clicked
   * 
   * @param x x coordinate where mouse was clicked
   * @param y y coordinate where mouse was clicked
   */
  public void mouseClicked(int x, int y);

  /**
   * Inform the GameMaster that the key 'key' was pressed, and handle it appropriately. 'a' and 'd'
   * turn right and left, 'w' moves a tile forward, and 'n' moves to the next Player's turn.
   * 
   * @param key the letter which was pressed
   */
  public void handleKeyEvent(char key);

  /**
   * Update the tile from which the Unit currUnit is moving
   * 
   * @param currUnit Unit whose tiles needs to be updated
   */
  public void updateTileAfterMove(Unit currUnit);

  /** End the turn of the current player and move to the next turn */
  void endTurn();

  /**
   * Move for a robot
   */
  public void forthMoveFromAI();

  /**
   * Turn for a robot
   */
  public void forthTurnFromAI();

  /**
   * Scan for the Unit at Position 'p' which has range 'range.
   * 
   * @param p Position of the unit scanning
   * @param range range of the unit scanning
   * @return the number of robots scanned
   */
  public int forthScan(Position p, int range);

  /**
   * Get a list of the Units who are in visible range of the Unit 'myUnit'
   * 
   * @param myUnit That performs scan
   * @return ArrayList of Unit all the scanned units
   */
  public ArrayList<Unit> forthScannedUnits(Unit myUnit);

  /**
   * Tells game master to perform identify on the unit
   * 
   * @param myUnit Unit performing the Identify
   * @param myTarget Unit on which identify was performed
   * @return Information related to identify
   */
  public IdentifyClass forthIdentify(Unit myUnit, Unit myTarget);

  /**
   * tells game master, to perform a shoot
   * 
   * @param range range of the target unit i placed on
   * @param dir direction where target unit is facing
   * @param unit unit on which to attack
   */
  public void forthShoot(int range, int dir, Unit unit);

  /**
   * Check for a Unit over the tile
   * 
   * @param e mouse event information
   */
  public void mouseMoved(MouseEvent e);

  /**
   * Check the contents of the tile in direction 'checkDirection' from Unit 'unit.
   * 
   * @param unit The Unit checking.
   * @param checkDirection The direction to check in.
   * @return Details regarding the contents of the checked tile.
   */
  public String forthCheck(Unit unit, int checkDirection);

  /**
   * Send a message consisting of the value 'payLoad' from the current Unit to the Unit on the
   * current Unit's team of type destType.
   * 
   * @param destType The type of the Unit for which the message is destined.
   * @param payLoad The value of the message
   * @return true if the message was sent successfully, false otherwise.
   */
  public boolean sendMessage(ForthString destType, Value payLoad);

}
