package gameState;

public class IllegalBoardSizeException extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  public IllegalBoardSizeException() {
    super();
  }

  public IllegalBoardSizeException(String message) {
    super(message);
  }

  public IllegalBoardSizeException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalBoardSizeException(Throwable cause) {
    super(cause);
  }
}
