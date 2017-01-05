package units;

import java.awt.Image;

import displayManager.DisplayManager;
import gameMaster.TeamColour;
import gameState.Position;

/**
 * A test unit that was described the the testing plan, used for testing various methods throughout
 * the system
 *
 */
public class TestUnit extends Unit {

  /** The attack for this Unit type */
  private static int ATTACK = 2;

  /** The movement for this Unit type */
  private static int MOVEMENT = 2;

  /** The health for this Unit type */
  private static int HEALTH = 2;

  /** The range for this unit type */
  private static int RANGE = 2;

  /**
   * Creates a dummy unit that can be used for testing purposes
   * 
   * @param initialPosition The initial position we are spawning at
   * @param team corresponds to the colour we are supposed to be
   */
  public TestUnit(Position initialPosition, TeamColour team) {
    super(ATTACK, MOVEMENT, HEALTH, RANGE, team.getScout()
        .getScaledInstance(DisplayManager.HEX_SIZE, DisplayManager.HEX_SIZE, Image.SCALE_DEFAULT),
        "TEST", initialPosition, team);
  }

}
