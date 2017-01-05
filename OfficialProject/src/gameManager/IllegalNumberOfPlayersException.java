package gameManager;

/**
 * Exception for Invalid number of players
 */
public class IllegalNumberOfPlayersException extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  /**
   * Exception for incorrect number of players
   */
  public IllegalNumberOfPlayersException() {
    super();
  }

  public IllegalNumberOfPlayersException(String message) {
    super(message);
  }

  public IllegalNumberOfPlayersException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalNumberOfPlayersException(Throwable cause) {
    super(cause);
  }

}
