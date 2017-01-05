package menuSystem;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class used for testing the menuController
 * 
 * @author Bryton
 *
 */
public class MockMenuTwo extends Menu {

  public MockMenuTwo() {
    super();
    try {
      menuImage = ImageIO.read(new File("resources/Background.png"));
    } catch (IOException e) {
      System.out.println("MockMenuTwo image not found");
    }
    setupMenu();
  }

  private void setupMenu() {
    myPanel = new JPanel();
    JLabel myLabel = new JLabel();

    Image scaledImage = menuImage.getScaledInstance(640, 360, Image.SCALE_DEFAULT);
    ImageIcon icon = new ImageIcon(scaledImage);

    myLabel.setIcon(icon);

    myPanel.add(myLabel);
    myPanel.repaint();
  }

}
