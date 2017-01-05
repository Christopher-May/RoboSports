package menuSystem;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The driver of the menu system Handles the overall display about what the user inputs. Should be
 * complete
 * 
 * Note: this class uses a lot of specific numbers that we plan to change to make the menu system
 * resizeable in the future.
 * 
 * 
 * @author Bryton
 *
 */
public class MenuController {

  /**
   * Representing the screen width Note: this is half of 720ps width
   */
  protected static int WIDTH = 640;

  /**
   * representing the screen height Note: this is half of 720ps height With 22 pixels added to
   * represent the bar that the frame adds
   */
  protected static int HEIGHT = 360 + 22;

  /** The stack representing the current path of menus the user has viewed */
  protected static Stack<Menu> menuStack;

  /** The main frame of the menu system */
  protected static JFrame masterFrame;

  /** The main panel of the menu system */
  protected static JPanel masterPanel;

  /** An integer representation of the number of AI that is going to appear in the game */
  protected static int numAI;

  /** An integer representation of the number of players that is going to appear in the game */
  protected static int numPlayers;

  /** An integer representation of the board size, it will either be 5 or 7. */
  protected static int boardSize;

  // /** A list of all the playernames that are going to represent the players */
  // protected static ArrayList<String> playerNames;

  /** Private constructor to ensure the class isn't duplicated */
  private MenuController() {}

  /**
   * Puts the desired menu onto the stack and updates the display
   * 
   * @param menuToAdd The menu we want to view
   */
  protected static void addToStack(Menu menuToAdd) {
    // First if statement to handle the error case where the menuStack was never intialized
    if (null == menuStack) {
      menuStack = new Stack<Menu>();

      // This if statement handles the very first time we add a menu to the stack, during
      // startDisplay
    } else if (0 == menuStack.size()) {
      menuStack.add(menuToAdd);

      // This case is every other case possible
    } else {
      menuStack.add(menuToAdd);
      updateDisplay();
    }
  }

  /**
   * go back a menu on the stack and then update the display if the stack only has 1 menu on it,
   * exit the system
   */
  protected static void removeFromStack() {
    // If the stack only has one menu, by defualt it has to be the main menu so we are exiting
    if (1 == menuStack.size()) {
      System.exit(0);

      // We have to handle every other case
    } else {
      menuStack.pop();
      updateDisplay();
    }
  }

  /**
   * Update the masterPanel to represent the top item of the stack.
   */
  protected static void updateDisplay() {
    // Handle an error case: trying to update the display when the stack is empty
    // Should never occur, but if it does exiting the system is the best bet
    if (0 == menuStack.size()) {
      System.exit(0);
    }

    // The current menu will be the top menu of the stack
    Menu currentMenu = menuStack.peek();

    // Then we remove the master panel from the master frame
    // update the master panel to be the new panel we are displaying
    // re add the master panel to the frame
    // repaint the frame and verify that we are still displaying it.
    masterFrame.remove(masterPanel);
    masterPanel = currentMenu.getMenuPanel();
    masterFrame.add(masterPanel);
    masterFrame.repaint();
    masterFrame.setVisible(true);
  }

  /**
   * setter method to change numAI
   * 
   * @param newnumAI the number of AI we are changing to
   */
  protected static void setnumAI(int newnumAI) {
    numAI = newnumAI;
  }

  /**
   * setter method to change numPlayers
   * 
   * @param newNumPlayers the number of players we are changing to
   */
  protected static void setNumPlayers(int newNumPlayers) {
    numPlayers = newNumPlayers;
  }

  /**
   * A method to verify that the numAI and numPlayers being set are valid based on the rules of the
   * game. Sticks the user in an infinite loop until the player counts are valid.
   * @return Check if valid player count
   */
  protected static boolean verifyPlayerCount() {
    int totalPlayers = numAI + numPlayers;

    // There are 3 possible options to play a valid game
    // 2 players, in which we have a board size of 5
    // 3 players, in which the user gets to choose between a board size of 5 or 7
    // 6 players, in which we have a board size of 7
    switch (totalPlayers) {
      case (2):
        boardSize = 5;
        return true;
      case (3):
        int reply = JOptionPane.showConfirmDialog(null,
            "3 Player games allow a board size"
                + " of 5 or 7.\nThe default is size 5.\nWould you like to increase to size 7?",
            "Board Size Selection", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          boardSize = 7;
        } else {
          boardSize = 5;
        }
        return true;
      case (6):
        if (0 == numAI) {
          boardSize = 7;
          return true;
        }
        return false;
      default:
        // we have an error, so we return false
        // Wherever this method is called handles this error case
        return false;
    }
  }

  // /**
  // * a method that instantiates the list if it doesn't exist
  // * if the name is already in the list, it asks the user for a new name
  // * else, it adds the name to the list until capacity of 6
  // * @param nameToAdd the name we are adding to the list
  // */
  // protected static void addToPlayerNames(String nameToAdd) {
  // if(playerNames == null){
  // playerNames = new ArrayList<String>();
  // }
  //
  // nameToAdd = nameToAdd.toUpperCase();
  // if(6 <= playerNames.size()){
  // JOptionPane.showMessageDialog(new JFrame(),
  // "The List is full, remove other names to enter more");
  // } else if(playerNames.contains(nameToAdd)){
  // removeFromList(nameToAdd);
  // } else {
  // playerNames.add(nameToAdd);
  // }
  // }

  // /**
  // * if the requested name is in the list, we remove it
  // * @param nameToRemove the name the user wants to remove from the list.
  // */
  // protected static void removeFromList(String nameToRemove) {
  // if(playerNames == null){
  // playerNames = new ArrayList<String>();
  // }
  //
  // if(playerNames.contains(nameToRemove)){
  // playerNames.remove(nameToRemove);
  // }
  // }

  // /**
  // * clears the list of all names
  // */
  // protected static void removeAllFromList() {
  // playerNames.clear();
  // }

  /**
   * the main method of this class, it initializes every variable to the default state sets up the
   * default menu frame and panel deals with the position of the frame starts the display
   */
  public static void startDisplay() {
    numAI = 0;
    numPlayers = 0;
    menuStack = new Stack<Menu>();
    // playerNames = new ArrayList<String>();

    masterFrame = new JFrame();
    masterFrame.setTitle("Menu System"); // can be changed to anything
    masterFrame.setResizable(false); // can change if we want it to be resizable
                                     // , easier to do it this way
    masterFrame.setSize(WIDTH, HEIGHT);


    try {
      masterPanel = MenuFactory.getMainMenu().getMenuPanel();
      MenuController.addToStack(MenuFactory.getMainMenu());
    } catch (IOException e1) {
      System.out.println("error in the menu factory's getMainMenu method");
    }

    masterFrame.add(masterPanel);
    masterFrame.setLocation((int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
        (int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    masterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    masterFrame.setVisible(true);

  }

  /**
   * Testing!
   * 
   * @param args not used.
   * @throws InterruptedException used for the timeUnit testing
   */
  public static void main(String args[]) throws InterruptedException {
    // Testing the startDisplay() method, visually verify that a frame popped up roughly in the
    // center of the screen.
    MenuController.startDisplay();

    // Testing for the MenuController class
    MockMenuOne mockMenuOne = new MockMenuOne();
    MockMenuTwo mockMenuTwo = new MockMenuTwo();

    menuStack.add(mockMenuOne);
    updateDisplay();
    TimeUnit.SECONDS.sleep(2);
    menuStack.add(mockMenuTwo);
    updateDisplay();
    TimeUnit.SECONDS.sleep(2);
    menuStack.pop();
    updateDisplay();
    TimeUnit.SECONDS.sleep(2);
    menuStack.clear();
    // updateDisplay(); //Uncomment this to verify that updateDisplay closes the system

    // Testing addToStack and removeFromStack
    MenuController.startDisplay();
    TimeUnit.SECONDS.sleep(2);
    addToStack(mockMenuOne);
    TimeUnit.SECONDS.sleep(2);
    addToStack(mockMenuTwo);
    TimeUnit.SECONDS.sleep(2);
    removeFromStack();
    TimeUnit.SECONDS.sleep(2);
    removeFromStack();
  }
}
