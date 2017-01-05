/**
 * 
 */
package gameState;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author gauravarora
 */
public class CubicalPositionTest {

  @Test
  public void testDirection() {
    assert CubicalPosition.direction(0, 0)
        .equals(CubicalPosition.direction(0)) : "Direction 0 0 0 Doesn't work";
    assert CubicalPosition.direction(1, 0)
        .equals(CubicalPosition.direction(1)) : "Direction 1 0 1 Doesn't work";
    assert CubicalPosition.direction(5, 1)
        .equals(CubicalPosition.direction(0)) : "Direction 5 1 0 Doesn't work";
    assert CubicalPosition.direction(0, 5)
        .equals(CubicalPosition.direction(5)) : "Direction 0 5 5 Doesn't work";
    assert CubicalPosition.direction(4, 5)
        .equals(CubicalPosition.direction(3)) : "Direction 4 5 3 Doesn't work";
  }

  @Test(expected = IllegalCubicalPosition.class)
  public void testCubicalPositionException() {
    @SuppressWarnings("unused")
    CubicalPosition cubicalPosition = new CubicalPosition(1, 2, 2);
  }

  @Test
  public void testCubicalPositionNoException() {
    @SuppressWarnings("unused")
    CubicalPosition cubicalPosition = new CubicalPosition(1, 0, -1);
  }

  @Test
  public void testgetPosition() {
    assertEquals(new CubicalPosition(1, 0, -1).getPosition(), new Position(1, -1));
    assertEquals(new CubicalPosition(9, 4, -13).getPosition(), new Position(9, -13));
    assertEquals(new CubicalPosition(-1, 0, 1).getPosition(), new Position(-1, 1));
    assertEquals(new CubicalPosition(0, 0, 0).getPosition(), new Position(0, 0));

  }

  @Test
  public void testAddCubicalPosition() {

    assertEquals(new CubicalPosition(1, 0, -1).addCubicalPosition(new CubicalPosition(1, 0, -1)),
        new CubicalPosition(2, 0, -2));
    assertEquals(new CubicalPosition(0, 0, 0).addCubicalPosition(new CubicalPosition(2, 0, -2)),
        new CubicalPosition(2, 0, -2));
    assertEquals(new CubicalPosition(2, 5, -7).addCubicalPosition(new CubicalPosition(-2, -5, 7)),
        new CubicalPosition(0, 0, 0));
  }


  public void testSubtractCubicalPosition() {

    assertEquals(
        new CubicalPosition(1, 0, -1).subtractCubicalPosition(new CubicalPosition(1, 0, -1)),
        new CubicalPosition(0, 0, 0));
    assertEquals(
        new CubicalPosition(0, 0, 0).subtractCubicalPosition(new CubicalPosition(2, 0, -2)),
        new CubicalPosition(-2, 0, 2));
    assertEquals(
        new CubicalPosition(2, 5, -7).subtractCubicalPosition(new CubicalPosition(-2, -5, 7)),
        new CubicalPosition(4, 10, -14));

  }


  @Test
  public void testScaleCubicalPosition() {
    assertEquals((new CubicalPosition(1, 0, -1).scaleCubicalPosition(2)),
        new CubicalPosition(2, 0, -2));
    assertEquals((new CubicalPosition(0, 0, 0).scaleCubicalPosition(1000)),
        new CubicalPosition(0, 0, 0));
  }

  @Test
  public void testLength() {
    assertEquals(new CubicalPosition(1, 0, -1).length(), 1);
    assertEquals(new CubicalPosition(2, 0, -2).length(), 2);
    assertEquals(new CubicalPosition(3, -1, -2).length(), 3);
  }

  @Test
  public void testDistance() {
    assertEquals(new CubicalPosition(1, 0, -1).distance(new CubicalPosition(1, 0, -1)), 0);
    assertEquals(new CubicalPosition(1, 0, -1).distance(new CubicalPosition(3, -1, -2)), 2);
    assertEquals(new CubicalPosition(3, -3, 0).distance(new CubicalPosition(3, 0, -3)), 3);
    assertEquals(new CubicalPosition(-4, 4, 0).distance(new CubicalPosition(4, -4, 0)), 8);
    assertEquals(new CubicalPosition(0, -2, 2).distance(new CubicalPosition(-1, -3, 4)), 2);
  }



}
