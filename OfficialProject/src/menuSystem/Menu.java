package menuSystem;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Abstract class containing all of the shared methods for the menus handles initialization of the
 * background image
 * 
 * @author Bryton
 *
 */
public abstract class Menu {

  /** the main panel of the menu */
  public JPanel myPanel;

  /** the image/background of this menu */
  protected Image menuImage;

  /**
   * Constructor for the abstract class Gets the generic background image for the class as they all
   * use the same.
   */
  public Menu() {
    try {
      menuImage = ImageIO.read(new File("resources/ImageMenuBackground.jpg"));
    } catch (IOException e) {
      System.out.println(
          "There is an error with the generic background" + " image in the resources folder.");
    }
  }

  /**
   * Getter for the JPanel
   * 
   * @return the JPanel associated to the menu
   */
  public JPanel getMenuPanel() {
    return myPanel;
  }

  /**
   * Adds menuToAdd to the stack of menus
   * 
   * @param menuToAdd the menu we are going to add
   */
  public void addToStack(Menu menuToAdd) {
    MenuController.addToStack(menuToAdd);
  }

  /**
   * Method that is called when the go back button is clicked
   */
  public void goBackClick() {
    MenuController.removeFromStack();
  }
}
