package gameMaster;

/**
 * Exception created for invalid images
 */
public class IllegalImageException extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  /**
   * Exception for incorrect number of players
   */
  public IllegalImageException() {
    super();
  }

  public IllegalImageException(String message) {
    super(message);
  }

  public IllegalImageException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalImageException(Throwable cause) {
    super(cause);
  }
}
