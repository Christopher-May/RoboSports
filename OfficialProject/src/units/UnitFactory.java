package units;

import gameMaster.TeamColour;
import gameState.Position;

/**
 * This Unit Factory provides a convenience for getting Units of the proper type. Units are not
 * recycled
 */
public class UnitFactory {

  /**
   * Get a new Tank
   * 
   * @param inititalPosition The starting Position of this Unit in the game
   * @param team The team of this Robot. Also used to determine initial position.
   * @return A new Tank
   */
  public static Tank getTank(Position inititalPosition, TeamColour team) {
    return new Tank(inititalPosition, team);
  }

  /**
   * Get a new Sniper.
   * 
   * @param inititalPosition The starting Position of this Unit in the game
   * @param team The team of this Robot. Also used to determine initial position.
   * @return A new Sniper
   */
  public static Sniper getSniper(Position inititalPosition, TeamColour team) {
    return new Sniper(inititalPosition, team);
  }

  /**
   * Get a new Scout.
   * 
   * @param inititalPosition The starting Position of this Unit in the game
   * @param team The team of this Robot. Also used to determine initial position.
   * @return A new Scout.
   */
  public static Scout getScout(Position inititalPosition, TeamColour team) {
    return new Scout(inititalPosition, team);
  }

  /**
   * Get a new Test unit. This method is provided as a convenience.
   * @return A new TestUnit
   */
  public static TestUnit getTestUnit() {
    return new TestUnit(new Position(1, 1), TeamColour.BLUE);
  }

}
