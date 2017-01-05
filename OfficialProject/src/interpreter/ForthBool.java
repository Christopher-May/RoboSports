package interpreter;

public class ForthBool extends Value {

  private boolean thisBool;

  public void Set(boolean valueIn) {
    thisBool = valueIn;
  }

  public boolean getThisBool() {
    return thisBool;
  }
}
