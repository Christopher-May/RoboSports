package interpreter;

import java.util.ArrayList;

public class MultiWordCommand implements Command {

  private ArrayList<String> phrase;

  public MultiWordCommand() {
    phrase = new ArrayList<String>();
  }

  public void Execute() {
    //System.out.println("executing via deprecated multiword execute");
  }

  public void Execute(ForthCodeParser p) {
    //System.out.println("Executing list of phrases");
    p.evaluateAllForthWords(phrase, 1);
  }

  public void addStringToList(String commandIn) {

    // commandIn = commandIn.replaceAll(" ", "");
    // System.out.println("*phrase***** adding:"+commandIn);
    phrase.add(commandIn);
  }

}
