package interpreter;

import java.util.ArrayList;

public class LoopCommand {

  private ArrayList<Command> loopBody;

  public void Execute(ForthCodeParser p) {
    // System.out.println("System defined loop, performing operation: (executing commands in
    // list)");
    int innerLoop = 0;
    for (Command cur : loopBody) {

      if (cur instanceof ForthCommand) {
        cur.Execute();
      }

      if (cur instanceof ForthWord) {
        if (((ForthWord) cur).getLiteralValue() instanceof ForthString) {

          String theWord = ((ForthString) cur).getThisString();

          if (theWord.equals("I")) {
            ForthInt theI = new ForthInt();
            theI.Set(innerLoop);
            p.pushToForthStack(theI);
            // System.out.println("I is :" + innerLoop);
          }
          if (theWord.equals("leave")) {
            // System.out.println("leave keyword, exiting loop");
            break;
          }

        }
        cur.Execute();
      }
      if (cur instanceof IfCommand) {
        // TODO
        cur.Execute();
      }
      if (cur instanceof LoopCommand) {
        // TODO
        // build another loop, recurse
      }

    }
  }

  public void Add(Command commandIn) {
    loopBody.add(commandIn);
  }
}
