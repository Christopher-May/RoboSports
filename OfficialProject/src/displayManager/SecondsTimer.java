package displayManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Simple class used for a timer that updates a JLabel every second.
 *
 */
public class SecondsTimer {

  /** The label we will be using in our gameboard */
  private JLabel timerLabel;

  /** The timer that will be incrementing that label. */
  private Timer myTimer;

  /** The intial time is 0 seconds. */
  int time = 0;

  /**
   * The constructor initializes the label to the desired x and y position And to start with the
   * text "Seconds Passed: 0"
   * 
   * @param xPos the x pixel coordinate of the timer label location
   * @param yPos the y pixel coordinate of the timer label location
   * @param textString the string used to update the label
   */
  public SecondsTimer(int xPos, int yPos, String textString) {
    // Initialization of the label
    timerLabel = new JLabel(textString + ": 0");
    timerLabel.setBounds(xPos - 120, yPos, 240, 30);
    timerLabel.setFont(new Font("Sarif", Font.BOLD, 16));

    // creating the action listener to increment the time
    ActionListener timerListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        time++;
        timerLabel.setText(textString + ": " + time);
      }
    };

    // Starting the timer with the action listener and an initial delay of 0
    myTimer = new Timer(0, timerListener);
  }

  /**
   * A getter method for the label
   * 
   * @return the label for the timer
   */
  public JLabel getLabel() {
    return this.timerLabel;
  }

  /**
   * A method to start the timer with a delay of 1000 miliseconds (1 seconD)
   */
  public void startTimer() {
    myTimer.setInitialDelay(0);
    myTimer.setDelay(1000);

    myTimer.start();
  }

  /**
   * A method to stop the timer.
   */
  public void stopTimer() {
    myTimer.stop();
  }

  /**
   * A method to reset the timer to 0 and start again
   */
  public void resetTimer() {
    time = 0;
    myTimer.restart();
  }
}
