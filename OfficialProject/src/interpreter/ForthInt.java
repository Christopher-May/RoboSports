package interpreter;

public class ForthInt extends Value {

  private int thisInt;

  public ForthInt() {

  }

  public void Set(int valueIn) {
    thisInt = valueIn;
  }

  public int getThisInt() {
    return thisInt;
  }


}
