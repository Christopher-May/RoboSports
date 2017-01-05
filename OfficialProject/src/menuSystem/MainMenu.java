package menuSystem;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

/**
 * The code to set up the main menu.
 * 
 * @author Bryton
 *
 */
public class MainMenu extends Menu {

  /**
   * calls the super class constructor sets up the menu image for this menu sets up the above fields
   * (update this comment when more is added)
   */
  public MainMenu() {
    super();
    try {
      initializeMenu();
    } catch (IOException e) {
      System.out.println("An icon image is missing from the resources folder!");
    }
  }

  /**
   * a method used to help the constructor initialize the menu.
   * 
   * @throws IOException throws an exception when the image(s) fail to load
   */
  private void initializeMenu() throws IOException {
    // Initialize the panel and give it an absolute layout
    myPanel = new JPanel();
    myPanel.setLayout(null);

    // Setting up the title
    JLabel titleLabelOne = new JLabel("BATTLE OF TANKS");
    titleLabelOne.setFont(new Font("Dialog", Font.BOLD, 30));
    titleLabelOne.setBounds(60, 40, 300, 50);
    myPanel.add(titleLabelOne);

    // Adding a nice scout image
    JLabel scoutImage = new JLabel("");
    Image scoutScaled = ImageIO.read(new File("resources/ImageBlueScout.png"))
        .getScaledInstance(128, 72, Image.SCALE_DEFAULT);
    ImageIcon scoutIcon = new ImageIcon(scoutScaled);
    scoutImage.setIcon(scoutIcon);
    scoutImage.setBounds(20, 120, 128, 72);
    myPanel.add(scoutImage);

    // Adding a nice sniper image
    JLabel sniperImage = new JLabel("");
    Image sniperScaled = ImageIO.read(new File("resources/ImageRedSniper.png"))
        .getScaledInstance(192, 108, Image.SCALE_DEFAULT);
    ImageIcon sniperIcon = new ImageIcon(sniperScaled);
    sniperImage.setIcon(sniperIcon);
    sniperImage.setBounds(110, 220, 192, 108);
    myPanel.add(sniperImage);

    // adding a nice tank image
    JLabel tankImage = new JLabel("");
    Image tankScaled = ImageIO.read(new File("resources/ImageGreenTank.png")).getScaledInstance(256,
        144, Image.SCALE_DEFAULT);
    ImageIcon tankIcon = new ImageIcon(tankScaled);
    tankImage.setIcon(tankIcon);
    tankImage.setBounds(180, 80, 256, 144);
    myPanel.add(tankImage);

    // Font used for the buttons
    Font buttonFont = new Font("Sarif", Font.BOLD, 18);

    // Set up the single player button
    JButton spButton = new JButton("Single Player");
    spButton.setBounds(435, 70, 187, 45);
    spButton.setFont(buttonFont);
    // Add an action listener to the button
    // When the button is pressed, call the singlePlayerClick() method
    spButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        singleplayerClick();
      }
    });
    myPanel.add(spButton);

    // Set up the multiplayer button
    JButton mpButton = new JButton("Multiplayer");
    mpButton.setBounds(435, 130, 187, 45);
    mpButton.setFont(buttonFont);
    // Add an action listener to the button
    // when the button is pressed, call the multiplayerClick() method
    mpButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        multiplayerClick();
      }
    });
    myPanel.add(mpButton);

    // Set up the quit (goback) button
    JButton quitButton = new JButton("Quit");
    quitButton.setBounds(435, 250, 187, 45);
    quitButton.setFont(buttonFont);
    // add an action listener to the button
    // when the button is pressed, call the goBackClick() method
    quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goBackClick();
      }
    });
    myPanel.add(quitButton);

    // Setting up the rules Button
    JButton RulesButton = new JButton("Rules");
    RulesButton.setBounds(435, 190, 187, 45);
    RulesButton.setFont(buttonFont);
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
   * A method that is called when the single player button is clicked
   */
  private void singleplayerClick() {
    this.addToStack(MenuFactory.getSPMenu());
  }

  /**
   * A method that is called when the multiplayer button is clicked
   */
  private void multiplayerClick() {
    this.addToStack(MenuFactory.getMPMenu());
  }

  /**
   * A method that is called when the rules button is clicked
   */
  private void rulesClick() {
    this.addToStack(MenuFactory.getRules());
  }

  /**
   * Method is used for testing Note: the method is used for testing but this will simply test the
   * drawing of the menus as of right now.
   * 
   * @param args Unused
   */
  public static void main(String args[]) {
    JFrame testFrame = new JFrame();

    testFrame.setTitle("Testing Drawing the Main menu");
    testFrame.setResizable(false);
    testFrame.setSize(MenuController.WIDTH, MenuController.HEIGHT);
    MainMenu testPanel = new MainMenu();
    testFrame.getContentPane().add(testPanel.getMenuPanel());
    testFrame.setLocation((int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
        (int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testFrame.setVisible(true);
  }
}
