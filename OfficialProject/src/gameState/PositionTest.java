package gameState;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PositionTest {

  @Test
  public void testGetCubicalPosition() {
    assertEquals(new Position(1, 3).getCubicalPosition(), new CubicalPosition(1, -4, 3));
    assertEquals(new Position(0, 0).getCubicalPosition(), new CubicalPosition(0, 0, 0));
    assertEquals(new Position(1, 1).getCubicalPosition(), new CubicalPosition(1, -2, 1));
  }
}
