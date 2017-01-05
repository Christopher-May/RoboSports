package units;

import java.awt.Image;
import java.util.ArrayList;

import gameMaster.TeamColour;
import gameState.GameBoardHex;
import gameState.Position;

/**
 * The abstract class that every unit type uses.
 *
 */
public abstract class Unit {

  /** The health that this Unit has left for the game */
  private int healthLeft;

  /** The health this Unit type has at the beginning of a game */
  private int health;

  /** The power of this Unit's attack */
  private int attack;

  /** The number of moves this Unit type has initially */
  private int moves;

  /** The number of moves this Unit has left for the turn */
  public int movesLeft;

  /** The range of this Unit type's attack */
  private int range;

  /** The current board position of this Unit */
  public Position myPosition;

  /** The direction this Unit is facing */
  private int direction;

  /** The graphic this Unit type is displayed with */
  private Image graphic;

  /** The team of this Unit (e.g., "RED" or "BLUE") */
  private TeamColour myTeam;

  /** Whether or not this unit has fired yet */
  private Boolean hasFired;

  /** A flag to keep track of whether this Unit has moved the current round yet. */
  public Boolean hasMoved;

  /** alive is true if the unit is alive, false otherwise */
  public Boolean alive;
  
  public Boolean isHighlighted;

  /** The Unit's type */
  public String type;

  public Unit(int attack, int movement, int health, int range, Image graphic, String type,
      Position initialPosition, TeamColour teamColour) {
    this.attack = attack;
    this.moves = movement;
    this.range = range;
    this.health = health;
    this.graphic = graphic;
    this.type = type;
    this.myPosition = initialPosition;
    this.myTeam = teamColour;
    this.direction = teamColour.getInitialDirection();
    this.hasMoved = false;
    this.hasFired = false;
    this.isHighlighted = false;
    initStats();
  }

  /**
   * Check if the distance is in the range of this Unit
   * 
   * @param distance A distance from this Unit in tiles
   * @return True if the distance is in range, False otherwise.
   */
  private boolean inRange(int distance) {
    if (distance >= 0 && distance <= this.range) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Attempt to shoot the tile.
   * 
   * @param tile The tile which is being shot.
   * @param distance the distance of the tile from this Unit
   */
  public void shoot(GameBoardHex tile, int distance) {
    // System.out.println("trying to fire...");
    if (!hasFired && inRange(distance)) {
      // System.out.println("fired!");
      ArrayList<Unit> unitsToDamage = tile.getUnits();
      for (Unit tileUnit : unitsToDamage) {
        if (null == tileUnit) {
          throw new NullPointerException(
              "The units on the tile we were trying to shoot were null!");
        }
        tileUnit.takeDamage(this.attack);
      }
      this.hasFired = true;
    }

  }

  /**
   * Update the Unit's position to the position of the requested tile, and adjust the Unit's
   * remaining moves.
   * 
   * @param tile the tile we are updating the position to.
   */
  public void move(GameBoardHex tile) {
  	if(this.alive) {
  		this.myPosition = tile.getPosition();
  		tile.addUnit(this);
  		this.movesLeft--;
  	}
  }

  /** Set this Unit's hasMoved flag to true. */
  public void moved() {
    this.hasMoved = true;
  }

  /**
   * a simple method to initialized the movesLeft to the max moves and the healthLeft to the max
   * health. Method is used in part of initialization
   */
  public void initStats() {
    this.movesLeft = this.moves;
    this.healthLeft = this.health;
    this.alive = true;
    this.hasFired = false;
  }

  /**
   * a method to set the remaining health to the max health
   */
  public void initHealth() {
    this.healthLeft = this.health;
  }

  /** Set this Unit's 'hasFired' flag to false */
  public void resetShot() {
    this.hasFired = false;
  }

  /**
   * Reset this Unit's 'movesLeft' to its type's movement, and reset its hasMoved flag
   */
  public void resetMovement() {
    this.movesLeft = this.moves;
    this.hasMoved = false;
  }

  /**
   * Takes 'damage' damage and reduces health of this Unit. If this Unit's health falls below 1,
   * this Unit dies.
   * 
   * @param damage the amount of damage we are to take.
   */
  public void takeDamage(int damage) {
    // System.out.println(this.getTeamName() + " taking " + damage + " damage.");
    this.healthLeft = this.healthLeft - damage;
    if (0 >= this.healthLeft) {
      this.alive = false;
      // System.out.println("dead.");
    }
  }

  /**
   * Turn this Unit left by incrementing their direction. A Unit's direction goes between 0 and 5.
   */
  public void turnLeft() {
    this.direction++;
    if (6 == this.direction) {
      this.direction = 0;
    }
  }

  /**
   * Turn this Unit right by incrementing their direction. A Unit's direction goes between 0 and 5.
   */
  public void turnRight() {
    this.direction--;
    if (-1 == this.direction) {
      this.direction = 5;
    }
  }

  public Position getPosition() {
    return this.myPosition;
  }

  public Image getImage() {
    return this.graphic;
  }

  public int getRange() {
    return range;
  }

  public int getDirection() {
    return direction;
  }

  public int getInitialMovement() {
    return this.moves;
  }

  public int getMovesLeft() {
    return this.movesLeft;
  }

  public int getMaxHealth() {
    return this.health;
  }

  public int getRemainingHealth() {
    return this.healthLeft;
  }

  public int getAttack() {
    return this.attack;
  }

  public String getType() {
    return this.type;
  }

  public TeamColour getTeamName() {
    return myTeam;
  }

  public void setPosition(Position newPos) {
    this.myPosition = newPos;
  }

}
