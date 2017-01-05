package gameManager;

import java.util.ArrayList;

import gameState.IdentifyClass;
import interpreter.ForthString;
import interpreter.Value;
import units.Unit;

/**
 * 
 * Robot observer is create in order to to be able to have interaction between game manager and
 * Robot
 *
 */
public interface RobotObserver {

  /**
   * Get the number of Robots visible to Unit 'u'.
   * 
   * @param u The Unit which is scanning.
   * @return The number of Robots within the vision of Unit 'u'.
   */
  int scan(Unit u);

  /**
   * Move the current unit forward one tile
   */
  void forthMove();

  /**
   * Turn the current unit one hexagon face to the left.
   */
  void forthTurn();

  /**
   * Identify the target unit.
   * 
   * @param myUnit The unit who wants to identify
   * @param myTarget the Unit being identified
   * @return Information about the Unit 'myTarget'.
   */
  IdentifyClass forthIdentify(Unit myUnit, Unit myTarget);

  /**
   * Retrieve a list of the units who are in the visibile range of 'myUnit'
   * 
   * @param myUnit unit performing the scan
   * @return The Units within visible range of 'myUnit'
   */
  ArrayList<Unit> forthScannedUnits(Unit myUnit);

  /**
   * Attempt to fire upon the tile at range and direction 'range' and 'dir' by Unit 'unit'.
   * 
   * @param range The range of the target tile
   * @param dir The direction of the target tile
   * @param unit The Unit attempting to shoot.
   */
  void forthShoot(int range, int dir, Unit unit);

  /**
   * Check the contents of the tile in direction 'checkDirection' from Unit 'unit'.
   * 
   * @param unit The Unit checking
   * @param checkDirection The direction to check in.
   * @return Details concerning the contents of the tile checked.
   */
  String forthCheck(Unit unit, int checkDirection);

  /**
   * Send a message consisting of of the Value 'payload' from the current Unit to the current Unit's
   * teamate of type destType.
   * 
   * @param destType The type of the Unit this message is destined for.
   * @param payLoad The contents of the message.
   * @return true if the send was successful, false otherwise.
   */
  boolean sendMessage(ForthString destType, Value payLoad);

}
