package units;

import java.awt.Image;

import displayManager.DisplayManager;
import gameMaster.TeamColour;
import gameState.Position;

/**
 * The Scout Unit
 * 
 * @author rowan.maclachlan
 *
 */
public class Scout extends Unit {

  /** The attack for this Unit type */
  private static int SCOUT_ATTACK = 1;

  /** The movement for this Unit type */
  private static int SCOUT_MOVEMENT = 3;

  /** The health for this Unit type */
  private static int SCOUT_HEALTH = 1;

  /** The range for this unit type */
  private static int SCOUT_RANGE = 2;

  /** The String type of the Scout */
  public static final String SCOUT_NAME = "SCOUT";

  /**
   * @param initialPosition the initial position of the unit
   * @param team the colour of the unit
   */
  public Scout(Position initialPosition, TeamColour team) {
    super(SCOUT_ATTACK, SCOUT_MOVEMENT, SCOUT_HEALTH, SCOUT_RANGE, team.getScout()
        .getScaledInstance(DisplayManager.HEX_SIZE, DisplayManager.HEX_SIZE, Image.SCALE_DEFAULT),
        SCOUT_NAME, initialPosition, team);
  }

}
