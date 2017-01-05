package gameState;

public class IllegalCubicalPosition extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  public IllegalCubicalPosition() {
    super();
  }

  public IllegalCubicalPosition(String message) {
    super(message);
  }

  public IllegalCubicalPosition(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalCubicalPosition(Throwable cause) {
    super(cause);
  }
}
