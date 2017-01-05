package interpreter;

import java.util.ArrayList;

public class IfCommand implements Command {


  private ArrayList<String> trueList;

  private ArrayList<String> falseList;

  public IfCommand() {
    trueList = new ArrayList<String>();
    falseList = new ArrayList<String>();
  }

  public void Execute() {

  }

  public void Execute(ForthCodeParser p) {
    //System.out.println("Executing if command");
    p.forthIf(trueList, falseList);
  }

  public void addTrue(String tCommandIn) {
    trueList.add(tCommandIn);
  }

  public void addFalse(String fCommandIn) {
    falseList.add(fCommandIn);
  }

}
