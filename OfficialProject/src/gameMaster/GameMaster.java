package gameMaster;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import displayManager.DisplayManager;
import gameManager.GameManager;
import gameManager.IllegalNumberOfPlayersException;
import gameState.GameBoard;
import gameState.GameBoardHex;
import gameState.IdentifyClass;
import gameState.Position;
import interpreter.ForthString;
import interpreter.Value;
import player.Player;
import units.Unit;

public class GameMaster implements GameManagerObservor {

  /** The number of rounds in one turn of the game */
  private static final int ROUNDS_PER_TURN = 3;

  /**
   * an integer representation of the current player turn (maps to the player in the list)
   */
  private int currentPlayerTurn;

  /** The current round in the game. */
  private int currentRound;

  /** current state of the game */
  private GameBoard myGameState;

  /** GameManager that will be taking care of the turns */
  private GameManager myGameManager;

  /** The total number of Players in this game */
  private int totalNumberOfPlayers;

  /** true if we are in an intermission screen, false otherwise. */
  private boolean turnScreenOn;

  /**
   * @param numberOfHumans The total number of Humans
   * @param numberOfAIs The total number of AIs
   * @param tankScripts ArrayList of tanksScripts from robotSelection menu
   * @param sniperScripts ArrayList of sniperScripts from robotSelection menu
   * @param scoutScripts ArrayList of scoutScripts from robotSelection menu
   * @param boardSize : size of the board
   */
  public GameMaster(int numberOfHumans, int numberOfAIs, ArrayList<String> tankScripts,
      ArrayList<String> sniperScripts, ArrayList<String> scoutScripts, int boardSize) {
    totalNumberOfPlayers = numberOfHumans + numberOfAIs;
    // checks if total number of players is 2,3,6
    if (!(totalNumberOfPlayers != 2 || totalNumberOfPlayers != 3 || totalNumberOfPlayers != 6)) {
      throw new IllegalNumberOfPlayersException("Total number of player can be 2,3 or 6");
    }
    // changes gameboard depending on number of players
    myGameState = new GameBoard(boardSize);
    ArrayList<String> sns = new ArrayList<String>();
    ArrayList<String> scs = new ArrayList<String>();
    ArrayList<String> ts = new ArrayList<String>();
    if (0 != numberOfAIs) {
      for (String s : scoutScripts) {
        String st =
            s.replaceAll("\\s+", " ").replaceAll("\\):", ") :").replaceAll("shot\\?", "shot ?");
        scs.add(st);
        // System.out.println(st);
      }
      for (String s : sniperScripts) {
        String st =
            s.replaceAll("\\s+", " ").replaceAll("\\):", ") :").replaceAll("shot\\?", "shot ?");
        sns.add(st);
        // System.out.println(st);
      }
      for (String s : tankScripts) {
        String st = s.replaceAll("\\s+", " ").replaceAll("\\):", ") :")
            .replaceAll("shot\\?", "shot ?").replaceAll("movesLeft 0 \\<\\>", "movesLeft 0 =");
        ts.add(st);
        // System.out.println(st);
      }
    }
    // creates new GameManager throws an exception if the number of scripts !=
    // numberofAI
    try {
      myGameManager = new GameManager(numberOfHumans, numberOfAIs, ts, sns, scs, myGameState, this);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    this.currentPlayerTurn = -1;
    this.currentRound = 0;
    this.turnScreenOn = false;
    nextTurn(); // begin game
  }

  /**
   * Check if the game is over/or there is a winner. This is called at the end of every turn to see
   * if there's a last Player standing.
   * 
   * @return True if the game is over, False otherwise
   */
  public boolean checkIfGameOver() {
    int numDeadUnits = 0;
    int numDeadPlayers = 0;
    for (Player p : this.myGameManager.getPlayers()) {
      numDeadUnits = 0;
      for (Unit u : p.getUnits()) {
        if (!u.alive) {
          numDeadUnits++;
        }
      }
      if (numDeadUnits >= GameManager.NUM_UNITS_PER_PLAYER) {
        numDeadPlayers++;
      }
    }

    if (numDeadPlayers >= this.totalNumberOfPlayers - 1) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * updates the display to have a new board
   * 
   */
  public void updateDisplay() {
    myGameManager.updateDisplay(myGameState, currentPlayerTurn, turnScreenOn);
  }

  /**
   * This is called when a user clicks on the GameBoard. It calls {@link #myGameManager} fire at the
   * tile clicked
   * 
   * @param x the pixel x position
   * @param y the pixel y position
   */
  @Override
  public void mouseClicked(int x, int y) {
    int translateX;
    if (this.myGameState.getBoardSize() == 5) {
      translateX = DisplayManager.TRANSLATE_X_5;
    } else {
      translateX = DisplayManager.TRANSLATE_X_7;
    }
    GameBoardHex clickedHex =
        myGameState.getHexUsingPixels(x, y, translateX, DisplayManager.TRANSLATE_Y, 30);
    if (clickedHex != null) {
      this.myGameManager.shootTarget(this.currentPlayerTurn, clickedHex);
    }
    updateDisplay();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    int translateX;
    ArrayList<Unit> units;

    if (this.myGameState.getBoardSize() == 5) {
      translateX = DisplayManager.TRANSLATE_X_5;
    } else {
      translateX = DisplayManager.TRANSLATE_X_7;
    }
    GameBoardHex hoveredHex =
        myGameState.getHexUsingPixels(x, y, translateX, DisplayManager.TRANSLATE_Y, 30);

    if (hoveredHex != null) {
      if (hoveredHex.isHighlighted() && 0 < hoveredHex.getUnits().size()) {
        units = hoveredHex.getUnits();
        this.myGameManager.getDisplayManager().showUnitInfo(units);
      }
    }
  }

  @Override
  public void handleKeyEvent(char key) {
    Unit cUnit = myGameManager.getCurrentUnit(this.currentPlayerTurn);
    if (null == cUnit) {
      throw new InvalidGameStateException("The current Unit is null.");
    }

    switch (key) {
      case 'a':
        myGameManager.leftTurn(currentPlayerTurn);
        break;
      case 'd':
        myGameManager.rightTurn(currentPlayerTurn);
        break;
      case 'w':
        Position newPos = cUnit.myPosition.getCubicalPosition()
            .neighbourInDirection(cUnit.getDirection()).getPosition();
        GameBoardHex newHex = this.myGameState.getHexFromPosition(newPos);
        myGameManager.move(currentPlayerTurn, newHex);
        break;
      case 'n':
        int nextPlayer = getNextPlayerTurn();
        if (this.isHumanTurn(nextPlayer)) {
          if (!turnScreenOn) {
            turnScreenOn = true;
            this.myGameState.resetHighlight();
            Color currPlayerColour = this.getPlayerColour(nextPlayer);
            this.myGameManager.getDisplayManager().notifyPlayerTurn(currPlayerColour);
            this.updateDisplay();
          } else {
            this.myGameManager.getDisplayManager().endNotification();

            endTurn();
          }
        } else {
          endTurn();
        }
        break;
    }
  }

  @Override
  public void updateTileAfterMove(Unit currUnit) {
    Position oldPos = currUnit.myPosition;
    GameBoardHex hex = this.myGameState.getHexFromPosition(oldPos);
    hex.getUnits().remove(currUnit);
  }

  @Override
  public void endTurn() {
    this.turnScreenOn = false;
    this.myGameState.resetHighlight();
    Unit cu = this.myGameManager.getCurrentUnit(currentPlayerTurn);
    if (null != cu) {
      cu.moved();
      cu.isHighlighted = false;
    }
    if (checkIfGameOver()) {
      endGame();
    }
    round();
    myGameManager.getDisplayManager().updateTurnTime();
    nextTurn();
  }

  /**
   * Find the winner of the game. Get the number of the player who won. If no player won, return -1.
   * 
   * @return -1 if there was a draw, and the number of the winning player otherwise.
   */
  private int findWinner() {
    ArrayList<Player> myPlayers = this.myGameManager.getPlayers();
    for (int i = 0; i < this.totalNumberOfPlayers; i++) {
      for (Unit u : myPlayers.get(i).getUnits()) {
        if (u.alive) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * End the game by finding the winner and asking the display to represent the game being over.
   */
  private void endGame() {
    int winner = findWinner();
    this.myGameManager.getDisplayManager().endGame(winner, myGameManager.getPlayers());
    // TODO Get stats to Robo Librarian and go back to menu //System.\
  }

  /**
   * Checks to see if the current Player is human or not.
   * 
   * @param player confirm if the player is human or not
   * @return True if the current Player is human, false otherwise.
   */
  private boolean isHumanTurn(int player) {
    return this.myGameManager.getPlayerI(player).isHuman();
  }

  /**
   * Get the integer representing the next player's turn.
   * 
   * @return integer representation of next player
   */
  private int getNextPlayerTurn() {
    return (currentPlayerTurn + 1) % totalNumberOfPlayers;
  }

  /**
   * Proceed to the next play in the game. If the current Player is human, process a Human player's
   * turn. If they are a CPU, process a CPU's turn.
   */
  private void nextTurn() {
    this.currentPlayerTurn = getNextPlayerTurn();
    if (!checkIfGameOver()) {
      if (this.isHumanTurn(this.currentPlayerTurn)) {
        this.myGameManager.makeHumanTurn(currentPlayerTurn);
      } else {
        this.myGameManager.makeCPUTurn(currentPlayerTurn);
      }
    }
    this.updateDisplay();
  }

  /**
   * Get the colour of the a player.
   * 
   * @param player : number of players
   * @return The color if 'player'
   */
  private Color getPlayerColour(int player) {
    TurnOrder order = TurnOrder.getEnumFromNumber(this.totalNumberOfPlayers);
    return order.getTurnOrder()[player].getColour();
  }

  /**
   * Check and process round numbers. If we are at the end of a round, advance the round. If we are
   * at the end of a turn, reset the round.
   */
  private void round() {
    // If it is the last round of the turn...
    if (this.currentRound >= ROUNDS_PER_TURN - 1) {
      // For every player in the first round: reset their Units' stats
      this.myGameManager.getPlayerI(currentPlayerTurn).resetAfterRound();
    }
    if (this.currentPlayerTurn >= this.totalNumberOfPlayers - 1) {
      this.currentRound++;
    }
    this.currentRound = this.currentRound % ROUNDS_PER_TURN;
  }

  @Override
  public void forthMoveFromAI() {
    Unit cUnit = myGameManager.getCurrentUnit(this.currentPlayerTurn);
    Position newPos = cUnit.myPosition.getCubicalPosition()
        .neighbourInDirection(cUnit.getDirection()).getPosition();
    GameBoardHex newHex = this.myGameState.getHexFromPosition(newPos);
    myGameManager.move(currentPlayerTurn, newHex);
  }

  @Override
  public void forthTurnFromAI() {
    myGameManager.leftTurn(currentPlayerTurn);
  }

  @Override
  public int forthScan(Position unitPosition, int range) {
    return this.myGameState.forthScan(unitPosition, range);
  }

  @Override
  public ArrayList<Unit> forthScannedUnits(Unit myUnit) {
    return this.myGameState.forthScannedUnits(myUnit.getPosition(), myUnit.getRange());
  }

  @Override
  public IdentifyClass forthIdentify(Unit myUnit, Unit myTarget) {
    return this.myGameState.forthIdentify(myUnit, myTarget);
  }

  @Override
  public void forthShoot(int range, int targetDir, Unit unit) {
    GameBoardHex targetHex = this.myGameState.getHexOfUnitFromRobotNumber(unit.getPosition(), range,
        unit.getDirection(), targetDir);
    if (targetHex != null) {
      this.myGameManager.shootTarget(this.currentPlayerTurn, targetHex);
    }
    updateDisplay();
  }

  @Override
  public String forthCheck(Unit unit, int checkDirection) {
    return this.myGameState.forthCheck(unit, checkDirection);
  }

  @Override
  public boolean sendMessage(ForthString destType, Value payLoad) {
    return this.myGameManager.sendMessageTo(this.currentPlayerTurn, destType, payLoad);
  }
}
