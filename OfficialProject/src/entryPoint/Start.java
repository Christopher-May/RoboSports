package entryPoint;
/**
 * Main class, where execution starts 
 *
 */
public class Start {

  /**
   * private constructor as this class never needs to be created, simply used as an entry point into
   * the system.
   */
  private Start() {}

  /**
   * this method is used to do a few initializations and then start the game. fill this in more when
   * it is coded.
   * 
   * @param args not used for anything, simply required by java
   */
  public static void main(String args[]) {
    menuSystem.MenuController.startDisplay();
  }

}
