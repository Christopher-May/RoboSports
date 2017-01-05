package interpreter;

public class ForthString extends Value {

  private String thisString;

  public ForthString() {

  }

  public void Set(String valueIn) {
    thisString = valueIn;
  }

  public String getThisString() {
    return thisString;
  }

}
