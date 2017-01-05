package gameManager;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import displayManager.DisplayManager;
import gameMaster.GameManagerObservor;
import gameMaster.InvalidGameStateException;
import gameMaster.TeamColour;
import gameMaster.TurnOrder;
import gameState.GameBoard;
import gameState.GameBoardHex;
import gameState.IdentifyClass;
import gameState.Position;
import interpreter.ForthString;
import interpreter.Value;
import player.CPUPlayer;
import player.HumanPlayer;
import player.Player;
import player.Robot;
import units.Unit;
/**
 * Class interacts with display manager and reports to Game master, This class mainly keep track 
 * of the turn of the player.
 */
public class GameManager implements DisplayObservor, RobotObserver {

  /** The number of Units per player */
  public static final int NUM_UNITS_PER_PLAYER = 3;

  /** The list of players that are in the game (should either be 2, 3 or 6 large) */
  private ArrayList<Player> myPlayers;

  /** a reference to the display manager */
  private DisplayManager myDisplay;

  /**
   * an integer representation of the current Unit turn (maps from the Players in the list)
   */
  private int currentUnitTurn;

  /**
   * An observer that is used by the display manager so that we can update things in the game
   * manager without the display manager knowing of its existance
   */
  private GameManagerObservor observer;

  /** An array of team colours that represents the turn order. */
  private TeamColour[] turnOrder;

  /**
   * @param numberOfHumans Total number of human players, selected from menu
   * @param numberOfAIs Total number of AI players, selected from menu
   * @param tankScripts ArrayList of tankScripts from robot selection menu
   * @param sniperScripts ArrayList of sniperScripts from robot selection menu
   * @param scoutScripts ArrayList of scoutScripts from robot selection menu
   * @param gameState the state of the game that we are going to be functioning off of.
   * @param observor GameManagaerObserver which will be gameMaster
   */
  public GameManager(int numberOfHumans, int numberOfAIs, ArrayList<String> tankScripts,
      ArrayList<String> sniperScripts, ArrayList<String> scoutScripts, GameBoard gameState,
      GameManagerObservor observor) {
    // checks if number AI == number of scripts
    // System.out.println(numberOfAIs);
    if (numberOfAIs != 0) {
      if (numberOfAIs != tankScripts.size()) {
        throw new IllegalArgumentException("Number of scripts should match numberOfAIs");
      }

      if (numberOfAIs != sniperScripts.size()) {
        throw new IllegalArgumentException("Number of scripts should match numberOfAIs");
      }

      if (numberOfAIs != scoutScripts.size()) {
        throw new IllegalArgumentException("Number of scripts should match numberOfAIs");
      }
    }

    myPlayers = new ArrayList<Player>();

    int totalPlayers = numberOfHumans + numberOfAIs;
    this.turnOrder = TurnOrder.getEnumFromNumber(totalPlayers).getTurnOrder();
    // cache TeamColour.value() it's expensive!!!!

    int i;
    /*
     * This will assign team colors to player according to turn order First to humans
     */
    for (i = 0; i < numberOfHumans && i < turnOrder.length; i++) {
      myPlayers.add(new HumanPlayer(turnOrder[i], gameState.getBoardSize()));
    }
    /*
     * and then to CPU
     */
    for (int j = i; j < turnOrder.length; j++) {
      myPlayers.add(new CPUPlayer(turnOrder[j], gameState.getBoardSize(), tankScripts.get(j - i),
          sniperScripts.get(j - i), scoutScripts.get(j - i), this));
    }
    currentUnitTurn = 0;
    myDisplay = new DisplayManager(this);

    ArrayList<Unit> units = new ArrayList<Unit>();
    // adds players units into units ArrayList
    units = myPlayers.get(0).getUnits();
    initializeUnits(gameState);
    // gets the display to draw the panel and "start the game"
    Unit currUnit = this.getCurrentUnit(0);
    myDisplay.createDrawHexBoardPanel(gameState, units, currUnit, true);
    this.observer = observor;
  }

  /**
   * Gets every unit for each players then initializes the unit
   * 
   * @param gameState the gameState that we are basing all construction off of
   */
  private void initializeUnits(GameBoard gameState) {
    ArrayList<Unit> units;
    for (int i = 0; i < myPlayers.size(); i++) {
      units = myPlayers.get(i).getUnits();
      for (Unit unit : units) {
        gameState.getHexFromPosition(unit.myPosition).addUnit(unit);
      }
    }
  }

  /**
   * Find the next Unit that should move for the Player currPlayer.
   * @param currPlayer The current player.
   * @return True if a Unit was found, False otherwise.
   */
  private boolean setCurrentUnitTurn(Player currPlayer) {
    // Then getting the unit that is next up to move.
    this.currentUnitTurn = -1;
    boolean foundUnit = false;
    Unit cu;

    int unitOffset = 0;
    // While we haven't found a unit and while there are still unit to check...
    while (!foundUnit && unitOffset < currPlayer.getUnits().size()) {
      cu = currPlayer.getUnits().get(unitOffset);
      if (cu.alive && !cu.hasMoved) {
      	cu.isHighlighted = true;
        this.currentUnitTurn = unitOffset;
        foundUnit = true;
      }
      unitOffset++;
    }
    return foundUnit;
  }

  /**
   * Find the next Unit to move for the current human player. If the current player has no units to
   * move this turn, end their turn.
   * @param playerNumber the number of the current player
   */
  public void makeHumanTurn(int playerNumber) {
    // Getting the current players we are going to do the turn of.
    HumanPlayer currPlayer = (HumanPlayer) myPlayers.get(playerNumber);
    // Then getting the unit that is next up to move.
    boolean foundUnit = this.setCurrentUnitTurn(currPlayer);
    // If we did not find a Unit that could move,
    // our turn is done.
    if (!foundUnit) {
      doneTurn();
    }
  }

  /**
   * Find the next Unit to move for the current CPU player. 
   * If the current player has no units to move this turn, end their turn.
   * @param playerNumber the number of the current player
   */
  public void makeCPUTurn(int playerNumber) {
    // Getting the current players we are going to do the turn of.
    CPUPlayer currPlayer = (CPUPlayer) myPlayers.get(playerNumber);
    // Then getting the unit that is next up to move.
    boolean foundUnit = this.setCurrentUnitTurn(currPlayer);
    // If we did not find a Unit that could move,
    // our turn is done.
    if (foundUnit) {
      currPlayer.runScriptOnRobot(this.currentUnitTurn);
    }
    doneTurn();
  }

  /**
   * Update the display manager to the view of the current Player a method used to call the
   * appropriate method in the display manager the appropriate method will be whatever currently
   * needs to be updated.
   * @param myGameState the gameState that we are going to update the display 
   * @param currPlayerTurn update according to the player
   * @param turnScreenOn, is it between the turns
   */
  public void updateDisplay(GameBoard myGameState, int currPlayerTurn, boolean turnScreenOn) {
    Unit currUnit = this.getCurrentUnit(currPlayerTurn);
    ArrayList<Unit> units = myPlayers.get(currPlayerTurn).getUnits();
    System.out.println("GM unit: " + currUnit.getType());
    this.myDisplay.createDrawHexBoardPanel(myGameState, units, currUnit, turnScreenOn);
  }

  /**
   * Get a list of the current Player's Units.
   * @param currPlayer The current player of this Game Manager
   * @return an array of units for the currently active player.
   */
  public ArrayList<Unit> getUnits(int currPlayer) {
    return this.myPlayers.get(currPlayer).getUnits();
  }

  /**
   * Get the number of the current Robot.
   * @return the integer value of the current robot's turn.
   */
  public int getCurrentRobotTurn() {
    return this.currentUnitTurn;
  }

  /**
   * Get all the players in the game.
   * @return This GameManager's Players.
   */
  public ArrayList<Player> getPlayers() {
    return this.myPlayers;
  }

  /**
   * Retrieve the current Unit of the current player.
   * @param currPlayer The current player of this Game Manager
   * @return The current Unit of the current Player
   */
  public Unit getCurrentUnit(int currPlayer) {
    if (this.currentUnitTurn < 0 || this.currentUnitTurn >= NUM_UNITS_PER_PLAYER) {
      return null;
    } else {
      return this.myPlayers.get(currPlayer).getUnits().get(this.currentUnitTurn);
    }
  }

  /**
   * Attempt to shoot the clickedHex with the current Player's current Robot.
   * This will attempt will fail if there are no units on the targeted tile.
   * @param currentPlayerTurn The turn number of the current Player
   * @param clickedHex the tile we want to shoot
   */
  public void shootTarget(int currentPlayerTurn, GameBoardHex clickedHex) {
    Unit cUnit = this.getUnits(currentPlayerTurn).get(this.currentUnitTurn);
    if (null == cUnit) {
      throw new InvalidGameStateException("The current Unit is null.");
    }

    int distance = cUnit.getPosition().getTileDistance(clickedHex.getPosition());

    if (0 <= clickedHex.getUnits().size()) {
      cUnit.shoot(clickedHex, distance);
    }
  }

  @Override
  public void mouseClicked(int x, int y) {
    observer.mouseClicked(x, y);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    observer.mouseMoved(e);
  }

  @Override
  public void handleKeyEvent(char key) {
    this.observer.handleKeyEvent(key);
  }

  /**
   * Notify this GameManager's observer that
   * an AI has requested to move.
   */
  public void forthMove() {
    this.observer.forthMoveFromAI();
  }

  /**
   * Notify this GameManager's observer that an
   * AI has requested to turn.
   */
  public void forthTurn() {
    this.observer.forthTurnFromAI();
  }

  /**
   * Move the current Unit of the current player from that
   * Unit's current position to the position 'dest'.  This move
   * attempt will fail if the destination tile is off the board, or
   * the current units does not have any moves left.
   * @param currentPlayerTurn the player who is currently active
   * @param dest the hexagonal tile destination of the player
   */
  public void move(int currentPlayerTurn, GameBoardHex dest) {
    if (null == dest) {
      return;
    }

    Unit currU = getCurrentUnit(currentPlayerTurn);
    if (null == currU) {
      throw new InvalidGameStateException("The current Unit is null.");
    }

    if (0 == currU.movesLeft) {
      return;
    }
    if(currU.alive) {
    	this.observer.updateTileAfterMove(currU);
    	currU.move(dest);
    	this.observer.updateDisplay();
    }
  }

  /**
   * Rotate the active unit of the current player 60 degrees to the left
   * @param currentPlayerTurn the player who is currently active
   */
  public void leftTurn(int currentPlayerTurn) {
    Player currP = this.myPlayers.get(currentPlayerTurn);
    Unit currU = currP.getUnits().get(this.currentUnitTurn);
    currU.turnRight();
    this.observer.updateDisplay();
  }

  /**
   * rotate the active unit of the current player 60 degrees to the right
   * @param currentPlayerTurn the player who is currently active
   */
  public void rightTurn(int currentPlayerTurn) {
    Player currP = this.myPlayers.get(currentPlayerTurn);
    Unit currU = currP.getUnits().get(this.currentUnitTurn);
    currU.turnLeft();
    this.observer.updateDisplay();
  }

  /**
   * a method that is called when the player is done their turn
   */
  public void doneTurn() {
    observer.endTurn();
  }

  /**
   * returns the player at index i in the myPlayers Array List
   * @param i the player to return
   * @return the player
   */
  public Player getPlayerI(int i) {
    return this.myPlayers.get(i);
  }

  @Override
  public int scan(Unit u) {
    Position p = u.getPosition();
    int r = u.getRange();
    return this.observer.forthScan(p, r);
  }

  /**
   * Get the DisplayManager
   * @return myDisplay This GameManager's DisplayManager
   */
  public DisplayManager getDisplayManager() {
    return this.myDisplay;
  }

  @Override
  public IdentifyClass forthIdentify(Unit myUnit, Unit myTarget) {
    return this.observer.forthIdentify(myUnit, myTarget);
  }

  @Override
  public ArrayList<Unit> forthScannedUnits(Unit myUnit) {
    return this.observer.forthScannedUnits(myUnit);
  }

  @Override
  public void forthShoot(int range, int dir, Unit unit) {
    this.observer.forthShoot(range, dir, unit);
  }

  @Override
  public String forthCheck(Unit unit, int checkDirection) {
    return this.observer.forthCheck(unit, checkDirection);
  }

  @Override
  public boolean sendMessage(ForthString destType, Value payLoad) {
    return this.observer.sendMessage(destType, payLoad);
  }

  /**
   * Send a message consisting of the value 'payLoad' 
   * from the current Unit to the Unit on the current Unit's team of type destType. 
   * @param currentPlayerTurn the current player's turn number. 
   * @param destType The type of the Unit for which the message is destined.
   * @param payLoad The value of the message
   * @return true if the message was sent successfully, false otherwise.
   */
  public boolean sendMessageTo(int currentPlayerTurn, ForthString destType, Value payLoad) {
    Robot r = ((CPUPlayer) this.getPlayerI(currentPlayerTurn)).getRobot(destType);
    String sentFrom = this.getCurrentUnit(currentPlayerTurn).getType();
    if (r.getUnit().alive && r.getMailBoxCount(destType) <= 6) {
      return r.sendMessageTo(sentFrom, payLoad);
    } else {
      return false;
    }
  }

}
