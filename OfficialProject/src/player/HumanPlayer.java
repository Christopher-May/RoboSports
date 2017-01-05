package player;

import java.util.ArrayList;

import gameMaster.TeamColour;
import gameState.GameBoardHex;
import units.Unit;
import units.UnitFactory;
/**
 * Class representing the Human Player
 */
public class HumanPlayer extends Player {

  /** an ArrayList of the units assigned for the human player. */
  private ArrayList<Unit> myUnits;

/**
 * Since every player always has the same three units upon initialization we will create them
 *  here.
 * @param team Team Color of the player 
 * @param boardSize size of the board
 */
  public HumanPlayer(TeamColour team, int boardSize) {
    myUnits = new ArrayList<Unit>();
    myUnits.add(UnitFactory.getScout(team.getInitialPosition(boardSize), team));
    myUnits.add(UnitFactory.getSniper(team.getInitialPosition(boardSize), team));
    myUnits.add(UnitFactory.getTank(team.getInitialPosition(boardSize), team));

    this.isHuman = true;
  }

  @Override
  public void makeMove(Unit unitToMove, GameBoardHex tile) {
    unitToMove.move(tile);
  }

  @Override
  public void fireUpon(Unit unitToFire, GameBoardHex tile) {
    unitToFire.shoot(tile, 0);
  }

  @Override
  public ArrayList<Unit> getUnits() {
    return this.myUnits;
  }

  @Override
  public void resetAfterRound() {
    for (Unit unit : this.myUnits) {
      unit.resetMovement();
      unit.resetShot();
    }
  }

}
