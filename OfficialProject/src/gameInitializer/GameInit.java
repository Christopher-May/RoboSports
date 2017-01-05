package gameInitializer;

import java.util.ArrayList;

import player.HumanPlayer;
import player.Robot;

/**
 * The abstract gameInit class Incomplete Contains all fields that are shared between both sub
 * initializers
 * 
 * @author Bryton
 *
 */
public abstract class GameInit {

  /** integer to represent the number of CPU players, should only ever be 1, 2 or 3. */
  protected int numCPUPlayers;

  /**
   * List of the scripts needed for the robots, myRobotScripts.length should equal myRobots.length
   */
  protected ArrayList<String> myRobotScripts;

  /** List of the robots */
  protected ArrayList<Robot> myRobots;

  /** List of the create Human Players */
  protected ArrayList<HumanPlayer> myHumans;

  /** the size of the map, should either be 5 or 7 as per game description */
  protected int boardSize;

/**
 * Constructor for the GameInit() abstract class, there can be some stuff in here if we need to do
 * anything generic for each of this classes children.
 * @param newNumCPUPlayers numbers of CPU player
 * @param newRobotScripts robot scripts
 * @param newBoardSize the size of the board
 */
  protected GameInit(int newNumCPUPlayers, ArrayList<String> newRobotScripts, int newBoardSize) {
    numCPUPlayers = newNumCPUPlayers;
    myRobotScripts = newRobotScripts;
    boardSize = newBoardSize;
  }
}
