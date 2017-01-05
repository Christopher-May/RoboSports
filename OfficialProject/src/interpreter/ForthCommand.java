package interpreter;

import java.util.ArrayList;

public class ForthCommand implements Command {

  private ArrayList<Command> knownList;

  public ForthCommand() {}

  public void Execute() {
    //System.out.println("System defined word, performing operation: (executing commands in list)");

    for (Command c : knownList) {
      c.Execute();
    }
  }

  public void Add(Command commandIn) {
    knownList.add(commandIn);
  }
}
