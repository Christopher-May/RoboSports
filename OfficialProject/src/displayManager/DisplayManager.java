package displayManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameManager.DisplayObservor;
import gameState.GameBoard;
import gameState.GameBoardHex;
import player.Player;
import units.Unit;
/**
 * 
 * Display manager class is used to display the game board state
 *
 */
public class DisplayManager implements MouseMotionListener, MouseListener, KeyListener {

  // variables
  /** boardPanel is used to represent the entire game screen. */
  private JPanel boardPanel;

  private JPanel gameBoardPanel;

  /**
   * boardFrame is the frame used to hold the entire game screen and all of its components.
   */
  private JFrame boardFrame;

  /** Used to display information about units on the board */
  private JLabel unitInfoPanel;

  /** Used to display controls */
  private JLabel rulesPanel;

  /** turnTimer represents the JLabel used to display the current turn time */
  private SecondsTimer turnTimer;
  private JLabel turnTimerLabel;

  /** gameTimer represents the JLabel used to display the current game time */
  private SecondsTimer gameTimer;
  private JLabel gameTimerLabel;

  /** The label to prevent cheating and notify a player that it is their turn */
  private JLabel countDownLabel;

  /** The height and width of the gameboard */
  private final int width = 960;
  private final int height = 662;

  int translateX;
  int translateY;

  /**
   * Two panels that are used to display extra information They are fields of the class because
   * other methods need to alter them
   */
  private JLabel backgroundLabel;
  // private JPanel rulesPanel;

  // Constants
  /** board x for large size board position */
  public static final int TRANSLATE_X_7 = 550;
  /** board x for small size board position */
  public static final int TRANSLATE_X_5 = 550;
  /** board y for large and small size board position */
  public static final int TRANSLATE_Y = 325;
  /** The hex size on the game board */
  public static final int HEX_SIZE = 30;

  private Image redBoom;
  private Image orangeBoom;
  private Image yellowBoom;
  private Image greenBoom;
  private Image blueBoom;
  private Image purpleBoom;
  private Image manyBoom;
  private Image highlightUnit;

  // Grass Texture
  private BufferedImage grass;
  private TexturePaint grasstp;

  /**
   * Constructor for the DisplayManager class, currently empty remember to update this comment when
   * the class is created.
   */
  private DisplayObservor observors;

  public DisplayManager(DisplayObservor observor) {
    observors = observor;
    try {
      initializeFrame();
    } catch (IOException e) {
      System.out.println("Background.jpg file missing from resources");
    }
    initializeImages();
  }

  private void initializeImages() {
    int boomSize = HEX_SIZE * 2;
    try {
      this.redBoom = ImageIO.read(new File("resources/ImageRedBoom.png"))
          .getScaledInstance(boomSize, boomSize, Image.SCALE_DEFAULT);
      this.orangeBoom = ImageIO.read(new File("resources/ImageOrangeBoom.png"))
          .getScaledInstance(boomSize, boomSize, Image.SCALE_DEFAULT);
      this.yellowBoom = ImageIO.read(new File("resources/ImageYellowBoom.png"))
          .getScaledInstance(boomSize, boomSize, Image.SCALE_DEFAULT);
      this.greenBoom = ImageIO.read(new File("resources/ImageGreenBoom.png"))
          .getScaledInstance(boomSize, boomSize, Image.SCALE_DEFAULT);
      this.blueBoom = ImageIO.read(new File("resources/ImageBlueBoom.png"))
          .getScaledInstance(boomSize, boomSize, Image.SCALE_DEFAULT);
      this.purpleBoom = ImageIO.read(new File("resources/ImagePurpleBoom.png"))
          .getScaledInstance(boomSize, boomSize, Image.SCALE_DEFAULT);
      this.manyBoom = ImageIO.read(new File("resources/ImageManyBoom.png"))
          .getScaledInstance(boomSize, boomSize, Image.SCALE_DEFAULT);
      this.highlightUnit = ImageIO.read(new File("resources/ImageUnitHighlight.png"))
          .getScaledInstance(boomSize, boomSize, Image.SCALE_DEFAULT);
      this.grass = ImageIO.read(new File("resources/ImageGrassy.png"));
      this.grasstp =
          new TexturePaint(grass, new Rectangle2D.Double(HEX_SIZE, HEX_SIZE, HEX_SIZE, HEX_SIZE));
    } catch (IOException e) {
      System.out.println("One of the Boom animations is missing!");
    }
  }

  /**
   * Helper method for the constructor to initialize the fame.
   * 
   * @throws IOException thrown then the Background.jpg image is missing from the resources folder
   */
  private void initializeFrame() throws IOException {

    // Code to set up the frame settings
    boardFrame = new JFrame();
    boardFrame.setTitle("Robosports: Battle of Tanks");
    boardFrame.setResizable(false);
    boardFrame.setSize(width, height);

    // Code to set up the default panel settings
    boardPanel = new JPanel();
    boardPanel.setLayout(null);

    // Set up the unit info display panel
    unitInfoPanel = new JLabel();
    unitInfoPanel.setBounds(10, 60, 300, 300);
    unitInfoPanel.setFont(new Font("Sarif", Font.PLAIN, 14));
    boardPanel.add(unitInfoPanel);

    // Set up rules
    rulesPanel = new JLabel();
    rulesPanel.setBounds(10, 460, 300, 300);
    rulesPanel.setFont(new Font("Sarif", Font.BOLD, 16));
    setRulesPanel();
    boardPanel.add(rulesPanel);

    // Code to set up the overall game timer
    gameTimer = new SecondsTimer(130, 10, "Total Game Time");
    gameTimerLabel = gameTimer.getLabel();
    boardPanel.add(gameTimerLabel);

    // Code to set up the turn timer
    turnTimer = new SecondsTimer(130, 600, "Current Round Time");
    turnTimerLabel = turnTimer.getLabel();
    boardPanel.add(turnTimerLabel);

    // Set up the quit (goback) button
    JButton quitButton = new JButton("Quit Game");
    quitButton.setBounds(750, 580, 187, 45);
    quitButton.setFont(new Font("Sarif", Font.BOLD, 16));
    // add an action listener to the button
    // when the button is pressed, call the goBackClick() method
    quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        quitButtonClick();
      }
    });
    boardPanel.add(quitButton);

    // // Setting up the rules Button
    // JButton RulesButton = new JButton("Rules");
    // RulesButton.setBounds(26, 580, 187, 45);
    // RulesButton.setFont(new Font("Sarif", Font.BOLD, 16));
    // // Adding an action listener to the rules button
    // // When the button is pressed, call the rulesClick() method
    // RulesButton.addActionListener(new ActionListener() {
    // public void actionPerformed(ActionEvent e) {
    // rulesButtonClick();
    // }
    // });
    // boardPanel.add(RulesButton);

    // Setting up the background image,
    backgroundLabel = new JLabel("");
    Image scaledImage = ImageIO.read(new File("resources/ImageGameBackground.jpg"))
        .getScaledInstance(960, 640, Image.SCALE_DEFAULT);
    ImageIcon icon = new ImageIcon(scaledImage);
    backgroundLabel.setIcon(icon);
    backgroundLabel.setBounds(0, 0, width, height - 22);
    boardPanel.add(backgroundLabel);
    boardPanel.repaint();

    boardFrame.setLocation((int) (0.1 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
        (int) (0.1 * Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    boardFrame.add(boardPanel);

    turnTimer.startTimer();
    gameTimer.startTimer();
    boardFrame.setVisible(true);
  }

  /**
   * a method used to update the current display this method is used to update the non game features
   * such as the game and turn timers. fill in this comment when updateDisplay is coded.
   * 
   * @param currUnit
   * @param units
   * @param hexsize
   * @param myGameState
   */

  /**
   * a method used to update the turn time called from updateDisplay when appropriate fill in this
   * comment when this method is coded.
   */
  public void updateTurnTime() {
    turnTimer.resetTimer();
  }

  /**
   * Simply close the application when we are done possible restart the application?
   */
  private void quitButtonClick() {
    System.exit(0);
  }

  private void setRulesPanel() {
    String rules = "<html><body>";
    rules += "*** RULES ***<br>";
    rules += "CLICK to shoot<br>";
    rules += "'W' to move<br>";
    rules += "'A' to turn left<br>";
    rules += "'D' to turn right<br>";
    rules += "'N' to end turn<br>";
    rules += "</body></html>";
    this.rulesPanel.setText(rules);
    this.rulesPanel.setSize(rulesPanel.getPreferredSize());
  }

  /**
   * Draw a highlighted ring around the units on the hex.
   * 
   * @param at atransformation required
   * @param g3 the graphics
   */
  private void highlightUnit(AffineTransform at, Graphics2D g3) {
    g3.drawImage(highlightUnit, at, null);
  }

  /**
   * Draws the game board on the board panel with correct range
   * 
   * @param gameState gameBoard Information
   * @param g3 reference to graphics object
   */
  private void drawBoardOnPanel(GameBoard gameState, Graphics2D g3) {

    double xCenter = 0;
    double yCenter = 0;
    int xPoint;
    int yPoint;
    g3.translate(translateX, translateY);
    Polygon hexTile = new Polygon();
    LinkedList<Polygon> listOfTiles = new LinkedList<Polygon>();
    // Top Half + middle
    for (int j = 0; j < 2 * gameState.getBoardSize() - 1; j++) {
      for (int k = 0; k < 2 * gameState.getBoardSize() - 1; k++) {
        // the layer
        if (gameState.getHexAtIndex(j, k) != null) {
          xPoint = 0;
          yPoint = 0;
          hexTile = new Polygon();
          GameBoardHex hex = gameState.getHexAtIndex(j, k);
          xCenter = hex.getPoint(HEX_SIZE).getX();
          yCenter = hex.getPoint(HEX_SIZE).getY();
          for (int i = 0; i < 6; i++) {// draws each line
            xPoint = (int) Math.round((xCenter + HEX_SIZE * Math.cos(((2 * i + 1) * Math.PI / 6))));
            yPoint = (int) Math.round((yCenter + HEX_SIZE * Math.sin(((2 * i + 1) * Math.PI / 6))));
            hexTile.addPoint(xPoint, yPoint);
            listOfTiles.add(hexTile);
          }
          g3.setStroke(new BasicStroke(2));
          g3.setPaint(Color.darkGray);
          if (hex.isHighlighted()) {
            g3.drawPolygon(hexTile);
            g3.setPaint(grasstp);
            g3.fillPolygon(hexTile);
            ArrayList<Unit> aliveUnits = hex.getAliveUnits();
            ArrayList<Unit> deadUnits = hex.getDeadUnits();
            // If there are alive units on the tile, we will not
            // draw any booms underneath or overtop of them
            if (aliveUnits.size() > 0) {
              for (Unit u : aliveUnits) {
                AffineTransform atUnit = new AffineTransform();
                AffineTransform atHighlight = new AffineTransform();
                atUnit.translate((int) xCenter - HEX_SIZE / 2, (int) yCenter - HEX_SIZE / 2);
                atHighlight.translate((int) xCenter - HEX_SIZE - 1, (int) yCenter - HEX_SIZE);
                // 3. do the actual rotation
                atUnit.rotate(Math.PI / 3 * (u.getDirection() - 1), u.getImage().getWidth(null) / 2,
                    u.getImage().getHeight(null) / 2);
                g3.drawImage(u.getImage(), atUnit, null);
                if (u.isHighlighted) {
                  highlightUnit(atHighlight, g3);
                }
              }
            }
            // If there are no alive units but there is one dead unit,
            // we should draw a boom of that dead units color
            else if (deadUnits.size() == 1) { // no alive units, only dead
              // ones
              AffineTransform at = new AffineTransform();
              // 4. translate it to the center of the component
              at.translate((int) xCenter - HEX_SIZE, (int) yCenter - HEX_SIZE);
              drawBoom(deadUnits, at, g3);
            } else if (deadUnits.size() > 1) {
              AffineTransform at = new AffineTransform();
              // 4. translate it to the center of the component
              at.translate((int) xCenter - HEX_SIZE, (int) yCenter - HEX_SIZE);
              drawBoom(null, at, g3);
            }
          } else { // everything else
            g3.fillPolygon(hexTile);

          }
        }
      }
    }
  }



  /**
   * Large method to draw the game board to its panel
   * 
   * @param gameState current game state of the board
   * @param playerUnits player units
   * @param currUnit current unit of the player
   * @param turnScreenOn if screen should be turned on.
   */
  public synchronized void createDrawHexBoardPanel(GameBoard gameState, ArrayList<Unit> playerUnits,
      Unit currUnit, boolean turnScreenOn) {

    if (gameState.getBoardSize() == 5) {
      translateX = TRANSLATE_X_5;
    } else {
      translateX = TRANSLATE_X_7;
    }
    translateY = TRANSLATE_Y;

    if (!turnScreenOn) {
      for (Unit u : playerUnits) {
        if (u.alive) {
          gameState.highlightRange(u.myPosition, u.getRange());
        }
      }
    }

    gameBoardPanel = new JPanel() {
      private static final long serialVersionUID = 1L;

      public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g3 = (Graphics2D) g1;
        drawBoardOnPanel(gameState, g3);
      }
    };

    gameBoardPanel.setBounds(0, 0, 2 * HEX_SIZE * (2 * gameState.getBoardSize() - 1) + translateX,
        2 * HEX_SIZE * (2 * gameState.getBoardSize() - 1) + translateY);
    gameBoardPanel.setLayout(null);
    gameBoardPanel.setFocusable(true);
    gameBoardPanel.addMouseListener(this);
    gameBoardPanel.addMouseMotionListener(this);
    gameBoardPanel.addKeyListener(this);
    gameBoardPanel.setBackground(null);
    gameBoardPanel.setOpaque(false);
    gameBoardPanel.repaint();
    boardPanel.remove(backgroundLabel);
    boardPanel.add(gameBoardPanel);
    boardFrame.revalidate();
    boardFrame.repaint();
    boardPanel.add(backgroundLabel);
    boardFrame.setVisible(true);
  }

  /**
   * Draw a boom graphic with on g3. If there is only one unit, draw a boom of that Units team's
   * colour. If there is more than one unit, draw a multi-coloured boom.
   * 
   * @param units A list of dead units that might be drawn as booms
   * @param at The transformation of the boom
   * @param g3 How the graphics are drawn.
   */
  private synchronized void drawBoom(ArrayList<Unit> units, AffineTransform at, Graphics2D g3) {
    if (null != units) {
      if (1 < units.size()) {
        g3.drawImage(manyBoom, at, null);
      } else {
        Unit u = units.get(0);
        switch (u.getTeamName().getName()) {
          case "RED":
            g3.drawImage(redBoom, at, null);
            break;
          case "ORANGE":
            g3.drawImage(orangeBoom, at, null);
            break;
          case "YELLOW":
            g3.drawImage(yellowBoom, at, null);
            break;
          case "GREEN":
            g3.drawImage(greenBoom, at, null);
            break;
          case "BLUE":
            g3.drawImage(blueBoom, at, null);
            break;
          case "PURPLE":
            g3.drawImage(purpleBoom, at, null);
            break;
          default:
            g3.drawImage(manyBoom, at, null);
            break;
        }
      }
    } else {
      g3.drawImage(manyBoom, at, null);
    }
  }

  /**
   * Update the display when a player has won the game!
   * 
   * @param winningPlayer the player who one, -1 if it was a draw.
   * @param gamePlayers All the players in the game
   */
  public void endGame(int winningPlayer, ArrayList<Player> gamePlayers) {

    boardFrame.remove(boardPanel);

    boardPanel = new JPanel();
    boardPanel.setLayout(null);

    JLabel text;

    if (-1 == winningPlayer) {
      Color draw = Color.black;
      text = new JLabel("Draw!");
      text.setForeground(draw);
      text.setBounds((this.width / 4) - 150, (this.height / 2) - 40, 300, 60);
    } else {
      Color winnerColour =
          gamePlayers.get(winningPlayer).getUnits().get(0).getTeamName().getColour();
      text = new JLabel("Player " + winningPlayer + " Victory!");
      text.setForeground(winnerColour);
      text.setBounds((this.width / 4) - 200, (this.height / 2) - 40, 300, 60);
    }

    JButton quitButton = new JButton("Quit Game");
    quitButton.setBounds(this.width / 2 - 50, this.height / 2 - 20, 100, 40);
    quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    text.setFont(new Font("Sarif", Font.BOLD, 30));
    boardPanel.add(text);
    boardFrame.add(boardPanel);
    boardFrame.repaint();

  }

  /**
   * Display the information associated with the units over top of the game board.
   * 
   * @param units Units whose information needs to be shown
   */
  public void showUnitInfo(ArrayList<Unit> units) {
    if (null == units) {
      throw new IllegalArgumentException("units cannot be null");
    }
    if (0 >= units.size()) {
      throw new IllegalArgumentException("Only show the information of the unit");
    }
    String unitInfo = "<html><body>";
    for (Unit u : units) {
      if (u.alive) {
        unitInfo += "TEAM: " + u.getTeamName().getName().toString() + "<br>";
        unitInfo += "UNIT: " + u.getType() + "<br>";
        unitInfo += "HP: " + u.getRemainingHealth() + "<br>";
        unitInfo += "******<br>";
      }
    }
    unitInfo += "</body></html>";
    this.unitInfoPanel.setText(unitInfo);
    this.unitInfoPanel.setSize(unitInfoPanel.getPreferredSize());
  }

  /**
   * Method to add a message and spawn a turn timer to tell the next player that their turn is
   * starting in 5 seconds
   * 
   * @param playerColour The colour of the player whos turn is next
   */
  public void notifyPlayerTurn(Color playerColour) {
    // Initialization of the label
    countDownLabel = new JLabel("It is your turn");
    countDownLabel.setBounds(10, (height - 22) / 2, 340, 40);
    countDownLabel.setFont(new Font("Sarif", Font.BOLD, 28));
    countDownLabel.setForeground(playerColour);
    boardPanel.add(countDownLabel);
    boardPanel.repaint();
  }

  /**
   * Method to remove that message after the countdown hit 0 should only be called from the
   * notifyPlayerTurn method or maybe a button
   */
  public void endNotification() {
    boardPanel.remove(countDownLabel);
    boardPanel.repaint();
  }

  /**
   * Notify the GameManager observer that the mouse was clicked at the pixel position of the mouse.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    this.observors.mouseClicked(e.getX(), e.getY());
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void mouseDragged(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {
    this.observors.mouseMoved(e);
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {
    this.observors.handleKeyEvent(Character.toLowerCase((char) e.getKeyChar()));
  }
}
