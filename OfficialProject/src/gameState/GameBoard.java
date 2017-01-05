package gameState;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import units.Unit;
/** 
 * Represent the game board
 */
public class GameBoard {
  /**
   * 2d array storing the hexes according to row and col, if there is no hex, it is NULL
   */
  private GameBoardHex[][] myHexes;
  /** size of the board */
  protected int boardSize;

  /**
   * Creates the GameBoard for the game
   * 
   * @param newBoardSize The size of the gameboard that will be created
   */
  public GameBoard(int newBoardSize) {
    this.boardSize = newBoardSize;
    myHexes = new GameBoardHex[2 * boardSize - 1][2 * boardSize - 1];
    for (int i = -boardSize + 1; i < boardSize; i++) {
      for (int j = -boardSize + 1; j < boardSize; j++) {
        if (i + j > -boardSize && i + j < boardSize) // getting rid of
          // all those,
          // that doesn't
          // belong in
          // on the board
          myHexes[j + boardSize - 1][i + boardSize - 1] = new GameBoardHex(new Position(i, j));
      }
    }
  }

  /**
   * Returns the hex at the wanted position
   * 
   * @param i column at which hex wanted
   * @param j row at which hex wanted
   * @return the hex at jth row and ith column
   */
  public GameBoardHex getHexAtIndex(int i, int j) {
    if (!isValidIndex(j, i)) {
      return null;
    }
    return myHexes[j][i];
  }

  /**
   * Check is the index in parameter is valid or not
   * 
   * @param i row index
   * @param j coloumn index
   * @return true if i, j is the valid index
   */
  private boolean isValidIndex(int i, int j) {
    if ((i < myHexes.length && i >= 0) && (j < myHexes.length && j >= 0)) {
      return myHexes[j][i] != null;
    } else
      return false;

  }

  /**
   * get the gameBoardHex from position
   * 
   * @param p position whose hex is wanted
   * @return the GameBoardHex at Position p
   * 
   */
  public GameBoardHex getHexFromPosition(Position p) {
    int xIndex = p.getX() + boardSize - 1;
    int zIndex = p.getZ() + boardSize - 1;
    return getHexAtIndex(xIndex, zIndex);
  }

  /**
   * get hex using pixel position
   * 
   * @param x: x position of the pixel
   * @param y: y position of the pixel
   * @param translateX: translatedX
   * @param translateY: translatedY
   * @param hexSize: size of the hex
   * @return the hex at pixel, if any otherwise null
   * 
   */
  public GameBoardHex getHexUsingPixels(int x, int y, int translateX, int translateY, int hexSize) {
    x -= translateX;
    y -= translateY;
    int bSize = this.boardSize;
    int hexX = (int) Math.round((x * Math.sqrt(3) / 3 - y / 3) / hexSize) + bSize - 1;
    int hexY = (int) Math.round((y * (2.0 / 3)) / hexSize) + bSize - 1;
    if (hexX < 2 * bSize - 1 && hexY < 2 * bSize - 1 && hexX >= 0 && hexY >= 0) {
      return getHexAtIndex(hexX, hexY);
    } else {
      return null;
    }
  }

  /**
   * 
   * @param p position
   * @return the index in representing the position myHexes
   **/
  private Point positionToIndex(Position p) {
    return new Point(p.getX() + this.boardSize - 1, p.getZ() + this.boardSize - 1);
  }

  /**
   * get all the hex which are in range range
   * 
   * @param pos position around which range required
   * @param range range required
   * @return ArrayList of all Position within range
   */
  public ArrayList<Position> getRangeOfHex(Position pos, int range) {

    ArrayList<Position> p = new ArrayList<Position>();
    for (int i = -range; i <= range; i++) {
      for (int j = Math.max(-range, -i - range); j <= Math.min(range, range - i); j++) {
        int k = -i - j;
        Position hexPosition =
            pos.getCubicalPosition().addCubicalPosition(new CubicalPosition(i, j, k)).getPosition();
        Point index = this.positionToIndex(hexPosition);
        if (index.getX() >= 0 && index.getY() >= 0 && index.getX() < 2 * boardSize - 1
            && index.getY() < 2 * boardSize - 1
            && this.myHexes[(int) index.getX()][(int) index.getY()] != null) {
          p.add(hexPosition);
        }
      }
    }
    return p;
  }

  /**
   * Highlights the range from a Position
   * 
   * @param pos The starting Position that will get highlighted
   * @param range The range that is going to be highlighted
   */
  public void highlightRange(Position pos, int range) {

    ArrayList<Position> highlightedPos = getRangeOfHex(pos, range);
    for (Position p : highlightedPos) {
      this.getHexFromPosition(p).highlight();
    }
  }

  /**
   * Return the position relative to the absolute direction of the system
   * 
   * @param p : Position of the Unit
   * @param radius: radius of the Unit
   * @param dir: absolute direction
   * @return the Number of hexes around the position
   */
  public HashMap<Integer, Position> getHexNumbersRing(Position p, int radius, int dir) {
    ArrayList<Position> points = new ArrayList<Position>();
    CubicalPosition cube = p.getCubicalPosition()
        .addCubicalPosition(CubicalPosition.direction(1).scaleCubicalPosition(radius));

    for (int i = 5; i >= 0; i--) {
      for (int j = 0; j < radius; j++) {
        @SuppressWarnings("unused")
        Point index = this.positionToIndex(cube.getPosition());
        if (!cube.getPosition().equals(p)) {
          points.add(cube.getPosition());
        }
        cube = cube.neighbourInDirection(i);
      }
    }
    Collections.rotate(points, (dir - 1) * radius - 1);
    Collections.reverse(points);

    HashMap<Integer, Position> hp = new HashMap<Integer, Position>();
    for (int i = 0; i < points.size(); i++) {
      if (points.get(i) != null && getHexFromPosition(points.get(i)) != null) {
        hp.put(i, points.get(i));
      }
    }
    return hp;

  }

  /**
   * remove the highlight from all the hexes
   */
  public void resetHighlight() {
    for (int i = 0; i < this.myHexes.length; i++) {
      for (int j = 0; j < this.myHexes.length; j++) {
        if (this.myHexes[i][j] != null) {
          this.myHexes[i][j].unhighlight();
        }
      }
    }
  }

  /**
   * highlight all the hexes
   */
  public void setHighlightToBoard() {
    for (int i = 0; i < this.myHexes.length; i++) {
      for (int j = 0; j < this.myHexes.length; j++) {
        if (this.myHexes[i][j] != null) {
          this.myHexes[i][j].highlight();
        }
      }
    }
  }

  /**
   * Return the position relative to the absolute direction of the system
   * @param pOfUnit position of the unit
   * @param range range of the unit 
   * @param absoluteDir direction of the unit
   * @param robotDir direction for which you want hex
   * @return the hex from robot number, returns null if 
   */
  public GameBoardHex getHexOfUnitFromRobotNumber(Position pOfUnit, int range, int absoluteDir,
      int robotDir) {
    HashMap<Integer, Position> hp = getHexNumbersRing(pOfUnit, range, absoluteDir);
    if (hp.get(robotDir) != null) {
      return this.getHexFromPosition(hp.get(robotDir));
    }
    return null;
  }

  /**
   * Perform check as mentioned in the forth interpreter
   * 
   * @param me The Unit performing identify
   * @param target The unit on which identify is performed on
   * @return IdentifyClass with details of Identify operation
   */
  public IdentifyClass forthIdentify(Unit me, Unit target) {
    IdentifyClass identifyResult = new IdentifyClass();
    int minDistance = me.getPosition().getTileDistance(target.getPosition());
    HashMap<Integer, Position> map =
        this.getHexNumbersRing(me.getPosition(), minDistance, me.getDirection());
    for (HashMap.Entry<Integer, Position> entry : map.entrySet()) {
      Position p = entry.getValue();
      GameBoardHex hex = this.getHexFromPosition(p);
      ArrayList<Unit> units = hex.getUnits();

      for (Unit unit : units) {
        if (unit.equals(target)) {
          identifyResult.direction = entry.getKey();
          break;
        }
      }
      if (identifyResult.direction != -1) {
        break;
      }
    }
    if (identifyResult.direction == -1) {
      identifyResult.direction = 0;
    }
    identifyResult.colour = target.getTeamName().getName();
    identifyResult.range = target.getRange();
    identifyResult.remainingHealth = target.getRemainingHealth();
    return identifyResult;

  }

  /**
   * @param p position from where the Scan is performed
   * @param range range until where scan is performed
   * @return Perform the scan as described in forth interpreter description,
   */
  public int forthScan(Position p, int range) {
    ArrayList<Position> positions = getRangeOfHex(p, range);
    int count = 0;
    for (int i = 0; i < positions.size(); i++) {
      GameBoardHex hex = this.getHexFromPosition(positions.get(i));
      for (Unit unit : hex.getUnits()) {
        if (unit.alive) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Perform check as mentioned in the forth interpreter
   * 
   * @param myUnit The unit who is performing the forth
   * @param relativeDir the direction where you want to perform the check
   * @return the approriate String as Expected by interpreter
   */
  public String forthCheck(Unit myUnit, int relativeDir) {

    int absoluteDir = myUnit.getDirection();
    GameBoardHex hex =
        getHexOfUnitFromRobotNumber(myUnit.getPosition(), 1, absoluteDir, relativeDir);
    if (null == hex) {
      return "OUT OF BOUNDS";
    }
    ArrayList<Unit> units = hex.getUnits();
    String found = "EMPTY";
    for (Unit unit : units) {
      if (unit.alive) {
        found = "OCCUPIED";
        break;
      }
    }
    return found;
  }


  /**
   * Return the arrayList of units scanned
   * 
   * @param p position where scan is performed from
   * @param range of the unit performing the scan
   * @return Perform the scan as described in forth interpreter description, and return the scanned
   *         items.
   */
  public ArrayList<Unit> forthScannedUnits(Position p, int range) {
    ArrayList<Position> positions = getRangeOfHex(p, range);
    ArrayList<Unit> units = new ArrayList<Unit>();
    for (int i = 0; i < positions.size(); i++) {
      GameBoardHex hex = this.getHexFromPosition(positions.get(i));
      for (Unit unit : hex.getUnits()) {
        if (unit.alive) {
          units.add(unit);
        }
      }
    }
    return units;
  }

  /**
   * @return the board size
   */
  public int getBoardSize() {
    return this.boardSize;
  }

  /**
   * Board testing was done using drawing things on the actual game board by visually checking the
   * data.
   * 
   * @param args not used
   */
  public static void main(String[] args) {
    int bSize = 5;
    int hexSize = 40;
    GameBoard g = new GameBoard(bSize);
    JFrame frame = new JFrame();
    frame.setTitle("DrawPoly");
    frame.setSize(1000, 1000);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container contentPane = frame.getContentPane();
    System.out.println(g.getRangeOfHex(new Position(0, -4), 1));
    System.out.println(g.getRangeOfHex(new Position(0, 0), 1));
    System.out.println(g.getRangeOfHex(new Position(0, 4), 1));
    System.out.println(g.getRangeOfHex(new Position(4, 0), 1));
    System.out.println(g.getRangeOfHex(new Position(-1, 4), 1));
    System.out.println(g.getRangeOfHex(new Position(0, -3), 1));
    System.out.println(g.getHexOfUnitFromRobotNumber(new Position(0, 0), 1, 0, 1).getPosition());

    int tX = 350;
    int tY = 350;

    JPanel panel = new JPanel() {

      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        g1.translate(tX, tY);
        double xCenter = 0;
        double yCenter = 0;
        int xPoint;
        int yPoint;
        double radius = hexSize;
        Polygon hexTile = new Polygon();
        LinkedList<Polygon> listOfTiles = new LinkedList<Polygon>();

        // Top Half + middle
        for (int j = 0; j < 2 * bSize - 1; j++) {
          for (int k = 0; k < 2 * bSize - 1; k++) {
            // the layer
            if (g.myHexes[j][k] != null) {
              xPoint = 0;
              yPoint = 0;
              hexTile = new Polygon();
              xCenter = g.myHexes[k][j].getPoint(hexSize).getX();
              yCenter = g.myHexes[k][j].getPoint(hexSize).getY();

              for (int i = 0; i < 6; i++) {
                xPoint =
                    (int) Math.round((xCenter + radius * Math.cos(((2 * i + 1) * Math.PI / 6))));
                yPoint =
                    (int) Math.round((yCenter + radius * Math.sin(((2 * i + 1) * Math.PI / 6))));
                hexTile.addPoint(xPoint, yPoint);
                listOfTiles.add(hexTile);

              }

              if (g.myHexes[k][j].isHighlighted()) {
                g1.drawPolygon(hexTile);

                if (g.myHexes[k][j].text != null)
                  g1.drawString(g.myHexes[k][j].text, (int) Math.round(xCenter - 3),
                      (int) Math.round(yCenter));
              } else {
                g1.drawPolygon(hexTile);
                g1.drawString(k - bSize + 1 + ":" + (j - bSize + 1), (int) Math.round(xCenter - 3),
                    (int) Math.round(yCenter));
              }
            }
          }
        }
      }
    };

    panel.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent e) {}

      @Override
      public void mouseMoved(MouseEvent e) {
        GameBoardHex h = null;
        if ((h = g.getHexUsingPixels(e.getX(), e.getY(), tX, tY, hexSize)) != null) {
          g.resetHighlight();
          g.highlightRangeForRingTest(h.getPosition(), 1, 0);
          g.highlightRangeForRingTest(h.getPosition(), 2, 0);

          panel.repaint();
        }
      }
    });
    contentPane.add(panel);
    frame.setVisible(true);
  }


  protected void highlightRangeForRingTest(Position position, int radius, int dir) {
    HashMap<Integer, Position> highlightedPos = getHexNumbersRing(position, radius, dir);
    highlightedPos.forEach((k, v) -> {
      GameBoardHex hex = this.getHexFromPosition(v);
      hex.highlight();
      hex.text = k + "";
    });
  }

  protected void highlightRangeForRangeTest(Position position, int radius, int dir) {
    ArrayList<Position> highlightedPos = getRangeOfHex(position, radius);
    highlightedPos.forEach((p) -> {
      GameBoardHex hex = this.getHexFromPosition(p);
      hex.highlight();
    });
  }


}
