package player;

import java.util.ArrayList;

import gameState.GameBoardHex;
import units.Unit;
/**
 * Class representing the player, which can be Human or CPU player
 */
public abstract class Player {

  /**
   * Is this player a human player or a computer player? True if it is a human player, false
   * otherwise
   */
  protected boolean isHuman;

  /**
   * Move the Unit 'unitToMove' from its current tile to the tile 'tile'.
   * 
   * @param unitToMove A Unit who's turn it currently is.
   * @param tile A tile to move towards.
   */
  public abstract void makeMove(Unit unitToMove, GameBoardHex tile);

  /**
   * Fire upon the GameBoardHex 'tile' with the Unit 'unitToFire'.
   * 
   * @param unitToFire A Unit who's turn it currently is.
   * @param tile A tile to fire at.
   */
  public abstract void fireUpon(Unit unitToFire, GameBoardHex tile);

  /**
   * Get a list of this Player's Units. The units should be ordered in the ArrayList as Scout,
   * Sniper, and then Tank.
   * 
   * @return This Player's Units
   */
  public abstract ArrayList<Unit> getUnits();

  /**
   * Re-assign movement and firing flags to this player's Units.
   */
  public abstract void resetAfterRound();

  /**
   * Is this a human player or a computer player?
   * 
   * @return true if it is a human player, false otherwise.
   */
  public boolean isHuman() {
    return this.isHuman;
  }

}
