package experiment;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawSingleHex extends JPanel {
  public DrawSingleHex() {}

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setTitle("DrawPoly");
    frame.setSize(350, 250);
    Container contentPane = frame.getContentPane();
    JPanel j = new JPanel() {

      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        double xCenter = 0;
        double yCenter = 0;
        int xPoint;
        int yPoint;
        double radius = 25.0;
        int size = 5;
        Polygon hexTile = new Polygon();
        LinkedList<Polygon> listOfTiles = new LinkedList<Polygon>();

        // Top Half + middle
        for (int j = 1; j < size + 1; j++) {// draw the layers
          for (int k = 1; k < size + j; k++) {// draw each hex in the layer

            xPoint = 0;
            yPoint = 0;
            hexTile = new Polygon();
            xCenter = (Math.sqrt(3) * radius * (6 - k) + 250 + (Math.sqrt(3) * radius * j) / 2);
            yCenter = Math.ceil(1.5 * radius * j);
            for (int i = 0; i < 6; i++) {// draws each line
              xPoint = (int) (xCenter + radius * Math.cos(((2 * i + 1) * Math.PI / 6)));
              yPoint = (int) (yCenter + radius * Math.sin(((2 * i + 1) * Math.PI / 6)));
              hexTile.addPoint(xPoint, yPoint);
              listOfTiles.add(hexTile);
            }
            g.drawPolygon(hexTile);
          }
        }
        // Bottom half
        for (int j = size - 1; j > 0; j--) {// draw the layers
          for (int k = size + j; k > 1; k--) {// draw each hex in the layer

            xPoint = 0;
            yPoint = 0;
            hexTile = new Polygon();
            for (int i = 0; i < 6; i++) {// draws each line
              xCenter =
                  (Math.sqrt(3) * radius * (6 - k) + 250 + (Math.sqrt(3) * radius * (j + 2)) / 2);
              yCenter = Math.ceil(1.5 * radius * (2 * size - j));
              xPoint = (int) (xCenter + radius * Math.cos(((2 * i + 1) * Math.PI / 6)));
              yPoint = (int) (yCenter + radius * Math.sin(((2 * i + 1) * Math.PI / 6)));
              hexTile.addPoint(xPoint, yPoint);
              listOfTiles.add(hexTile);
            }
            g.drawPolygon(hexTile);
          }
        }


      }


    };
    contentPane.add(j);

    frame.setVisible(true);

  }
}
