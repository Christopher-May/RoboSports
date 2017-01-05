package gameState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import gameMaster.TeamColour;
import units.Unit;
import units.UnitFactory;

public class GameBoardHexTest {
  /**
   * Testing for all methods in GameBoardHex was performed here, getters for fields and getPoint,
   * getPoint is visually tested with
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Test
  public void testChangeHiglight() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    boolean higl = hex.isHighlighted();
    hex.changeHiglight();
    assertNotEquals(higl, hex.isHighlighted());

  }

  @Test
  public void testHighlight() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    hex.highlight();
    assertEquals(hex.isHighlighted(), true);

  }

  @Test
  public void testUnhighlight() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    hex.unhighlight();
    assertEquals(hex.isHighlighted(), false);
  }

  @Test
  public void testGetUnits() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    assertEquals(0, hex.getUnits().size());
    Unit testUnit = UnitFactory.getScout(new Position(0, 0), TeamColour.BLUE);;
    hex.addUnit(testUnit);
    assertEquals(1, hex.getUnits().size());
    assertEquals(hex.getUnits().contains(testUnit), true);

  }

  @Test
  public void testGetAliveUnits() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    assertEquals(0, hex.getAliveUnits().size());
    Unit testUnit1 = UnitFactory.getScout(new Position(0, 0), TeamColour.BLUE);
    Unit testUnit2 = UnitFactory.getScout(new Position(0, 0), TeamColour.BLUE);
    hex.addUnit(testUnit1);
    assertEquals(1, hex.getAliveUnits().size());
    assertEquals(hex.getAliveUnits().contains(testUnit1), true);
    hex.addUnit(testUnit2);
    assertEquals(2, hex.getAliveUnits().size());
    assertEquals(hex.getAliveUnits().contains(testUnit2), true);
    testUnit1.alive = false;
    assertEquals(hex.getAliveUnits().contains(testUnit1), false);
    assertEquals(hex.getAliveUnits().contains(testUnit2), true);

  }

  @Test
  public void testGetDeadUnits() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    assertEquals(0, hex.getAliveUnits().size());
    Unit testUnit1 = UnitFactory.getScout(new Position(0, 0), TeamColour.BLUE);
    Unit testUnit2 = UnitFactory.getScout(new Position(0, 0), TeamColour.BLUE);
    hex.addUnit(testUnit1);
    assertEquals(0, hex.getDeadUnits().size());
    hex.addUnit(testUnit2);
    testUnit1.alive = false;
    assertEquals(hex.getDeadUnits().contains(testUnit2), false);
    assertEquals(hex.getDeadUnits().contains(testUnit1), true);

  }

  @Test
  public void testAddUnitExpectedExceptionPosition() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    Unit testUnit1 = UnitFactory.getScout(new Position(0, 1), TeamColour.BLUE);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Position of unit should be equal to position of hex");
    hex.addUnit(testUnit1);
  }

  @Test
  public void testAddUnitExpectedExceptionUnit() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    Unit testUnit1 = UnitFactory.getScout(new Position(0, 0), TeamColour.BLUE);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Unit already present on hex, this should never happen");
    hex.addUnit(testUnit1);
    hex.addUnit(testUnit1);
  }

  @Test
  public void testAddUnit() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    assertEquals(0, hex.getUnits().size());
    Unit testUnit = UnitFactory.getScout(new Position(0, 0), TeamColour.BLUE);;
    hex.addUnit(testUnit);
    assertEquals(1, hex.getUnits().size());
    assertEquals(hex.getUnits().contains(testUnit), true);
  }

  @Test
  public void testRemoveUnit() {
    GameBoardHex hex = new GameBoardHex(new Position(0, 0));
    assertEquals(0, hex.getUnits().size());
    Unit testUnit = UnitFactory.getScout(new Position(0, 0), TeamColour.BLUE);;
    hex.addUnit(testUnit);
    hex.removeUnit(testUnit);
    assertEquals(hex.getUnits().contains(testUnit), false);
  }

}
