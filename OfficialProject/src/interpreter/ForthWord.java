package interpreter;

public class ForthWord implements Command {

  // static to work around the issue of needing discrete execute method implementations
  private Value literalValue;

  public ForthWord() {
    // TODO Auto-generated constructor stub
  }

  public void setLiteralValue(Value valueIn) {
    literalValue = valueIn;
  }

  public Value getLiteralValue() {
    return literalValue;
  }

  public void Execute() {

    // System.out.println(
    // "The word to be executed is literal, push it onto the stack with overloaded method");

    // ForthCodeParser.forthStack.push(literalValue); //old method with static public stack
    // ForthCodeParser.pushToForthStack();
  }

  public void Execute(ForthCodeParser p) {
    p.pushToForthStack(literalValue);
  }

}
