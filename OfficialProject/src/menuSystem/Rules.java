package menuSystem;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The code to draw and visually test the rules menu.
 * 
 * @author Bryton
 *
 */
public class Rules extends Menu {

  /**
   * Constructor for the rules menu
   */
  public Rules() {
    super();
    try {
      initializeMenu();
    } catch (IOException e) {
      System.out.println("Missing an image for one of the tanks");
    }
  }

  /**
   * Note: If we decide we want the menu system to be resizeable, we will have to change the pixel
   * positions to be precentage of screen size based, not static as they are now
   * 
   * @throws IOException throws this exception when there is a missing image
   */
  private void initializeMenu() throws IOException {
    // Sets up the initial panel with an absolute layout form
    myPanel = new JPanel();
    myPanel.setLayout(null);

    // Declaring some fonts that are used a lot in the following initializations
    Font textFont = new Font("Sarif", Font.BOLD, 16);
    Font rulesFont = new Font("Sarif", Font.BOLD, 14);

    // Setting up the goback button
    JButton goBackButton = new JButton("Go Back");
    goBackButton.setBounds(20, 320, 100, 35);
    // adding an action listener to the goback button
    // When the button is pressed, call the goBackClick method.
    goBackButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goBackClick();
      }
    });
    myPanel.add(goBackButton);

    // Tank icon and description
    // Positions all of the tank information labels in a column
    JLabel tankName = new JLabel("Tank");
    tankName.setFont(textFont);
    tankName.setBounds(110, 5, 60, 20);
    myPanel.add(tankName);

    JLabel tankImage = new JLabel("");
    Image tankScaled = ImageIO.read(new File("resources/ImageGreenTank.png")).getScaledInstance(128,
        72, Image.SCALE_DEFAULT);
    ImageIcon tankIcon = new ImageIcon(tankScaled);
    tankImage.setIcon(tankIcon);
    tankImage.setBounds(76, 25, 128, 72);
    myPanel.add(tankImage);

    JLabel tankHealth = new JLabel("HEALTH: 3");
    tankHealth.setFont(textFont);
    tankHealth.setBounds(100, 98, 120, 20);
    myPanel.add(tankHealth);

    JLabel tankAttack = new JLabel("ATTACK: 3");
    tankAttack.setFont(textFont);
    tankAttack.setBounds(100, 128, 100, 20);
    myPanel.add(tankAttack);

    JLabel tankMovement = new JLabel("MOVEMENT: 1");
    tankMovement.setFont(textFont);
    tankMovement.setBounds(100, 158, 120, 20);
    myPanel.add(tankMovement);

    JLabel tankRange = new JLabel("RANGE: 1");
    tankRange.setFont(textFont);
    tankRange.setBounds(100, 188, 80, 20);
    myPanel.add(tankRange);

    // Scout icon and description
    // Positions all of the scout information labels in a column
    JLabel scoutName = new JLabel("Scout");
    scoutName.setFont(textFont);
    scoutName.setBounds(288, 5, 60, 20);
    myPanel.add(scoutName);

    JLabel scoutImage = new JLabel("");
    Image scoutScaled = ImageIO.read(new File("resources/ImageBlueScout.png"))
        .getScaledInstance(128, 72, Image.SCALE_DEFAULT);
    ImageIcon scoutIcon = new ImageIcon(scoutScaled);
    scoutImage.setIcon(scoutIcon);
    scoutImage.setBounds(256, 25, 128, 72);
    myPanel.add(scoutImage);

    JLabel scoutHealth = new JLabel("HEALTH: 1");
    scoutHealth.setFont(textFont);
    scoutHealth.setBounds(278, 98, 120, 20);
    myPanel.add(scoutHealth);

    JLabel scoutAttack = new JLabel("ATTACK: 1");
    scoutAttack.setFont(textFont);
    scoutAttack.setBounds(278, 128, 100, 20);
    myPanel.add(scoutAttack);

    JLabel scoutMovement = new JLabel("MOVEMENT: 3");
    scoutMovement.setFont(textFont);
    scoutMovement.setBounds(278, 158, 120, 20);
    myPanel.add(scoutMovement);

    JLabel scoutRange = new JLabel("RANGE: 2");
    scoutRange.setFont(textFont);
    scoutRange.setBounds(278, 188, 80, 20);
    myPanel.add(scoutRange);

    // Sniper icon and description
    // Positions all of the sniper information labels in a column
    JLabel sniperName = new JLabel("Sniper");
    sniperName.setFont(textFont);
    sniperName.setBounds(465, 5, 70, 20);
    myPanel.add(sniperName);

    JLabel sniperImage = new JLabel("");
    Image sniperScaled = ImageIO.read(new File("resources/ImageRedSniper.png"))
        .getScaledInstance(128, 72, Image.SCALE_DEFAULT);
    ImageIcon sniperIcon = new ImageIcon(sniperScaled);
    sniperImage.setIcon(sniperIcon);
    sniperImage.setBounds(436, 25, 128, 72);
    myPanel.add(sniperImage);

    JLabel sniperHealth = new JLabel("HEALTH: 2");
    sniperHealth.setFont(textFont);
    sniperHealth.setBounds(455, 98, 120, 20);
    myPanel.add(sniperHealth);

    JLabel sniperAttack = new JLabel("ATTACK: 2");
    sniperAttack.setFont(textFont);
    sniperAttack.setBounds(455, 128, 100, 20);
    myPanel.add(sniperAttack);

    JLabel sniperMovement = new JLabel("MOVEMENT: 2");
    sniperMovement.setFont(textFont);
    sniperMovement.setBounds(455, 158, 120, 20);
    myPanel.add(sniperMovement);

    JLabel sniperRange = new JLabel("RANGE: 3");
    sniperRange.setFont(textFont);
    sniperRange.setBounds(455, 188, 80, 20);
    myPanel.add(sniperRange);

    // The labels representing the bottom bits of description
    JLabel rulesHeading = new JLabel("Rules");
    rulesHeading.setFont(textFont);
    rulesHeading.setBounds(298, 230, 80, 20);
    myPanel.add(rulesHeading);

    JLabel rulesOne = new JLabel(
        "Every round you play your robot with the highest movement" + " that has yet to play");
    rulesOne.setFont(rulesFont);
    rulesOne.setBounds(25, 255, 600, 20);
    myPanel.add(rulesOne);

    JLabel rulesTwo = new JLabel(
        "A play consists of moving, or shooting in any order" + " until you are done you're turn");
    rulesTwo.setFont(rulesFont);
    rulesTwo.setBounds(25, 280, 600, 20);
    myPanel.add(rulesTwo);

    JLabel rulesThree = new JLabel("You win when you're the only survivor");
    rulesThree.setFont(rulesFont);
    rulesThree.setBounds(180, 310, 300, 20);
    myPanel.add(rulesThree);

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
   * Method is used for testing Note: this method doesn't test full method functionality it is only
   * used to test that the menu draws properly To test full menu system functionality, run the
   * MenuController tests and click through the menu system.
   * 
   * @param args Unused
   */
  public static void main(String args[]) {
    JFrame testFrame = new JFrame();

    testFrame.setTitle("Testing Drawing the rules menu");
    testFrame.setResizable(false);
    testFrame.setSize(MenuController.WIDTH, MenuController.HEIGHT);
    Rules testPanel = new Rules();
    testFrame.getContentPane().add(testPanel.getMenuPanel());
    testFrame.setLocation((int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
        (int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testFrame.setVisible(true);
  }
}
