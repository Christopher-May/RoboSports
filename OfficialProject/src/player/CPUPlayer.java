package player;

import java.util.ArrayList;

import gameManager.RobotObserver;
import gameMaster.TeamColour;
import gameState.GameBoardHex;
import interpreter.ForthString;
import units.Scout;
import units.Sniper;
import units.Tank;
import units.Unit;
import units.UnitFactory;
/**
 * Class representing the CPU Player
 */
public class CPUPlayer extends Player {

  /** An arrayList of the Robots assigned to this CPUPlayer */
  private ArrayList<Robot> myRobots;

  /**
   * Since every CPUPlayer needs to have three robots to exist this method will create those three
   * robots; however, we need to pass in the three scripts.
   * 
   * @param tankScript the script to be used for the tank robot
   * @param sniperScript the script to be used for the sniper robot
   * @param scoutScript the script to be used for the scout robot
   * @param observer RobotObserver, which would be the game manager
   * @param team Team color of the player
   * @param boardSize needed to get initialPosition
   */
  public CPUPlayer(TeamColour team, int boardSize, String tankScript, String sniperScript,
      String scoutScript, RobotObserver observer) {

    myRobots = new ArrayList<Robot>();
    myRobots.add(new Robot(UnitFactory.getScout(team.getInitialPosition(boardSize), team),
        scoutScript, observer));
    myRobots.add(new Robot(UnitFactory.getSniper(team.getInitialPosition(boardSize), team),
        sniperScript, observer));
    myRobots.add(new Robot(UnitFactory.getTank(team.getInitialPosition(boardSize), team),
        tankScript, observer));

    this.isHuman = false;
  }

  public void runScriptOnRobot(int currentRobot) {
    this.myRobots.get(currentRobot).play();
  }

  /**
   * A method used to call the move method of the appropriate unit.
   * 
   * @param unitToMove the unit we are going to move
   * @param tile the tile we are moving the unit to Note: We don't have to test if the tile is
   *        within movement range because the game manager should have already done so.
   * 
   *        Note: unitToMove should represent a robot
   */
  @Override
  public void makeMove(Unit unitToMove, GameBoardHex tile) {
    unitToMove.move(tile);
  }

  /**
   * A method used to call the fire method of the appropriate unit
   * 
   * @param unitToFire the unit that is going to be firing
   * @param tile the tile we are going to shoot Note: We don't have to test if the tile is within
   *        shooting range because the game manager should have already done so.
   * 
   *        Note: unitToFire in this case should represent a robot
   */
  @Override
  public void fireUpon(Unit unitToFire, GameBoardHex tile) {
    unitToFire.shoot(tile, 1);
  }

  @Override
  public ArrayList<Unit> getUnits() {
    ArrayList<Unit> units = new ArrayList<Unit>(3);
    for (Robot currentRobot : myRobots) {
      Unit currentUnit = currentRobot.getUnit();
      // Switch to place the current unit at the correct
      // position in the list.
      switch (currentUnit.getType()) {
        case Scout.SCOUT_NAME:
          units.add(currentUnit);
          break;
        case Sniper.SNIPER_NAME:
          units.add(currentUnit);
          break;
        case Tank.TANK_NAME:
          units.add(currentUnit);
          break;
        default:
          break;
      }
    }
    return units;
  }

  @Override
  public void resetAfterRound() {
    for (Robot robot : this.myRobots) {
      robot.getUnit().resetMovement();
      robot.getUnit().resetShot();
    }
  }

  /**
   * Get the Robot of this CPU player who's Unit is the same as that specified by destType.
   * 
   * @param destType A type of Unit.
   * @return The Robot r who's Unit is the same as that specified by destType, or null if destType
   *         if no Robot can be found.
   */
  public Robot getRobot(ForthString destType) {
    ArrayList<Robot> robots = this.getRobots();
    for (Robot r : robots) {
      if (r.getUnit().getType().equals(destType.getThisString())) {
        return r;
      }
    }
    return null;
  }

  /**
   * Get this CPU PLayer's Robots.
   * 
   * @return This CPU Player's Robots
   */
  private ArrayList<Robot> getRobots() {
    return this.myRobots;
  }

}
