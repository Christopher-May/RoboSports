package gameMaster;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameState.Position;

/**
 * 
 * Enumeration created for Team colour, It helps to get initial position, initial direction and
 * images
 *
 */
public enum TeamColour {

  RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE;

  /**
   * Gets the teams correct starting position on the GameBoard
   * 
   * @param boardSize the board size of the GameBoard
   * @return The correct starting position of the team
   */
  public Position getInitialPosition(int boardSize) {

    switch (this) {
      case RED:
        return new Position(-boardSize + 1, 0);
      case ORANGE:
        return new Position(0, -boardSize + 1);
      case YELLOW:
        return new Position(boardSize - 1, -boardSize + 1);
      case GREEN:
        return new Position(boardSize - 1, 0);
      case BLUE:
        return new Position(0, boardSize - 1);
      case PURPLE:
        return new Position(-boardSize + 1, boardSize - 1);
      default:
        throw new IllegalArgumentException("Unrecognized color");
    }
  }

  /**
   * Gets the teams correct starting position on the GameBoard
   * 
   * @return The correct starting direction of the team
   */
  public int getInitialDirection() {

    switch (this) {
      case RED:
        return 1;
      case ORANGE:
        return 2;
      case YELLOW:
        return 3;
      case GREEN:
        return 4;
      case BLUE:
        return 5;
      case PURPLE:
        return 0;
      default:
        throw new IllegalArgumentException("Unrecognized color");
    }
  }

  /**
   * Gets the teams correct Scout image
   * 
   * @return the image of the team's Scout
   */
  public Image getScout() {
    try {
      switch (this) {
        case RED:
          return ImageIO.read(new File("resources/ImageRedScout.png"));
        case ORANGE:
          return ImageIO.read(new File("resources/ImageOrangeScout.png"));
        case YELLOW:
          return ImageIO.read(new File("resources/ImageYellowScout.png"));
        case GREEN:
          return ImageIO.read(new File("resources/ImageGreenScout.png"));
        case BLUE:
          return ImageIO.read(new File("resources/ImageBlueScout.png"));
        case PURPLE:
          return ImageIO.read(new File("resources/ImagePurpleScout.png"));
        default:
          throw new IllegalArgumentException("Unrecognized color");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("coudn't find image"); // change this to meaningful
      // exception
    }
  }

  /**
   * Gets the teams correct Tank image
   * 
   * @return the image of the team's Tank
   */
  public Image getTank() {
    try {
      switch (this) {
        case RED:
          return ImageIO.read(new File("resources/ImageRedTank.png"));
        case ORANGE:
          return ImageIO.read(new File("resources/ImageOrangeTank.png"));
        case YELLOW:
          return ImageIO.read(new File("resources/ImageYellowTank.png"));
        case GREEN:
          return ImageIO.read(new File("resources/ImageGreenTank.png"));
        case BLUE:
          return ImageIO.read(new File("resources/ImageBlueTank.png"));
        case PURPLE:
          return ImageIO.read(new File("resources/ImagePurpleTank.png"));
        default:
          throw new IllegalArgumentException("Unrecognized color");
      }
    } catch (IOException e) {
      throw new IllegalImageException("couldn't find image");
    }
  }

  /**
   * Gets the teams correct Sniper image
   * 
   * @return the image of the team's Sniper
   */
  public Image getSniper() {
    try {
      switch (this) {
        case RED:
          return ImageIO.read(new File("resources/ImageRedSniper.png"));
        case ORANGE:
          return ImageIO.read(new File("resources/ImageOrangeSniper.png"));
        case YELLOW:
          return ImageIO.read(new File("resources/ImageYellowSniper.png"));
        case GREEN:
          return ImageIO.read(new File("resources/ImageGreenSniper.png"));
        case BLUE:
          return ImageIO.read(new File("resources/ImageBlueSniper.png"));
        case PURPLE:
          return ImageIO.read(new File("resources/ImagePurpleSniper.png"));
        default:
          throw new IllegalArgumentException("Unrecognized color");
      }
    } catch (IOException e) {
      throw new IllegalImageException("coudn't find image");
    }
  }

  public Color getColour() {
    switch (this) {
      case RED:
        return Color.red;
      case ORANGE:
        return Color.orange;
      case YELLOW:
        return Color.yellow;
      case GREEN:
        return Color.green;
      case BLUE:
        return Color.blue;
      case PURPLE:
        return Color.pink;
      default:
        return Color.black;
    }
  }

  /**
   * Return a String in all caps describing the colour of this team.
   * 
   * @return Returns the string format of the colour, mainly used for the interpreter
   */
  public String getName() {
    switch (this) {
      case RED:
        return "RED";
      case ORANGE:
        return "ORANGE";
      case YELLOW:
        return "YELLOW";
      case GREEN:
        return "GREEN";
      case BLUE:
        return "BLUE";
      case PURPLE:
        return "PINK";
      default:
        return "BLACK";
    }
  }

}
