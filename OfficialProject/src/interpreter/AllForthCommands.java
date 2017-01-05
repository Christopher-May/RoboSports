package interpreter;

public class AllForthCommands implements Command {

  private String commandName;// cheat field to store key that makes loops work

  @Override
  public void Execute() {
    //System.out.println("Should use the overloaded Execute method instead, Execute() deprecated");
  }

  public String getCommandName() {
    return commandName;
  }

  public void setCommandName(String stringIn) {
    commandName = stringIn;
  }

  public void Execute(ForthCodeParser p, String op) {
    // System.out.println("Addcommand is going to perform operation: " + op);
    switch (op) {
      // arithmetic
      case "+":
        p.forthAdd();
        break;
      case "-":
        p.forthSubtract();
        break;
      case "*":
        p.forthMultiply();
        break;
      case "/mod":
        p.forthModulus();
        break;

      // logic
      case "and":
        p.forthAnd();
        break;
      case "or":
        p.forthOr();
        break;
      case "invert":
        p.forthInvert();
        break;

      // comparisons
      case "<":
        p.forthLessThan();
        break;
      case "<=":
        p.forthLessOrEqual();
        break;
      case "=":
        p.forthEqual();
        break;
      case "<>":
        p.forthNotEqual();
        break;
      case "=>":
        p.forthGreaterOrEqual();
        break;
      case ">":
        p.forthGreaterThan();
        break;

      // Stack words
      case "drop":
        p.forthDrop();
        break;
      case "dup":
        p.forthDup();
        break;
      case "swap":
        p.forthSwap();
        break;
      case "rot":
        p.forthRotate();
        break;

      // Variables
      // TODO implement ? !
      // variable name, and name are omitted and should be implemented in the parser
      case "?":
        break;
      case "!":
        break;

      // misc
      // TODO implement break
      case "random":
        break;
      case ".":
        p.fDot();
        break;
    }
  }
}
