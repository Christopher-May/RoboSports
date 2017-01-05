package gameInitializer;

import java.util.ArrayList;

import gameMaster.GameMaster;

/**
 * Class used to initialize a single player game
 * 
 *
 */
public class SinglePlayerGameInitInfo extends GameInit {

  // /** a string representing the name representation of the only human player */
  // private String humanPlayerName;

  /**
   * A constructor that will initialize everything required for the game master to be created. This
   * class will handle any error testing that wasn't handled in the menu system
   * 
   * @param newNumCPUPlayers The number of CPU players in this game
   * @param newRobotScripts The robot scripts associated with the CPU players, should be of of size
   *        newNumCPUPlayers * 3
   * @param newMapSize The board size, either 5 or 7
   * @param newNumHumanPlayers The number of human players in the game
   */
  public SinglePlayerGameInitInfo(int newNumCPUPlayers, ArrayList<String> newRobotScripts,
      int newMapSize, int newNumHumanPlayers) {

    super(newNumCPUPlayers, newRobotScripts, newMapSize);
    createGame();
  }

  /**
   * A method that creates a gameMaster with the required specifications
   */
  @SuppressWarnings("unused") // for theGameMaster, the constructor starts the game for us.
  private void createGame() {
    ArrayList<String> tankList = new ArrayList<String>();
    ArrayList<String> scoutList = new ArrayList<String>();
    ArrayList<String> sniperList = new ArrayList<String>();

    for (int i = 0; i < myRobotScripts.size() / 3; i++) {
      tankList.add(myRobotScripts.get(i));
      scoutList.add(myRobotScripts.get(i + 1));
      sniperList.add(myRobotScripts.get(i + 2));
    }

    GameMaster theGameMaster =
        new GameMaster(1, numCPUPlayers, tankList, sniperList, scoutList, boardSize);
  }
}

