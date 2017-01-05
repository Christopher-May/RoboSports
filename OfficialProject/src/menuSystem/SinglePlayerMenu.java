package menuSystem;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * The code to construct the single player menu
 * 
 * @author Bryton
 */
public class SinglePlayerMenu extends Menu {

  /// ** the field representing the users desired name */
  // private String playerName;

  /** the selected number of AI */
  private int numAI;

  /**
   * A constructor that calls the common super class constructor loads in the correct image and then
   * sets up the above fields (more to come to this comment when the constructor is complete)
   */
  public SinglePlayerMenu() {
    super();
    numAI = 1;
    initializeMenu();
  }

  /**
   * a method used to help the constructor initialize the menu. Note: If we decide we want the menu
   * system to be resizeable, we will have to change the pixel positions to be precentage of screen
   * size based, not static as they are now
   */
  private void initializeMenu() {
    // Initializing the panel and setting the layout to pixel position absolute layout.
    myPanel = new JPanel();
    myPanel.setLayout(null);

    // Setting up, positioning, and adding the title of the menu to the panel
    JLabel titleLabel = new JLabel("Single Player");
    titleLabel.setBounds(240, 40, 160, 35);
    titleLabel.setFont(new Font("Sarif", Font.BOLD, 24));
    myPanel.add(titleLabel);

    // Setting up, positioning, and adding the numAI query to the panel
    JLabel numAIQuestion = new JLabel("How many computer players are there?");
    numAIQuestion.setBounds(50, 100, 320, 35);
    numAIQuestion.setFont(new Font("Sarif", Font.BOLD, 16));
    myPanel.add(numAIQuestion);

    // Creating a combo box that allows the user to select the number of AI
    // and adding this combo box to the panel
    // The combox box defaults to the first added item.
    JComboBox<Integer> numAIBox = new JComboBox<Integer>();
    numAIBox.setBounds(370, 104, 60, 27);
    numAIBox.addItem(1);
    numAIBox.addItem(2);
    // Adding an action listener that calls setNumAI when an action is preformed.
    // In this case, an action is selecting an item in the combo box.
    numAIBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setNumAI((int) numAIBox.getSelectedItem());
      }
    });
    myPanel.add(numAIBox);

    // Commenting this method out as we have decided to not use this functionality for now.
    // //Setting up, positing, and adding the playerName query to the panel
    // JLabel enterNameQuestion = new JLabel("What is your name?");
    // enterNameQuestion.setBounds(60, 175, 270, 16);
    // enterNameQuestion.setFont(new Font("Sarif",Font.BOLD, 16));
    // myPanel.add(enterNameQuestion);
    //
    // //Setting up, positioning, and adding some extra textual information to the panel.
    // JLabel information = new JLabel("(Press enter to confirm)");
    // information.setBounds(50, 192, 270, 16);
    // information.setFont(new Font("Sarif",Font.BOLD, 16));
    // myPanel.add(information);
    //
    // //Setting up a textfield for the player to enter their name
    // JTextField nameTextField = new JTextField();
    // nameTextField.setBounds(260, 173, 130, 26);
    // //Adding an action listener to the nameTextField
    // //This action listener calls the setName method when the enter key is pressed
    // //Any text that is in the textfield will be set to the player name
    // //This text will overwrite the previous playername if it was already set.
    // nameTextField.addActionListener(new ActionListener(){
    // public void actionPerformed(ActionEvent e){
    //
    // setName(nameTextField.getText());
    // nameTextField.setText("You entered: " + nameTextField.getText());
    // }
    // });
    // myPanel.add(nameTextField);

    // Setting up the finalize button
    JButton finalizeButton = new JButton("Confirm");
    finalizeButton.setBounds(382, 249, 110, 43);
    // Adding an action listener to the finalize button
    // When the button is pressed, call the finalizeSettingsClick() method
    finalizeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        finalizeSettingsClick();
      }
    });
    myPanel.add(finalizeButton);

    // Setting up the goBack button
    JButton goBackButton = new JButton("Go Back");
    goBackButton.setBounds(149, 249, 110, 43);
    // Adding an action listener to the goback button
    // When the button is pressed, call the goBackClick() method.
    goBackButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goBackClick();
      }
    });
    myPanel.add(goBackButton);

    // Setting up the rules Button
    JButton RulesButton = new JButton("Rules");
    RulesButton.setBounds(280, 320, 80, 30);
    // Adding an action listener to the rules button
    // When the button is pressed, call the rulesClick() method
    RulesButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rulesClick();
      }
    });
    myPanel.add(RulesButton);

    // Setting up the background image,
    JLabel backgroundLabel = new JLabel("");
    Image scaledImage = menuImage.getScaledInstance(MenuController.WIDTH, MenuController.HEIGHT,
        Image.SCALE_DEFAULT);
    ImageIcon icon = new ImageIcon(scaledImage);
    backgroundLabel.setIcon(icon);
    backgroundLabel.setBounds(0, 0, MenuController.WIDTH, MenuController.HEIGHT);
    myPanel.add(backgroundLabel);
    myPanel.repaint();
  }

  /**
   * A method that is called when the finalize settings button is clicked. Sets all of the menu
   * controller static fields properly.
   */
  private void finalizeSettingsClick() {
    // For the game to start, the player MUST have entered a name
    // This is so we don't have to have testing if they did/didn't enter a name later
    // if(null == playerName){
    // JOptionPane.showMessageDialog(new JFrame(), "Please enter a name in the textbox!");
    // } else {
    // the static number of cpu players is set to the
    // user selected number of cpu players
    MenuController.numAI = this.numAI;
    // the static number of human players is set to the
    // user selected number of human players
    MenuController.numPlayers = 1;
    // Ensuring that the list is empty and then adding the user entere name to the list
    // MenuController.removeAllFromList();
    // MenuController.addToPlayerNames(this.playerName);

    // Verifying that the playercount is valid
    // This also sets the board size to be appropriate
    // if playercount == 2, board size is 5
    // if playercount == 3, board size is 5 or 7
    // if playercount == 6, board size is 7
    if (MenuController.verifyPlayerCount()) {
      MenuController.addToStack(MenuFactory.getRobotSelection());
    } else {
      // Note, this SHOULDN'T occur, but is here as a safety precaution.
      JOptionPane.showMessageDialog(new JFrame(), "Invalid Player Numbers, try again");
    }
    // }
  }

  /**
   * Setter method for the number of AI
   * 
   * @param newAI the new AI
   */
  private void setNumAI(int newAI) {
    this.numAI = newAI;
  }

  // /**
  // * A setter method for the name
  // * @param newName the desired new name
  // */
  // //names feature again
  // private void setName(String newName) {
  // this.playerName = newName;
  // }

  /**
   * A method for when the rules button is clicked
   */
  private void rulesClick() {
    MenuController.addToStack(MenuFactory.getRules());
  }

  /**
   * Method is used for testing Note: this method doesn't test full method functionality it is only
   * used to test that the menu draws properly To test full menu system functionality, run the
   * MenuController tests and click through the menu system.
   * 
   * @param args Unused
   */
  public static void main(String args[]) {
    JFrame testFrame = new JFrame();

    testFrame.setTitle("Testing Drawing the single player menu");
    testFrame.setResizable(false);
    testFrame.setSize(MenuController.WIDTH, MenuController.HEIGHT);
    SinglePlayerMenu testPanel = new SinglePlayerMenu();
    testFrame.getContentPane().add(testPanel.getMenuPanel());
    testFrame.setLocation((int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
        (int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testFrame.setVisible(true);
  }
}
