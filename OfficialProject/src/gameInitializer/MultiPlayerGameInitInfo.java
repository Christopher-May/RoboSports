package gameInitializer;

import java.util.ArrayList;

import gameMaster.GameMaster;

/**
 * 
 * Class used to initialize a multiplayer game
 *
 */
public class MultiPlayerGameInitInfo extends GameInit {

  /**
   * number of human players required for the multiplayer game note that this number can only either
   * be 1,2,3 or 6 and that numHumanPlayers+numAI players cannot exceed 3 unless numAI is 0 in which
   * numHumanPlayers must be 6
   */
  private int numHumanPlayers;

  // /**a list of names for each human player*/
  // private ArrayList<String> humanPlayerNames;

  /**
   * A constructor that will initialize everything required for the game master to be created. This
   * class will handle any error testing that wasn't handled in the menu system
   * 
   * @param newNumCPUPlayers The number of CPU players in this game
   * @param newRobotScripts The robot scripts associated with the CPU players, should be of of size
   *        newNumCPUPlayers * 3
   * @param newBoardSize The board size, either 5 or 7
   * @param newNumHumanPlayers The number of human players in the game
   */
  public MultiPlayerGameInitInfo(int newNumCPUPlayers, ArrayList<String> newRobotScripts,
      int newBoardSize, int newNumHumanPlayers) {

    super(newNumCPUPlayers, newRobotScripts, newBoardSize);
    numHumanPlayers = newNumHumanPlayers;
    createGame();
  }

  /**
   * A method that creates a gameMaster with the required specifications
   */
  @SuppressWarnings("unused") // This can be added because Game Master constructor handles starting
  // the game
  private void createGame() {
    if (0 == numCPUPlayers) {
      GameMaster theGameMaster =
          new GameMaster(numHumanPlayers, numCPUPlayers, null, null, null, boardSize);
    } else {
      ArrayList<String> tankList = new ArrayList<String>();
      ArrayList<String> scoutList = new ArrayList<String>();
      ArrayList<String> sniperList = new ArrayList<String>();

      for (int i = 0; i < myRobotScripts.size() / 3; i++) {
        tankList.add(myRobotScripts.get(i));
        scoutList.add(myRobotScripts.get(i + 1));
        sniperList.add(myRobotScripts.get(i + 2));
      }

      GameMaster theGameMaster = new GameMaster(numHumanPlayers, numCPUPlayers, tankList,
          sniperList, scoutList, boardSize);
    }
  }
}
