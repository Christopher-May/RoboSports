package gameMaster;

import gameManager.IllegalNumberOfPlayersException;
/**
 * Enumeration for turn order
 */
public enum TurnOrder {
  TWO(2), THREE(3), SIX(6);
  private int number;

  private TurnOrder(int number) {
    this.number = number;
  }

  public static TurnOrder getEnumFromNumber(int number) {
    switch (number) {
      case 2:
        return TurnOrder.TWO;
      case 3:
        return TurnOrder.THREE;
      case 6:
        return TurnOrder.SIX;
      default:
        throw new IllegalNumberOfPlayersException();
    }
  }

  public TeamColour[] getTurnOrder() {

    switch (this.number) {

      case 2:
        return new TeamColour[] {TeamColour.RED, TeamColour.GREEN};
      case 3:
        return new TeamColour[] {TeamColour.RED, TeamColour.YELLOW, TeamColour.BLUE};
      case 6:
        return new TeamColour[] {TeamColour.RED, TeamColour.ORANGE, TeamColour.YELLOW,
            TeamColour.GREEN, TeamColour.BLUE, TeamColour.PURPLE};
      default:
        throw new IllegalNumberOfPlayersException();
    }
  }
}
