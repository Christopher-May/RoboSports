package units;

import java.awt.Image;

import displayManager.DisplayManager;
import gameMaster.TeamColour;
import gameState.Position;

/**
 * The Tank Unit
 * 
 * @author rowan.maclachlan
 *
 */
public class Tank extends Unit {

  /** The attack for this Unit type */
  private static int TANK_ATTACK = 3;

  /** The movement for this Unit type */
  private static int TANK_MOVEMENT = 1;

  /** The health for this Unit type */
  private static int TANK_HEALTH = 3;

  /** The range for this unit type */
  private static int TANK_RANGE = 1;

  /** The String type of the Tank */
  public static final String TANK_NAME = "TANK";

  /**
   * @param initialPosition the initial position of this unit
   * @param team the initial colour of this unit
   */
  public Tank(Position initialPosition, TeamColour team) {
    super(TANK_ATTACK, TANK_MOVEMENT, TANK_HEALTH, TANK_RANGE, team.getTank()
        .getScaledInstance(DisplayManager.HEX_SIZE, DisplayManager.HEX_SIZE, Image.SCALE_DEFAULT),
        TANK_NAME, initialPosition, team);
  }

}
