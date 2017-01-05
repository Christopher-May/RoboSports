package gameManager;

import java.awt.event.MouseEvent;

/**
 * Interface required to be able to interact with the game manager
 */
public interface DisplayObservor {

  /**
   * Handle the mouse click event at pixel position x, y. Notify this GameManager's observer of the
   * event.
   * 
   * @param x pixel position in the x dimension.
   * @param y pixel position in the y dimension.
   */
  public void mouseClicked(int x, int y);

  /**
   * Handle the key character event. Notify this GameManager's observer of the event.
   * 
   * @param key A key the user has pressed.
   */
  public void handleKeyEvent(char key);

  /**
   * Handle a mouse moved event. Notify this GameManager of the event.
   * 
   * @param e Event created when the user moves their mouse.
   */
  public void mouseMoved(MouseEvent e);

}
