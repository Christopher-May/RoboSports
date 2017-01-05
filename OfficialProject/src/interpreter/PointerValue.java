package interpreter;

public class PointerValue extends Value {

  public String myPointerValue;

  public PointerValue() {
    
  }

  public void Set(String valueIn) {
    myPointerValue = valueIn;
  }

  public String getMyPointerValue() {
    return myPointerValue;
  }

}
