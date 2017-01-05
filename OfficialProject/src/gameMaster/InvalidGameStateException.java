package gameMaster;

/** Exception created for invalid game state */
public class InvalidGameStateException extends IllegalStateException {
  private static final long serialVersionUID = 1L;

  public InvalidGameStateException(String message) {
    super(message);
  }
}
