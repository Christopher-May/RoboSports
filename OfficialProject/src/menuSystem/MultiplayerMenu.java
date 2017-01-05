package menuSystem;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The code to construct the multiplayer menu Almost complete: I want to make the entering names
 * smoother for non computer scientists I asked for feedback and it wasn't entirely clear and i
 * agree with this Functionality is all in place.
 * 
 * @author Bryton
 *
 */
public class MultiplayerMenu extends Menu {

  /** The number of CPU players the user desires in the game */
  private int numAI;

  /** The number of Human Players the user desires in the game */
  private int numPlayers;

  /** An arrayList of the entered names. */
  private ArrayList<String> names;

  /**
   * the constructor calls the super class constructor that will initialize all common features and
   * then it will initialize all of the above options to their default values.
   */
  public MultiplayerMenu() {
    super();
    names = new ArrayList<String>();
    numPlayers = 2;
    numAI = 0;
    initializeMenu();
  }

  /**
   * a method used to help the constructor initialize the menu.
   */
  private void initializeMenu() {
    // Sets up the panel to its initial state and gives it an absolute layout
    myPanel = new JPanel();
    myPanel.setLayout(null);

    // The main label for the menu
    JLabel titleLabel = new JLabel("Multiplayer");
    titleLabel.setBounds(240, 40, 160, 35);
    titleLabel.setFont(new Font("Sarif", Font.BOLD, 24));
    myPanel.add(titleLabel);

    // The prompt for the number of human players
    JLabel numHumanQuestion = new JLabel("How many Human players are there?");
    numHumanQuestion.setBounds(50, 100, 320, 35);
    numHumanQuestion.setFont(new Font("Sarif", Font.BOLD, 16));
    myPanel.add(numHumanQuestion);

    // The combo box allowing the user to select the number of human players
    JComboBox<Integer> numHumanBox = new JComboBox<Integer>();
    numHumanBox.setBounds(375, 104, 60, 27);
    // The default is the first item entered, 2
    // if we change this default value, update it in the constructor above
    numHumanBox.addItem(2);
    numHumanBox.addItem(3);
    numHumanBox.addItem(6);
    // An action listener that fires when a number is selected
    // the action listener simply calls the selectNumPlayers() method with the number selected
    numHumanBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selectNumPlayers((int) numHumanBox.getSelectedItem());
      }
    });
    myPanel.add(numHumanBox);

    // The prompt for the number of cpu players
    JLabel numAIQuestion = new JLabel("How many Computer players are there?");
    numAIQuestion.setBounds(50, 130, 335, 35);
    numAIQuestion.setFont(new Font("Sarif", Font.BOLD, 16));
    myPanel.add(numAIQuestion);

    // The combo box allowing the user to select the number of cpu players
    JComboBox<Integer> numAIBox = new JComboBox<Integer>();
    numAIBox.setBounds(375, 134, 60, 27);
    // The default is the first item entered, 0
    // if we change this default value, update it in the constructor above
    numAIBox.addItem(0);
    numAIBox.addItem(1);
    // An action listener that fires when a number is selected
    // the action listener simpyl calls the selectNumAI method.
    numAIBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selectNumAI((int) numAIBox.getSelectedItem());
      }
    });
    myPanel.add(numAIBox);

    // Commenting this out for now, we are removing this functionality
    // Will be added in the future
    // //The prompt for the user to enter the names
    // JLabel enterNameQuestion = new JLabel("Enter the player names:");
    // enterNameQuestion.setBounds(50, 175, 270, 16);
    // enterNameQuestion.setFont(new Font("Sarif",Font.BOLD, 16));
    // myPanel.add(enterNameQuestion);
    //
    // //The next three are some extra information about the commands.
    // JLabel information = new JLabel("(Press enter to confirm)");
    // information.setBounds(50, 192, 270, 16);
    // information.setFont(new Font("Sarif",Font.BOLD, 12));
    // myPanel.add(information);
    //
    // JLabel commandsInformationOne = new JLabel("Enter an empty string to clear"
    // + " the list.");
    // commandsInformationOne.setBounds(50, 208, 330, 16);
    // commandsInformationOne.setFont(new Font("Sarif",Font.BOLD, 12));
    // myPanel.add(commandsInformationOne);
    //
    // JLabel commandsInformationTwo = new JLabel("Enter Showme (case sensitive)"
    // + " to show all of the currently selected items.");
    // commandsInformationTwo.setBounds(50, 224, 480, 16);
    // commandsInformationTwo.setFont(new Font("Sarif",Font.BOLD, 12));
    // myPanel.add(commandsInformationTwo);
    //
    // //The text field where the user can enter the name
    // JTextField nameTextField = new JTextField();
    // nameTextField.setBounds(252, 173, 180, 26);
    // //An action listener for the text field
    // //When the enter key is pressed, this action will fire
    // nameTextField.addActionListener(new ActionListener(){
    // public void actionPerformed(ActionEvent e){
    // //We take the user entered string, call the setNames() method with it
    // String name = nameTextField.getText();
    // setNames(name);
    //
    // //And then we update the textfield to inform the user of what was done
    // if(name.equals("")){
    // nameTextField.setText("Clear Command!");
    // } else if (name.equals("Showme")){
    // nameTextField.setText("Showme Command!");
    // } else {
    // nameTextField.setText("Name was registered");
    // }
    // }
    // });
    // myPanel.add(nameTextField);

    // The finalize button
    JButton finalizeButton = new JButton("Confirm");
    finalizeButton.setBounds(382, 249, 110, 43);
    // An action listener for the finalize button
    // This method will fire when the button is pressed
    // when the button is pressed, we call the finalizeSettingsClick() method.
    finalizeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        finalizeSettingsClick();
      }
    });
    myPanel.add(finalizeButton);

    // The goBackButton
    JButton goBackButton = new JButton("Go Back");
    goBackButton.setBounds(149, 249, 110, 43);
    // Adding an action listener to the goBack button
    // When the button is pressed, call the goBackClick() method
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
   * A method that will test if the selected options are valid if they are valid, we send them to
   * the MenuController so that we can continue with game initialization, if they aren't valid, we
   * inform the user and ask them to update their inputted information.
   */
  private void finalizeSettingsClick() {
    // If we don't have enough unique names entered, we inform the player
    // and remind them about the commands
    // if(numPlayers > names.size()){
    // JOptionPane.showMessageDialog(new JFrame(), "Not enough unique names entered"
    // + "\nYou can enter a blank string to clear the list"
    // + "\nYou can enter \"Showme\" to display all names entered");

    // If there is 6 human players, we have to ensure there is no cpu players
    // } else

    // Note: The above commented code is not a part of our system for the base game, we might add it
    // in later.
    if (((6 == numPlayers) && (0 != numAI))) {
      JOptionPane.showMessageDialog(new JFrame(),
          "You cannot have 6 human players and one" + " computer player, please fix your options.");

      // Everything is set up alright, so we send the information to the menu controller
    } else {
      MenuController.numAI = this.numAI;
      MenuController.numPlayers = this.numPlayers;
      // for(String e : names){
      // MenuController.addToPlayerNames(e);
      // }
      if (MenuController.verifyPlayerCount()) {
        MenuController.addToStack(MenuFactory.getRobotSelection());
      } else {
        // Note, this SHOULDN'T occur, but is here as a safety precaution.
        JOptionPane.showMessageDialog(new JFrame(), "Invalid Player Numbers, try again");
      }
    }
  }

  /**
   * Setter for the numAI field
   * 
   * @param newNumAI the new number of AI
   */
  private void selectNumAI(int newNumAI) {
    numAI = newNumAI;
  }

  /**
   * Setter for the numPlayers field
   * 
   * @param newNumPlayers the new number of players
   */
  private void selectNumPlayers(int newNumPlayers) {
    numPlayers = newNumPlayers;
  }

  /**
   * A method to add a new string to the list of strings
   * 
   * @param newName the name we are adding to the list.
   */
  @SuppressWarnings("unused") // It's unused for now, will add it back in when we add the
  // names feature again
  private void setNames(String newName) {
    // The first command, if we have an empty string
    // If there is an empty string, we clear the list of names
    if (newName.equals("")) {
      names.clear();

      // If the command showme was entered
      // we pop up a new frame and display the list of names to the user.
    } else if (newName.equals("Showme")) {
      String displayString = "";
      for (String e : names) {
        displayString += "\n" + e;
      }
      JOptionPane.showMessageDialog(new JFrame(), displayString);
    } else {
      String upperCaseName = newName.toUpperCase();
      if (!(names.contains(upperCaseName))) {
        names.add(upperCaseName);
      }
    }
  }

  /**
   * A method that is called when the rulesClick button is called
   */
  private void rulesClick() {
    MenuController.addToStack(MenuFactory.getRules());
  }

  /**
   * Method is used for testing Note: the method is used for testing but this will simply test the
   * drawing of the menus as of right now.
   * 
   * @param args Unused
   */
  public static void main(String args[]) {
    JFrame testFrame = new JFrame();

    testFrame.setTitle("Testing Drawing the single player menu");
    testFrame.setResizable(false);
    testFrame.setSize(MenuController.WIDTH, MenuController.HEIGHT);
    MultiplayerMenu testPanel = new MultiplayerMenu();
    testFrame.getContentPane().add(testPanel.getMenuPanel());
    testFrame.setLocation((int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
        (int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testFrame.setVisible(true);
  }
}
