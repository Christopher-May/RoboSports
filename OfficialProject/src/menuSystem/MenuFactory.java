package menuSystem;

import java.io.IOException;

/**
 * A factory class to return the desired menu whenever it is requested The menu will only be created
 * once and we will always return that same menu
 * 
 * @author Bryton
 *
 */
public class MenuFactory {

  /** The variable holding the main menu */
  private static MainMenu MainMenu;

  /** The variable holding the single player menu */
  private static SinglePlayerMenu spMenu;

  /** The variable holding the multiplayer menu */
  private static MultiplayerMenu mpMenu;

  /** The variable holding the robot selection menu */
  private static RobotSelection robotSelection;

  /** The variable holding the rules menu */
  private static Rules rules;

  /** A private construct to ensure this class is never created. */
  private MenuFactory() {}

  /**
   * if the main menu variable is null, create the main menu and then return it if its not null,
   * return it
   * 
   * @return the main menu of the system
   * @throws IOException if resource cannot be found
   */
  public static MainMenu getMainMenu() throws IOException {
    if (null == MainMenu) {
      MainMenu = new MainMenu();
    }
    return MainMenu;
  }

  /**
   * if the single player menu variable is null, create the single player menu and then return it if
   * its not null, return it
   * 
   * @return the single player menu of the system
   */
  public static SinglePlayerMenu getSPMenu() {
    if (null == spMenu) {
      spMenu = new SinglePlayerMenu();
    }
    return spMenu;
  }

  /**
   * if the multiplayer menu variable is null, create the multiplayer menu and then return it if its
   * not null, return it
   * 
   * @return the multiplayer menu of the system
   */
  public static MultiplayerMenu getMPMenu() {
    if (null == mpMenu) {
      mpMenu = new MultiplayerMenu();
    }
    return mpMenu;
  }

  /**
   * if the robot selection menu variable is null, create the robot selection menu and then return
   * it, if its not null; return it
   * 
   * @return the robot selection menu of the system
   */
  public static RobotSelection getRobotSelection() {
    if (null == robotSelection) {
      robotSelection = new RobotSelection();
    }
    return robotSelection;
  }

  /**
   * if the rules menu variable is null, create the rules menu and then return it if its not null,
   * return it
   * 
   * @return the rules menu of the system.
   */
  public static Rules getRules() {
    if (null == rules) {
      rules = new Rules();
    }
    return rules;
  }

}
