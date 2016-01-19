package com.iluwatar.doubledispatch;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for Rectangle
 */
public class RectangleTest {

  /**
   * Test if the values passed through the constructor matches the values fetched from the getters
   */
  @Test
  public void testConstructor() {
    final Rectangle rectangle = new Rectangle(1, 2, 3, 4);
    assertEquals(1, rectangle.getLeft());
    assertEquals(2, rectangle.getTop());
    assertEquals(3, rectangle.getRight());
    assertEquals(4, rectangle.getBottom());
  }

  /**
   * Test if the values passed through the constructor matches the values in the {@link
   * #toString()}
   */
  @Test
  public void testToString() throws Exception {
    final Rectangle rectangle = new Rectangle(1, 2, 3, 4);
    assertEquals("[1,2,3,4]", rectangle.toString());
  }

  /**
   * Test if the {@link Rectangle} class can detect if it intersects with another rectangle.
   */
  @Test
  public void testIntersection() {
    assertTrue(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(0, 0, 1, 1)));
    assertTrue(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(-1, -5, 7, 8)));
    assertFalse(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(2, 2, 3, 3)));
    assertFalse(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(-2, -2, -1, -1)));
  }

}
