package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import gameManager.RobotObserver;
import gameState.IdentifyClass;
import sun.misc.Queue;
import units.Scout;
import units.Sniper;
import units.Unit;
import units.UnitFactory;

@SuppressWarnings("unused")
public class ForthCodeParser {

  /**
   * The emulated Forth Stack in java. Modifications to this stack should faithfully reflect Forth
   * code functionality as it pertains to the Subset suitable for 370 robosports
   **/
  private Stack<Value> forthStack;

  // private Stack<Command> parserStack; //might not be needed
  private HashMap<String, Command> words;

  // Storage of variables as encountered in forth code
  private HashMap<String, Value> variables;

  /** The Unit attached to this parser. */
  private Unit myUnit;

  /**
   * Mailbox of values, for communication between robots. Other robots may push values to the stack
   * prior to this robot's turn.
   */
  public ArrayList<Value> scoutMailbox;
  public ArrayList<Value> sniperMailbox;
  public ArrayList<Value> tankMailbox;

  private RobotObserver myObserver;

  /**
   * Class constructor
   * 
   * @param unit Unit on which parser will work
   * @param observer Which Robot is observing the parser
   */
  public ForthCodeParser(Unit unit, RobotObserver observer) {
    this.myUnit = unit;
    this.forthStack = new Stack<Value>();
    this.words = new HashMap<String, Command>();
    this.variables = new HashMap<String, Value>();
    this.scoutMailbox = new ArrayList<Value>(6); // max 6
    this.sniperMailbox = new ArrayList<Value>(6); // max 6
    this.tankMailbox = new ArrayList<Value>(6); // max 6
    this.myObserver = observer;

  }

  // ---ForthStack operations
  public void pushToForthStack(Value toPush) {
    forthStack.push(toPush);
  }

  public Value popFromForthStack() {
    return forthStack.pop();
  }

  private boolean forthStackIsEmpty() {
    return forthStack.isEmpty();
  }

  private Value peekAtForthStack() {
    return forthStack.peek();
  }

  // ---Arithmetic
  public void forthAdd() {
    ForthInt i = (ForthInt) forthStack.pop();
    ForthInt j = (ForthInt) forthStack.pop();
    int result = i.getThisInt() + j.getThisInt();
    ForthInt toAdd = new ForthInt();
    toAdd.Set(result);
    forthStack.push(toAdd);
    //// System.out.println("Debug: Operation result: " + ((ForthInt)
    //// peekAtForthStack()).getThisInt());
  }

  public void forthSubtract() {
    ForthInt i = (ForthInt) forthStack.pop();
    ForthInt j = (ForthInt) forthStack.pop();
    int result = j.getThisInt() - i.getThisInt();
    ForthInt toSub = new ForthInt();
    toSub.Set(result);
    forthStack.push(toSub);
    //// System.out.println("Debug: Operation result: " + ((ForthInt)
    //// peekAtForthStack()).getThisInt());
  }

  public void forthMultiply() {
    ForthInt i = (ForthInt) forthStack.pop();
    ForthInt j = (ForthInt) forthStack.pop();
    int result = j.getThisInt() * i.getThisInt();
    ForthInt toSub = new ForthInt();
    toSub.Set(result);
    forthStack.push(toSub);
    //// System.out.println("Debug: Operation result: " + ((ForthInt)
    //// peekAtForthStack()).getThisInt());
  }

  public void forthModulus() {
    ForthInt i = (ForthInt) forthStack.pop(); // ie
    ForthInt j = (ForthInt) forthStack.pop(); // iv
    int div = j.getThisInt() / i.getThisInt();
    int remainder = j.getThisInt() % i.getThisInt();
    ForthInt ir = new ForthInt();
    ForthInt iq = new ForthInt();
    ir.Set(remainder);
    iq.Set(div);
    forthStack.push(ir);
    forthStack.push(iq);
  }

  // ---Comparisons
  public void forthLessThan() {
    ForthInt i = (ForthInt) forthStack.pop();
    ForthInt j = (ForthInt) forthStack.pop();
    ForthBool result = new ForthBool();
    if (j.getThisInt() < i.getThisInt()) {
      result.Set(true);
    } else {
      result.Set(false);
    }
    this.pushToForthStack(result);
  }

  public void forthLessOrEqual() {
    ForthInt i = (ForthInt) forthStack.pop();
    ForthInt j = (ForthInt) forthStack.pop();
    ForthBool result = new ForthBool();
    if (j.getThisInt() <= i.getThisInt()) {
      result.Set(true);
    } else {
      result.Set(false);
    }
    this.pushToForthStack(result);
  }

  public void forthEqual() {
    Value v = forthStack.pop();
    Value j = forthStack.pop();
    // System.out.println(v + " " +j);
    ForthBool result = new ForthBool();
    if (v instanceof ForthInt) {
      if (((ForthInt) v).getThisInt() == ((ForthInt) j).getThisInt()) {
        result.Set(true);
      } else {
        result.Set(false);
      }
      this.pushToForthStack(result);
    }
    if (v instanceof ForthString) {
      String str1 = ((ForthString) v).getThisString();
      String str2 = ((ForthString) j).getThisString();
      // System.out.println("is *" + str1 + "* == *" + str2 + "* ?");
      if (((ForthString) v).getThisString().equals(((ForthString) j).getThisString())) {
        result.Set(true);
      } else {
        result.Set(false);
      }
      this.pushToForthStack(result);
    }
    if (v instanceof ForthBool) {
      if (((ForthBool) v).getThisBool() == ((ForthBool) j).getThisBool()) {
        result.Set(true);
      } else {
        result.Set(false);
      }
      this.pushToForthStack(result);
    }
  }

  public void forthNotEqual() {
    Value v = forthStack.pop();
    Value j = forthStack.pop();
    ForthBool result = new ForthBool();
    if (v instanceof ForthInt) {
      if (((ForthInt) v).getThisInt() == ((ForthInt) j).getThisInt()) {
        result.Set(false);
      } else {
        result.Set(true);
      }
      this.pushToForthStack(result);
    }
    if (v instanceof ForthString) {
      if (((ForthString) v).getThisString().equals(((ForthString) j).getThisString())) {
        result.Set(false);
      } else {
        result.Set(true);
      }
      this.pushToForthStack(result);
    }
    if (v instanceof ForthBool) {
      if (((ForthBool) v).getThisBool() == ((ForthBool) j).getThisBool()) {
        result.Set(false);
      } else {
        result.Set(true);
      }
      this.pushToForthStack(result);
    }
  }

  public void forthGreaterOrEqual() {
    ForthInt i = (ForthInt) forthStack.pop();
    ForthInt j = (ForthInt) forthStack.pop();
    ForthBool result = new ForthBool();
    if (j.getThisInt() >= i.getThisInt()) {
      result.Set(true);
    } else {
      result.Set(false);
    }
    this.pushToForthStack(result);
  }

  public void forthGreaterThan() {
    ForthInt i = (ForthInt) forthStack.pop();
    ForthInt j = (ForthInt) forthStack.pop();
    ForthBool result = new ForthBool();
    if (j.getThisInt() > i.getThisInt()) {
      result.Set(true);
    } else {
      result.Set(false);
    }
    this.pushToForthStack(result);
  }

  // ---Logic
  public void forthAnd() {
    ForthBool i = (ForthBool) forthStack.pop();
    ForthBool j = (ForthBool) forthStack.pop();
    ForthBool result = new ForthBool();
    if (i.getThisBool() && j.getThisBool()) {
      result.Set(true);
    } else {
      result.Set(false);
    }
    forthStack.push(result);

  }

  public void forthOr() {
    ForthBool i = (ForthBool) forthStack.pop();
    ForthBool j = (ForthBool) forthStack.pop();
    ForthBool result = new ForthBool();
    if (i.getThisBool() || j.getThisBool()) {
      result.Set(true);
    } else {
      result.Set(false);
    }
    forthStack.push(result);
  }

  public void forthInvert() {
    ForthBool i = (ForthBool) forthStack.pop();
    ForthBool result = new ForthBool();
    if (result.getThisBool()) {
      result.Set(false);
    } else {
      result.Set(true);
    }
    forthStack.push(result);
  }

  // ---Stack Words

  /**
   * drop ( v -- ) remove the value at the top of the stack.
   */
  public void forthDrop() {
    Value toDrop = popFromForthStack();
    toDrop = null;
  }

  /**
   * dup ( v -- v v ) duplicate the value at the top of the stack.
   */
  public void forthDup() {
    Value toDup = popFromForthStack();
    pushToForthStack(toDup);

    switch (toDup.getClass().toString()) {
      case ("class interpreter.ForthBool"):
        ForthBool newBool = new ForthBool();
        newBool.Set(((ForthBool) toDup).getThisBool());
        pushToForthStack(newBool);
        break;
      case ("class interpreter.ForthInt"):
        ForthInt newInt = new ForthInt();
        newInt.Set(((ForthInt) toDup).getThisInt());
        pushToForthStack(newInt);
        break;
      case ("class interpreter.ForthString"):
        ForthString newString = new ForthString();
        newString.Set(((ForthString) toDup).getThisString());
        pushToForthStack(newString);
        break;
      case ("class interpreter.PointerValue"):
        PointerValue newPointer = new PointerValue();
        newPointer.Set(((PointerValue) toDup).getMyPointerValue());
        pushToForthStack(newPointer);
        break;
    }
  }

  /**
   * swap ( v2 v1 -- v2 v1 ) swap the two values at the top of the stack
   */
  public void forthSwap() {
    Value v1 = popFromForthStack();
    Value v2 = popFromForthStack();
    pushToForthStack(v1);
    pushToForthStack(v2);
  }

  /**
   * ( v3 v2 v1 -- v3 v1 v2 ) rotate the top three stack elements
   */
  public void forthRotate() {
    Value v1 = popFromForthStack();
    Value v2 = popFromForthStack();
    Value v3 = popFromForthStack();
    pushToForthStack(v2);
    pushToForthStack(v1);
    pushToForthStack(v3);
  }

  // ---Status

  /**
   * health ( -- i )pushes the robot's initial health
   */
  private void health() {
    ForthInt fHealth = new ForthInt();
    fHealth.Set(myUnit.getMaxHealth());
    pushToForthStack(fHealth);
  }

  /**
   * healthLeft ( -- i ) pushes the robot's current health, health is consumed as damage is dealt
   */
  private void healthLeft() {
    ForthInt fHealthLeft = new ForthInt();
    fHealthLeft.Set(myUnit.getRemainingHealth());
    pushToForthStack(fHealthLeft);
  }

  /**
   * moves ( -- i ) pushes the robot's initial movement
   */
  private void moves() {
    ForthInt fMoves = new ForthInt();
    fMoves.Set(myUnit.getInitialMovement());
    pushToForthStack(fMoves);
  }

  /**
   * movesLeft ( -- i ) pushes the robots available movement (03), movement regenerates each turn
   * and is consumed as moves are made
   */
  private void movesLeft() {
    ForthInt fMovesLeft = new ForthInt();
    fMovesLeft.Set(myUnit.getMovesLeft());
    pushToForthStack(fMovesLeft);
    // System.out.println("~~~~~~~This many moves left:" + myUnit.getMovesLeft());
  }

  /**
   * attack ( -- i ) pushes the robots firepower (13), which never changes
   */
  private void attack() {
    ForthInt fAttack = new ForthInt();
    fAttack.Set(myUnit.getAttack());
    pushToForthStack(fAttack);
  }

  /**
   * range ( -- i ) pushes the robots range (13), which never changes
   */
  private void range() {
    ForthInt fRange = new ForthInt();
    fRange.Set(myUnit.getRange());
    pushToForthStack(fRange);
  }

  /**
   * team ( -- s ) pushes the robots current team colour, as a string: RED, ORANGE, YELLOW, GREEN,
   * BLUE, PURPLE
   */
  private void team() {
    ForthString fTeam = new ForthString();
    fTeam.Set(myUnit.getTeamName().toString());
    pushToForthStack(fTeam);
  }

  /**
   * type ( -- s ) pushes the robots type, as a string: SCOUT, SNIPER, TANK
   */
  private void type() {
    ForthString fType = new ForthString();
    String type;
    if (this.myUnit instanceof Scout) {
      type = "SCOUT";
    } else if (this.myUnit instanceof Sniper) {
      type = "SNIPER";
    } else {
      type = "TANK";
    }
    fType.Set(type);
    // System.out.println(" looking at type: "+ type);
    pushToForthStack(fType);
  }

  // ---Actions

  /**
   * turn! ( i -- ) turns the robot to face in a new direction.
   */
  private void turn() {
    ForthInt fToTurn = (ForthInt) popFromForthStack();
    int numTimesToTurn = fToTurn.getThisInt() % 6;
    // System.out.println("name: " + this.myUnit.getType());
    // System.out.println("------> before: " + this.myUnit.getDirection());
    for (int i = 0; i < numTimesToTurn; i++) {
      this.myObserver.forthTurn();
    }
    // System.out.println("----> after: " + this.myUnit.getDirection());

    // System.out.println("========During interpreter Turn " + fToTurn.getThisInt()+" unit(s)");
  }

  /**
   * move! ( -- ) moves robot one space in the direction its currently facing.
   */
  private void move() {
    this.myObserver.forthMove();
    //// System.out.println("========During interpreter Move forward 1 unit");
  }

  /**
   * shoot! ( id ir -- ) fires the robot's weapon at the space which is at distance ir and direction
   * id
   */
  private void shoot() {
    ForthInt fRange = (ForthInt) popFromForthStack();
    ForthInt fDirection = (ForthInt) popFromForthStack();

    this.myObserver.forthShoot(fRange.getThisInt(), fDirection.getThisInt(), this.myUnit);
  }

  /**
   * scan! ( -- i ) scans for all visible robots (based on the robots range), and reports how many
   * other robots are present. Then the robot can identify! each of these targets
   */
  private void scan() {
    ForthInt fScan = new ForthInt();
    fScan.Set(this.myObserver.scan(this.myUnit));
    pushToForthStack(fScan);
    //// System.out.println("========During interpreter Scan " + fScan.getThisInt());
  }

  /**
   * identify! ( i -- st ir id ih ) identifies the given target (an index in the range 0 through
   * scan! 1 -, giving its team colour (st), range (ir), direction (id), and remaining health (ih).
   */
  private void identify() {
    ArrayList<Unit> visibleUnits = this.myObserver.forthScannedUnits(this.myUnit);

    ForthInt unitToExamine = (ForthInt) popFromForthStack();
    Unit currentExaminedUnit = visibleUnits.get(unitToExamine.getThisInt());

    ForthInt ir = new ForthInt();
    ForthInt id = new ForthInt();
    ForthInt ih = new ForthInt();
    ForthString st = new ForthString();

    IdentifyClass results = this.myObserver.forthIdentify(this.myUnit, currentExaminedUnit);

    ir.Set(results.range);
    id.Set(results.direction);
    ih.Set(results.remainingHealth);
    st.Set(results.colour);

    //// System.out.println("======Considered range:"+ results.range + " direction:
    //// "+results.direction + " health left:"+
    // results.remainingHealth +" on team: "+ results.colour);

    pushToForthStack(ih);
    pushToForthStack(id);
    pushToForthStack(ir);
    pushToForthStack(st);

  }

  /**
   * check! ( i -- s ) pops a given direction, and pushes a string describing the adjacent space in
   * that direction
   *
   * String can be EMPTY | OCCUPIED | OUT OF BOUNDS (spaces included)
   */
  private void check() {
    ForthInt checkDirection = new ForthInt();
    checkDirection = (ForthInt) popFromForthStack();
    // System.out.println("check dir: " + checkDirection.getThisInt());

    String hexDesc = this.myObserver.forthCheck(this.myUnit, checkDirection.getThisInt());
    ForthString hexDescription = new ForthString();
    hexDescription.Set(hexDesc);

    // System.out.println("~~~Interpreter Check() Debug: pushing :" + hexDesc);

    pushToForthStack(hexDescription);

  }

  /**
   * empty? ( id -- be ) check! ."EMPTY" = ; compare a String from the top of the stack with the
   * literal value "EMPTY". If they are equal, put true on the stack. Else, put false on the stack"
   */
  private void empty() {
    check();
    ForthString checkResult = new ForthString();
    checkResult = (ForthString) popFromForthStack();
    ForthBool comparisonResult = new ForthBool();
    if (checkResult.getThisString().equals("EMPTY")) {
      comparisonResult.Set(true);
    } else {
      comparisonResult.Set(false);
    }
    pushToForthStack(comparisonResult);
  }

  // ---Mailboxes

  /**
   * send! ( s v -- b ) send a value v to team-member s; returns a boolean indicating success or
   * failure. Failure can occur if the robot is dead (health = 0), or its mailbox is full.
   * 
   */
  private void send() {
    Value payLoad = this.popFromForthStack();
    ForthString destType = (ForthString) this.popFromForthStack();

    ForthBool result = new ForthBool();

    if (this.myObserver.sendMessage(destType, payLoad)) {
      result.Set(true);
    } else {
      result.Set(false);
    }

    this.pushToForthStack(result);

  }

  /**
   * mesg? ( s -- b ) indicates whether the robot has a waiting message from team-member s
   */
  private void mesg() {
    ForthString teamMember = (ForthString) this.popFromForthStack();
    ForthBool result = new ForthBool();
    result.Set(false);

    if ("SCOUT" == teamMember.getThisString()) {
      if (this.scoutMailbox.size() > 0) {
        result.Set(true);
      } else {
        result.Set(false);
      }
    } else if ("SNIPER" == teamMember.getThisString()) {
      if (this.sniperMailbox.size() > 0) {
        result.Set(true);
      } else {
        result.Set(false);
      }
    } else {
      if (this.tankMailbox.size() > 0) {
        result.Set(true);
      } else {
        result.Set(false);
      }
    }

    this.pushToForthStack(result);

  }

  /**
   * recv! ( s -- v ) pushes the next message value from the given teammember onto the stack.
   */
  private void recv() {
    ForthString teamMember = (ForthString) this.popFromForthStack();
    Value msg;

    if ("SCOUT" == teamMember.getThisString()) {
      if (this.scoutMailbox.size() > 0) {
        msg = this.scoutMailbox.get(0);
        this.pushToForthStack(msg);
      }
    } else if ("SNIPER" == teamMember.getThisString()) {
      if (this.sniperMailbox.size() > 0) {
        msg = this.sniperMailbox.get(0);
        this.pushToForthStack(msg);
      }
    } else {
      if (this.tankMailbox.size() > 0) {
        msg = this.tankMailbox.get(0);
        this.pushToForthStack(msg);
      }
    }
  }

  // ---miscellaneous operations

  /**
   * the . command will print the value of the stack to //System out, and then remove the top of the
   * stack. Note: //System.out may not be where this value is to be placed, it may need to be
   * retained elsewhere in future versions.
   */
  public void fDot() {
    Value v = peekAtForthStack();
    if (v instanceof ForthInt) {
      // System.out.println("peeking at: "+ ((ForthInt) v).getThisInt());
    }
    if (v instanceof ForthString) {
      // System.out.println("peeking at: "+ ((ForthString) v).getThisString());
    }
    if (v instanceof ForthBool) {
      // System.out.println("peeking at: "+ ((ForthBool) v).getThisBool());
    }
    forthStack.pop();
  }

  /**
   * ( i -- i') generates a random integer between 0 and i inclusive
   */
  public void forthRandom() {
    ForthInt stackValue = (ForthInt) popFromForthStack();
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int toPush = ThreadLocalRandom.current().nextInt(0, stackValue.getThisInt() + 1);
    stackValue.Set(toPush);
    pushToForthStack(stackValue);
  }

  /**
   * Forth if evaluates the top item on the forth stack. If true, it executes the trueList, else it
   * executes the false list
   * 
   * @param trueList: this list of forthwords as strings to execute in order when true
   * @param falseList: the list of forthwords as strings to execute in order when false
   */
  public void forthIf(ArrayList<String> trueList, ArrayList<String> falseList) {
    ForthBool logicResult = (ForthBool) popFromForthStack();
    if (logicResult.getThisBool()) {
      // System.out.println("Logic resulted True" + "List of stuff was: ");

      for (String s : trueList) {
        // System.out.println(" "+ s);
      }

      evaluateAllForthWords(trueList, 1);
    } else {

      // System.out.println("Logic resulted False");

      for (String s : falseList) {
        // System.out.println("False list: "+ s);
      }
      evaluateAllForthWords(falseList, 1);
    }
  }

  // ----End of Stack ops
  // ----Main Entry point follows

  /**
   * The parse technique, first pass just build a list of strings, do not interpret anything until
   * finished (except ; and ." strings "
   * 
   * @param code the Forth Code from JSON
   */
  public void parseCode(String code) {
    ArrayList<String> allForthWords = new ArrayList<String>();
    String currentWord = "";
    for (int i = 0, n = code.length(); i < n; i++) {
      //// System.out.println("processing: "+ code.charAt(i));
      if (code.charAt(i) == ' ') {
        allForthWords.add(currentWord);
        currentWord = "";
      } else if (code.charAt(i) == ';') {
        allForthWords.add(";");
        currentWord += ' ';
      } else if (code.charAt(i) == '.') {
        if (code.charAt(i + 1) == '"') {
          i++; // skip the .
          i++; // skip the "
          currentWord += ".\""; // add ."
          while (code.charAt(i) != '"') { // build a string
            // handle spaces
            if (code.charAt(i) == ' ') {
              //// System.out.println("FIRST PASS DETECTED A SPACE AND TRIED TO ADD IT BACK IN");
              currentWord += " ";
              i++;
            } else {
              currentWord += code.charAt(i);
              i++;
            }
          }
        }
        currentWord += "\" "; // add " (space)
        // System.out.println("First pass parse built string: "+ currentWord);
        allForthWords.add(currentWord);
        currentWord = "";
      } else {
        currentWord += code.charAt(i);
      }
    }
    // System.out.println(code);
    evaluateAllForthWords(allForthWords, 1);
  }

  /**
   * The parse technique second (or many) pass, intended to be called and handle nesting recursively
   * 
   * @param allForthWords: the list of words to parse through, some words result in a recursive call
   *        to this method
   * @param counter: similar to a loop counter, used to determine how deep into nesting statements
   *        the method is.
   */
  public void evaluateAllForthWords(ArrayList<String> allForthWords, int counter) {

    if (counter == 0) { // recursion done
      return;
    } else {
      Iterator<String> it = allForthWords.iterator();
      while (it.hasNext()) {
        String word = it.next();

        if (word.equals("leave") && counter > 1) { // if a nested loop, leave exits the loop, maybe
          // this happens when counter=1?
          counter = 0; // Exit the current loop body, AND the outer loop
          return;
        }

        //// System.out.println(" Now processing: "+word);
        switch (word) {

          case "loop":
            if (counter >= 1) {
              evaluateAllForthWords(allForthWords, counter - 1);
              //// System.out.println(" Debug: Looping!");
            }
            return;

          case "until":
            ForthBool beginExitCondition = (ForthBool) popFromForthStack();
            // System.out.println("~~~~~Does begin go again? : "+beginExitCondition.getThisBool());
            if (beginExitCondition.getThisBool()) {
              return;
            } else {
              evaluateAllForthWords(allForthWords, 1);
            }
            break; // begin finished


          case "(":
            while (!word.equals(")")) {
              word = it.next(); // skip words until closing ) matched
              // //System.out.println(" ( matched skipping comment" + word);
            }
            // word= it.next();
            break;


          // case for begin, evaluate the exit condition, and if true exit recursion, else
          // keep going.

          case "+":
            forthAdd();
            break;
          case "-":
            forthSubtract();
            break;
          case "*":
            forthMultiply();
            break;
          case "/mod":
            forthModulus();
            break;
          // logic
          case "and":
            forthAnd();
            break;
          case "or":
            forthOr();
            break;
          case "invert":
            forthInvert();
            break;

          // comparisons
          case "<":
            forthLessThan();
            break;
          case "<=":
            forthLessOrEqual();
            break;
          case "=":
            forthEqual();
            break;
          case "<>":
            forthNotEqual();
            break;
          case "=>":
            forthGreaterOrEqual();
            break;
          case ">":
            forthGreaterThan();
            break;

          // Stack words
          case "drop":
            forthDrop();
            break;
          case "dup":
            forthDup();
            break;
          case "swap":
            forthSwap();
            break;
          case "rot":
            forthRotate();
            break;

          // Stack synonyms for fun !
          case "pop":
            forthDrop();
            break;
          case "move":
            move();
            break;

          // case "shot?": forthCheckShot();
          // break;

          // Actions
          case "shoot!":
            shoot();
            break;
          case "check!":
            check();
            break;
          case "scan!":
            scan();
            break;
          case "identify!":
            identify();
            break;
          case "turn!":
            turn();
            break;
          case "move!":
            move();
            break;
          case "empty?":
            empty();
            break;

          // Mailboxes
          case "send!":
            send();
            break;
          case "mesg?":
            mesg();
            break;
          case "recv!":
            recv();

            // Status
          case "health":
            health();
            break;
          case "healthLeft":
            healthLeft();
            break;
          case "moves":
            moves();
            break;
          case "movesLeft":
            movesLeft();
            break;
          case "attack":
            attack();
            break;
          case "range":
            range();
            break;
          case "team":
            team();
            break;
          case "type":
            type();
            break;

          // Variables
          // TODO Test all variable cases
          case "variable":
            ForthInt placeHolder = new ForthInt();
            placeHolder.Set(0);
            word = it.next();
            variables.put(word, placeHolder);
            // System.out.println("building new variable named - " + word);
            word = it.next(); // consume the ;
            //// System.out.println(" skipping the ; ");
            // word = it.next(); //and set to word following
            break;
          case "?":
            ForthString thisLocation = (ForthString) popFromForthStack();
            Value thisVal = variables.get(thisLocation.getThisString());
            pushToForthStack(thisVal);
            //// System.out.println(" Variable: "+thisLocation.getThisString()+" was found
            //// containing: "+thisVal.toString());
            break;
          case "!":
            // Example in RL.pdf has value and location reversed on page 10?

            // Hey look we noticed this weeks ago from the comment above ^
            // Here is some code to handle syntax errors:
            Value ambiguousValue = popFromForthStack();
            Value val2 = popFromForthStack();
            Value payload;
            ForthString variableName;

            if (ambiguousValue instanceof ForthString) { // if name first
              payload = val2;
              variableName = (ForthString) ambiguousValue;
            } else { // if payload first
              payload = ambiguousValue;
              variableName = (ForthString) val2;
            }

            // Value payload = popFromForthStack(); // the value
            // ForthString variableName = (ForthString) popFromForthStack(); // the location name

            String locationName = variableName.getThisString();
            variables.put(locationName, payload);
            //// System.out.println(" variable "+locationName+" now contains "+payload.toString());
            break;

          // misc
          // TODO: test forthRandom()
          case "random":
            forthRandom();
            break;

          case ".":
            //// System.out.println("=================FALSE STRING!!!");
            fDot();
            break;

          case "I":
            ForthInt doI = new ForthInt();
            doI.Set(counter - 1);
            pushToForthStack(doI);
            //// System.out.println("~~~~~~~~~~~~~Pushing I to stack");
            break;
          // forth bools
          case "true":
            ForthBool trueBool = new ForthBool();
            trueBool.Set(true);
            pushToForthStack(trueBool);
            break;
          case "false":
            ForthBool falseBool = new ForthBool();
            falseBool.Set(false);
            pushToForthStack(falseBool);
            break;

          // complicated cases that require building possibly nested expressions

          // Strategy is to just build the outermost loop, so a counter is used to track instances
          // of
          // Nests. Each time a subsequent do is encountered, the skipCounter increases by 1. For
          // each
          // skipCounter increment, ignore an encountered loop word, until the original matching
          // loop
          // word is found. Then evaluate the body with possibly nested loops by recursively calling
          // the
          // main parse method
          case "do":
            ForthInt start = (ForthInt) forthStack.pop();
            ForthInt end = (ForthInt) forthStack.pop();
            int numberOfLoops = (end.getThisInt() - start.getThisInt()) + 1; ////////
            // System.out.println("~Do should loop this many times: "+numberOfLoops);
            ArrayList<String> doBody = new ArrayList<String>();
            int skipCounter = 1; // This number is used to count how many loop words to ignore in
            // nested loops
            String loopBodyWord = "";

            outerwhileloop: while (it.hasNext()) { // for each word left, prematurely exit when
              // matching loop to do words found
              loopBodyWord = it.next();
              if (loopBodyWord.equals("loop") && skipCounter > 1) {
                skipCounter--;
                loopBodyWord = "";
                doBody.add("loop");
                //// System.out.println("A nested loop was built");
              } else if (loopBodyWord.equals("loop") && skipCounter <= 1) {
                doBody.add("loop");
                break outerwhileloop;
              } else {
                switch (loopBodyWord) {
                  case "do":
                    skipCounter++; // skip an extra case of loop, as it is a nest key
                    doBody.add("do");
                    loopBodyWord = "";
                    break;
                  /*
                   * case "loop": doBody.add("loop"); //DEPRECATED, redundant with default case
                   * break;
                   */
                  default:
                    //// System.out.println("adding "+loopBodyWord +" do instances: " +skipCounter);
                    doBody.add(loopBodyWord); // most common case, word was not do or loop
                    loopBodyWord = "";
                    break;
                }
              }
            } // end of outerwhileloop

            evaluateAllForthWords(doBody, numberOfLoops); // once the final loop word reached,
            // execute the built list
            break;

          // TODO nested ifs, use same technique for skip repeats as do
          case "if":
            ArrayList<String> trueBody = new ArrayList<String>();
            ArrayList<String> falseBody = new ArrayList<String>();
            int skipCount = 1;
            word = it.next();
            while (!word.equals("else") && skipCount == 1) { // build true list
              if (word.equals("(")) {
                // int j = 2; //skip the ( and the space
                while (!word.equals(")")) {
                  word = it.next(); // skip words until closing ) matched
                  //// System.out.println("skipping comment" + word);
                }
                word = it.next();
              }



              // word=it.next();
              else if (word.equals("if")) {
                //// System.out.println("Debug nested if");
                skipCount++;
                word = it.next();
              } else if (word.equals("then") && skipCount > 1) {
                skipCount--;
                word = it.next();
              } else {
                trueBody.add(word);
                word = it.next();
              }
            }
            //// System.out.println(word);
            if (word.equals("else")) {
              word = it.next(); // Skip the empty case
            }
            while (!word.equals("then")) { // from else to then, build false list
              // word=it.next();

              // This might not hande an if nested in an else statement

              if (word.equals("if")) {
                ArrayList<String> nestedIf = new ArrayList<String>();
                nestedIf.add("if");
                while (!word.equals("then")) {
                  nestedIf.add(word);
                  word = it.next();
                }
                nestedIf.add("then");
                word = it.next();
                evaluateAllForthWords(nestedIf, 1);
              }


              falseBody.add(word);
              word = it.next();
            }
            forthIf(trueBody, falseBody);
            break;

          // TODO nested begins, use same technique for skip repeats as do
          case "begin":
            ArrayList<String> beginBody = new ArrayList<String>();
            String beginBodyWord = "";
            int beginSkipCounter = 1;
            while (!word.equals("until") && beginSkipCounter <= 1) {
              word = it.next(); // add the final 'until' here to the body
              beginBody.add(word);
              if (word.equals("begin")) {
                beginSkipCounter++;
              }
              if (word.equals("until")) {
                beginSkipCounter--;
              }
            }
            evaluateAllForthWords(beginBody, 1);
            break;

          default: // it might still be a new word definition, string, or literal
            if (!word.isEmpty()) {
              switch (word.charAt(0)) {
                case ':':
                  int i = 0;
                  String newWordKey = "";
                  word = it.next();
                  while (i < word.length()) {
                    //// System.out.println(word.charAt(i));
                    newWordKey += word.charAt(i);
                    i++;
                  }
                  MultiWordCommand operations = new MultiWordCommand();
                  String currentWordInBody = "";
                  word = it.next();
                  while (!word.equals(";")) {
                    //// System.out.println("adding "+word+"to phrase list");
                    if (word.equals("(")) {
                      int j = 2; // skip the ( and the space
                      while (!word.equals(")")) {
                        word = it.next(); // skip words until closing ) matched
                        //// System.out.println("skipping comment" + word);
                      }
                      // word= it.next();
                    }
                    operations.addStringToList(word);
                    word = it.next();
                  }
                  words.put(newWordKey, operations);
                  // System.out.println("finished new word build for: "+newWordKey);
                  break;

                case '(':
                  int j = 2; // skip the ( and the space
                  while (!word.equals(")")) {
                    word = it.next(); // skip words until closing ) matched
                    //// System.out.println("skipping comment");
                  }
                  break;

                default:
                  if (word.matches("\\.\".*\" ")) { // Any String eg ."OUT OF BOUNDS" or ."OUT
                    // \"OF\" BOUNDS"
                    // System.out.println("===========================Found STRING!!!");
                    String strLiteral = word.substring(2, word.length() - 2);

                    ForthString stringLiteral = new ForthString();
                    stringLiteral.Set(strLiteral);
                    this.pushToForthStack(stringLiteral);
                  } else {
                    evaluateWord(word);
                  }
                  break;
              }
            }
        }
      }
      counter--; // reached once entire string list has finished iterating
      //// System.out.println("~~~~~~~~"+ counter);
      evaluateAllForthWords(allForthWords, counter);
    } // end of else statement, when counter was !=0
  }

  /**
   * evaluates the Forth Word passed in as a string. Executing a literal value pushes it onto the
   * stack, else for known word definitions
   * 
   * @param wordIn: string representation of a forth word
   */
  public void evaluateWord(String wordIn) {

    // TODO: this method may need to handle variable,bool,strings. The parser method\
    // seems to be doing this so far.

    //// System.out.println("Evaluating: "+wordIn);

    wordIn = wordIn.trim();
    // if wordIn is an integer, push it to the forthstack
    if (isInteger(wordIn)) {

      ForthWord thisWord = new ForthWord();

      ForthInt thisInt = new ForthInt();

      thisInt.Set(Integer.parseInt(wordIn));

      thisWord.setLiteralValue(thisInt);
      thisWord.Execute(this);
    }

    // if wordIn is a known command
    for (Map.Entry<String, Command> it : words.entrySet()) {
      if (it.getKey().equals(wordIn)) {
        // System.out.println(it.getValue() + " is a known operation, matching: " + wordIn);
        if (it.getValue() instanceof MultiWordCommand) { // if the word is a multiword command
          MultiWordCommand thisMultiCmd = (MultiWordCommand) it.getValue();
          thisMultiCmd.Execute(this);
        }
        if (it.getValue() instanceof AllForthCommands) { // if the word is in the predefined or
          // added commands
          AllForthCommands thisKnownWord = (AllForthCommands) it.getValue();
          thisKnownWord.Execute(this, it.getKey());
        }
      }
    }
    // if wordIn is a known variable name
    for (Map.Entry<String, Value> it : variables.entrySet()) {
      if (it.getKey().equals(wordIn)) {
        // System.out.println(wordIn + " is a known variable, matching: " + wordIn);
        ForthString theVariableName = new ForthString(); // not a location, but a string?
        theVariableName.Set(wordIn);
        pushToForthStack(theVariableName);
      }
    }
  }

  /**
   * Run the play method on this Robot.
   */
  public void play() {
    this.evaluateWord("play ");
  }

  // useful methods

  /**
   * Returns true if argument is a valid integer, else fasle
   * 
   * @param s: String to test
   * @return: true if s was an integer, else false
   */
  public static boolean isInteger(String s) {
    try {

      Integer.parseInt(s.trim());
      // s is a valid integer
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }

  /**
   * Print the dictionary of known words
   */
  private void printWordDictionary() {
    Iterator<Entry<String, Command>> it = words.entrySet().iterator();
    while (it.hasNext()) {
      @SuppressWarnings("rawtypes")
      Map.Entry pair = (Map.Entry) it.next();
      // System.out.println(pair.getKey() + " = " + pair.getValue());
      it.remove(); // avoids a ConcurrentModificationExceptions
    }
  }

  /**
   * Print the dictionary of known variables
   */
  private void printVariableDictionary() {
    Iterator<Entry<String, Value>> it = variables.entrySet().iterator();
    while (it.hasNext()) {
      @SuppressWarnings("rawtypes")
      Map.Entry pair = (Map.Entry) it.next();
      // System.out.println(pair.getKey() + " = " + pair.getValue());
      it.remove(); // avoids a ConcurrentModificationException
    }
  }

  /**
   * Print the forth stack as it exists currently.
   */
  public void printStack() {
    for (Value v : this.forthStack) {
      if (v instanceof ForthInt) {
        // System.out.println("StackPrint: "+ ((ForthInt) v).getThisInt());
      }
      if (v instanceof ForthString) {
        // System.out.println("StackPrint: "+ ((ForthString) v).getThisString());
      }
      if (v instanceof ForthBool) {
        // System.out.println("StackPrint: "+ ((ForthBool) v).getThisBool());
      }
      /*
       * if(v instanceof PointerValue){ //System.out.println("forthInt on stack: "+ ((ForthInt)
       * v).getThisInt()); }
       */
    }
  }

  /**
   * Main test method
   * 
   * @param args: discarded
   */
  public static void main(String[] args) {
    // // Useful test fields
    // Unit test = UnitFactory.getTestUnit();
    //
    // ForthCodeParser theInterpreter = new ForthCodeParser(test, null);
    // ForthInt a = new ForthInt();
    // ForthInt b = new ForthInt();
    // ForthInt value;
    // ForthInt value2;
    //
    // // Test Addition
    // a.Set(13);
    // b.Set(7);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthAdd();
    // value = (ForthInt) theInterpreter.popFromForthStack();
    //// //System.out.println(
    //// value.getThisInt() + " = 13 + 7. (true)Is the stack empty?: " +
    // theInterpretter.forthStackIsEmpty());
    // assert (value.getThisInt() == 20);
    //
    // // Test subtraction
    // value = null;
    // a.Set(4);
    // b.Set(3);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthSubtract();
    // value = (ForthInt) theInterpreter.popFromForthStack();
    //// //System.out.println(
    //// value.getThisInt() + " = 4-3. (true)Is the stack is empty?: " +
    // theInterpretter.forthStackIsEmpty());
    // assert value.getThisInt() == 1;
    //
    // // Test multiplication
    // value = null;
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthMultiply();
    // value = (ForthInt) theInterpreter.popFromForthStack();
    // //System.out.println(
    // value.getThisInt() + " = 4*3. (true)Is the stack is empty?: " +
    // theInterpreter.forthStackIsEmpty());
    // assert value.getThisInt() == 12;
    //
    // // Test modulus
    // value = null;
    // value2 = null;
    // a.Set(47);
    // b.Set(5);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthModulus();
    // value = (ForthInt) theInterpreter.popFromForthStack();
    // value2 = (ForthInt) theInterpreter.popFromForthStack();
    // //System.out.println("47/5 = " + value.getThisInt() + "(9). 47%5 =" + value2.getThisInt() +
    // "(2).");
    //
    // // Booleans for logic testing
    // ForthBool c = new ForthBool();
    // ForthBool d = new ForthBool();
    // c.Set(true);
    // d.Set(true);
    // theInterpreter.pushToForthStack(c);
    // theInterpreter.pushToForthStack(d);
    // theInterpreter.forthAnd();
    // ForthBool result = (ForthBool) theInterpreter.popFromForthStack();
    // // Test logic AND
    // if (result.getThisBool()) {
    // //System.out.println("True and True test OK for ForthAnd(). The " + " stack should be empty:
    // "
    // // + theInterpreter.forthStackIsEmpty());
    // } else {
    // //System.out.println("Something went wrong with ForthAnd()");
    // }
    //
    // // Test logic OR
    // result = null;
    // d.Set(false);
    // theInterpreter.pushToForthStack(c);
    // theInterpreter.pushToForthStack(d);
    // theInterpreter.forthOr();
    // result = (ForthBool) theInterpreter.popFromForthStack();
    // if (result.getThisBool()) {
    // //System.out.println("True or False test OK for ForthOr(). The " + " stack should be empty: "
    // // + theInterpreter.forthStackIsEmpty());
    // } else {
    // //System.out.println("Something went wrong with ForthOr()");
    // }
    //
    // // Test logic INVERT
    // result = null;
    // theInterpreter.pushToForthStack(d); // push false
    // theInterpreter.forthInvert();
    // result = (ForthBool) theInterpreter.popFromForthStack();
    // if (result.getThisBool()) {
    // //System.out.println("forthInvert() Tests OK");
    // } else {
    // //System.out.println("Something went wront with forthInvert()");
    // }
    //
    // // comparison tests
    // // testing less than
    // a.Set(1);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthLessThan();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was less than " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(1);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthLessThan();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was less than " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthLessThan();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was less than " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(1);
    // b.Set(2);
    // String lessThanTest = "< ";
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.parseCode(lessThanTest);
    // if( true != ((ForthBool)theInterpreter.popFromForthStack()).getThisBool() ) {
    // //System.out.println("Error: forthLessThan");
    // }
    //
    // // less than OR equal to
    // a.Set(1);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthLessOrEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was less than or equal to " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(1);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthLessOrEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was less than or equal to " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthLessOrEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was less than or equal to " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // // Testing equal too
    // a.Set(1);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was equal to " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was equal to " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // // Testing not equal too
    // a.Set(1);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthNotEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was not equal to " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthNotEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was not equal to " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // // Testing greater than or equals
    // a.Set(1);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthGreaterOrEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was greater than or equal to " + b.getThisInt() + ":
    // " + c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(1);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthGreaterOrEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was greater than or equal to " + b.getThisInt() + ":
    // " + c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthGreaterOrEqual();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was greater than or equal to " + b.getThisInt() + ":
    // " + c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // // Testing greater than
    // a.Set(1);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthGreaterThan();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was greater than " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(1);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthGreaterThan();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was greater than " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(2);
    // b.Set(2);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthGreaterThan();
    // c = (ForthBool) theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " was greater than " + b.getThisInt() + ": " +
    // c.getThisBool()
    // + "; and the stack" + " should be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // //reuse a for Stack words tests
    // //Testing drop
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.forthDrop();
    // //System.out.println("The forth stack should be empty and it is: "+
    // theInterpreter.forthStackIsEmpty());
    //
    // //Testing DUP
    // a.Set(42);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.forthDup();
    // a = (ForthInt)theInterpreter.popFromForthStack();
    // b = (ForthInt)theInterpreter.popFromForthStack();
    // //System.out.println(a.getThisInt() + " should equal "+ b.getThisInt() + ": " +
    // (a.getThisInt() == b.getThisInt())
    // +". Also, the forth stack should be empty and it is: "+
    // theInterpreter.forthStackIsEmpty());
    // //System.out.println("\tNote: the stack is empty because we popped the two values off"
    // + " so that we could compare them, before the pop the stack had two elements");
    //
    // c.Set(true);
    // theInterpreter.pushToForthStack(c);
    // theInterpreter.forthDup();
    // c = (ForthBool)theInterpreter.popFromForthStack();
    // d = (ForthBool)theInterpreter.popFromForthStack();
    // //System.out.println(c.getThisBool() + " should equal "+ d.getThisBool() + ": " +
    // // (c.getThisBool() == d.getThisBool())
    // // +". Also, the forth stack should be empty and it is: "+
    // theInterpreter.forthStackIsEmpty());
    // //System.out.println("\tNote: the stack is empty because we popped the two values off"
    // + " so that we could compare them, before the pop the stackc had two elements");
    //
    // //Testing SWAP
    // a.Set(432);
    // b.Set(1234);
    // int resultA = a.getThisInt();
    // int resultB = b.getThisInt();
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthSwap();
    // b = (ForthInt)theInterpreter.popFromForthStack();
    // a = (ForthInt)theInterpreter.popFromForthStack();
    // //System.out.println(resultA + " should equal " + b.getThisInt() + ": " + (resultA ==
    // b.getThisInt())
    // // + "\n\t" + resultB + " should equal " + a.getThisInt() + ": " + (resultB ==
    // a.getThisInt())
    // // + "\n\tAnd the stack should not be empty: " + theInterpreter.forthStackIsEmpty());
    //
    // a.Set(42);
    // c.Set(true);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(c);
    // theInterpreter.forthSwap();
    // if(theInterpreter.popFromForthStack() instanceof ForthInt &&
    // theInterpreter.popFromForthStack() instanceof ForthBool){
    // //System.out.println("int and bool popped in order ok as a result of Swap");
    // } else {
    // //System.out.println("Swap didnt work");
    // }
    //
    // //Testing ROT
    //
    // a.Set(42);
    // b.Set(1234);
    // c.Set(true);
    // theInterpreter.pushToForthStack(c);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.forthRotate();
    // if(theInterpreter.popFromForthStack() instanceof ForthBool &&
    // theInterpreter.popFromForthStack() instanceof ForthInt &&
    // theInterpreter.popFromForthStack() instanceof ForthInt){
    // //System.out.println("Rotate correctly manipulated stack test 1");
    // } else {
    // //System.out.println("Rotate didnt work");
    // }
    //
    // a.Set(42);
    // b.Set(1234);
    // c.Set(true);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.pushToForthStack(c);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.forthRotate();
    // if(theInterpreter.popFromForthStack() instanceof ForthInt &&
    // theInterpreter.popFromForthStack() instanceof ForthInt &&
    // theInterpreter.popFromForthStack() instanceof ForthBool){
    // //System.out.println("Rotate correctly manipulated stack test 2");
    // } else {
    // //System.out.println("Rotate didnt work");
    // }
    //
    // //System.out.println("The stack is empty "+ theInterpreter.forthStackIsEmpty());
    //
    // // STRING TESTS
    //
    // String testStringLiteral0 = ".\"SOMETHING\"";
    // String testStringLiteral1 = ".\"\"";
    // String testStringLiteral2 = ".\"EMPTY\"";
    // String testStringLiteral3 = ".\"SOME THING\"";
    // String testStringLiteral4 = ".\"SOME \"HI\" THING\"";
    //
    // ArrayList<String> literals = new ArrayList<String>();
    // literals.add(testStringLiteral0);
    // literals.add(testStringLiteral1);
    // literals.add(testStringLiteral2);
    // literals.add(testStringLiteral3);
    // literals.add(testStringLiteral4);
    //
    // theInterpreter.evaluateAllForthWords(literals, 1);
    // theInterpreter.printStack();
    //
    // ForthString strResult = (ForthString)theInterpreter.popFromForthStack();
    // if(!strResult.getThisString().equals("SOME \"HI\" THING")) {
    // //System.out.println("err4");
    // }
    //
    // strResult = (ForthString)theInterpreter.popFromForthStack();
    // if(!strResult.getThisString().equals("SOME THING")) {
    // //System.out.println("err3");
    // }
    //
    // strResult = (ForthString)theInterpreter.popFromForthStack();
    // if(!strResult.getThisString().equals("EMPTY")) {
    // //System.out.println("err2");
    // }
    //
    // strResult = (ForthString)theInterpreter.popFromForthStack();
    // if(!strResult.getThisString().equals("")) {
    // //System.out.println("err1");
    // }
    //
    // strResult = (ForthString)theInterpreter.popFromForthStack();
    // if(!strResult.getThisString().equals("SOMETHING")) {
    // //System.out.println("err0");
    // }
    //
    // if(!theInterpreter.forthStackIsEmpty()) {
    // //System.out.println("err-1");
    // }
    //
    // //Do is tested in the test class
    //
    // //test if
    //
    // a.Set(1);
    // b.Set(2);
    //
    // ArrayList<String> testTrueList = new ArrayList<String>();
    // testTrueList.add("1");
    // testTrueList.add("2");
    // testTrueList.add("+");
    // ArrayList<String> testFalseList = new ArrayList<String>();
    // testFalseList.add("1");
    // testFalseList.add("2");
    // testFalseList.add("-");
    //
    // ForthInt ifResult = new ForthInt();
    //
    // // Test for true case
    //
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.forthLessOrEqual();
    //
    // theInterpreter.forthIf(testTrueList, testFalseList);
    //
    // ifResult = (ForthInt) theInterpreter.popFromForthStack();
    // if( 3 != ifResult.getThisInt()) {
    // //System.out.println("Error: forthIf failed with result "+ifResult.getThisInt()
    // //+", the parse True/False list may not have been built correctly");
    // }
    // if( !theInterpreter.forthStackIsEmpty()) {
    // //System.out.println("Error: forthIf stack not empty");
    // }
    //
    // // Test for false case
    //
    // theInterpreter.pushToForthStack(b);
    // theInterpreter.pushToForthStack(a);
    // theInterpreter.forthLessOrEqual();
    //
    // theInterpreter.forthIf(testTrueList, testFalseList);
    //
    // ifResult = (ForthInt) theInterpreter.popFromForthStack();
    // if( -1 != ifResult.getThisInt()) {
    // //System.out.println("Error: forthIf failed with result "+ifResult.getThisInt()
    // //+", the parse True/False list may not have been built correctly");
    // }
    // if( !theInterpreter.forthStackIsEmpty()) {
    // //System.out.println("Error: forthIf stack not empty");
    // }
    //
    // // Test String
    //
    // ForthBool cond = new ForthBool();
    // cond.Set(false);
    //
    // testTrueList.clear();
    // testFalseList.clear();
    //
    // testTrueList.add("shoot!");
    // testFalseList.add(".\"no one there\"");
    //
    // theInterpreter.pushToForthStack(cond);
    // theInterpreter.forthIf(testTrueList, testFalseList);
    //
    // theInterpreter.printStack();
    // theInterpreter.forthDrop();
    // if( !theInterpreter.forthStackIsEmpty()) {
    // //System.out.println("Error: forthIf stack not empty");
    // }
    //
    // // Begin test
    //
    // String beginTest = ": test 0 begin dup 1 + dup 5 => until ; test ";
    //
    // theInterpreter.parseCode(beginTest);
    // theInterpreter.printStack();
    //
    // while( !theInterpreter.forthStackIsEmpty() ) {
    // theInterpreter.popFromForthStack();
    // }
    //
    // /* String aiTest =
    // ": empty? false ;"
    // + " : shoot! 42 dup ;"
    // + " variable lastShot ;"
    // + " : init 0 lastShot ! ;"
    // + " : play ( -- ) 0"
    // + " begin dup 4 ="
    // + " if shoot!"
    // + " else dup then "
    // + " dup 1 + dup 5 => until ;";
    //
    //
    // theInterpreter.parseCode(aiTest);
    // theInterpreter.printStack();*/
    //
    //// String testForth = "5 0 do .\"loop1 executed\" 2 0 do .\"loop2 executed\" loop loop ";
    //// theInterpretter.parseCode(testForth);
    //// theInterpretter.printStack();
  }
}
