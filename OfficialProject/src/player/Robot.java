package player;

import units.Scout;
import units.Sniper;
import units.Tank;
import units.Unit;
import interpreter.ForthCodeParser;
import interpreter.ForthString;
import interpreter.Value;

import gameManager.RobotObserver;
/**
 * This class represnt the Robot, which has a unit, script and the interpreter
 */
public class Robot {

  /** The unit assigned to this robot */
  private Unit myUnit;

  /**
   * A string representation of the list of forth commands this robot makes may not be the correct
   * variable type going off of the idea that JSON is represented as a string
   */
  private String myScript;

  /** the interpreter being used to parse myScript */
  private ForthCodeParser myInterpreter;

  /**
   * constructor for the method, it may required a bit more stuff i just got it to this point so
   * that i could pass some tests in the CPUPlayer
   * 
   * @param newUnit The type of unit the robot is
   * @param newScript The script the robot is going to be running
   * @param observer The robot observer which will be gameManager
   */
  public Robot(Unit newUnit, String newScript, RobotObserver observer) {
    this.myUnit = newUnit;
    this.myScript = newScript;
    this.myInterpreter = new ForthCodeParser(this.myUnit, observer);
    initialize();
  }

  /**
   * Do the first run through of this Robot's script.
   */
  public void initialize() {
    myInterpreter.parseCode(this.myScript);
  }

  /**
   * Do the robots turn
   * 
   */
  public void play() {
    this.myInterpreter.play();
  }

  /**
   * @return unit of the robot
   */
  public Unit getUnit() {
    return myUnit;
  }

  /**
   * Get the number of messages in the mailbox of this Robot's Unit's Interpreter of the type
   * 'destType'.
   * 
   * @param destType The type of targeted Unit
   * @return the number of messages in the mailbox from the Unit destType, or -1 if destType is not
   *         found.
   */
  public int getMailBoxCount(ForthString destType) {
    String type = destType.getThisString();
    switch (type) {
      case Scout.SCOUT_NAME:
        return this.myInterpreter.scoutMailbox.size();
      case Sniper.SNIPER_NAME:
        return this.myInterpreter.sniperMailbox.size();
      case Tank.TANK_NAME:
        return this.myInterpreter.tankMailbox.size();
      default:
        return -1;
    }
  }

  /**
   * Put the message 'payLoad' into the mailbox of this Unit corresponding to the Unit who sent the
   * message, 'sentFrom'
   * 
   * @param sentFrom The type of the Unit who sent the message
   * @param payLoad The message
   * @return true if the send was successful, false otherwise.
   */
  public boolean sendMessageTo(String sentFrom, Value payLoad) {
    switch (sentFrom) {
      case Scout.SCOUT_NAME:
        if (myInterpreter.scoutMailbox.size() > 6) {
          return false;
        }
        this.myInterpreter.scoutMailbox.add(payLoad);
        return true;
      case Sniper.SNIPER_NAME:
        if (myInterpreter.sniperMailbox.size() > 6) {
          return false;
        }
        this.myInterpreter.sniperMailbox.add(payLoad);
        return true;
      case Tank.TANK_NAME:
        if (myInterpreter.tankMailbox.size() > 6) {
          return false;
        }
        this.myInterpreter.tankMailbox.add(payLoad);
        return true;
      default:
        return false;
    }
  }

}
