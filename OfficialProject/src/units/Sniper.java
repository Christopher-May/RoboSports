package units;

import java.awt.Image;

import displayManager.DisplayManager;
import gameMaster.TeamColour;
import gameState.Position;

/**
 * The Sniper Unit
 * 
 * @author rowan.maclachlan
 *
 */
public class Sniper extends Unit {

  /** The attack for this Unit type */
  private static int SNIPER_ATTACK = 2;

  /** The movement for this Unit type */
  private static int SNIPER_MOVEMENT = 2;

  /** The health for this Unit type */
  private static int SNIPER_HEALTH = 2;

  /** The range for this unit type */
  private static int SNIPER_RANGE = 3;

  /** The String type of the Sniper */
  public static final String SNIPER_NAME = "SNIPER";

  /**
   * @param initialPosition the initial position of the unit
   * @param team the colour of the unit
   */
  public Sniper(Position initialPosition, TeamColour team) {
    super(SNIPER_ATTACK, SNIPER_MOVEMENT, SNIPER_HEALTH, SNIPER_RANGE, team.getSniper()
        .getScaledInstance(DisplayManager.HEX_SIZE, DisplayManager.HEX_SIZE, Image.SCALE_DEFAULT),
        SNIPER_NAME, initialPosition, team);
  }

}
