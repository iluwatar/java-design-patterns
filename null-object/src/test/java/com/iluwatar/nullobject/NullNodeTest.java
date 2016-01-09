package com.iluwatar.nullobject;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Date: 12/26/15 - 11:47 PM
 *
 * @author Jeroen Meulemeester
 */
public class NullNodeTest extends StdOutTest {

  /**
   * Verify if {@link NullNode#getInstance()} actually returns the same object instance
   */
  @Test
  public void testGetInstance() {
    final NullNode instance = NullNode.getInstance();
    assertNotNull(instance);
    assertSame(instance, NullNode.getInstance());
  }

  @Test
  public void testFields() {
    final NullNode node = NullNode.getInstance();
    assertEquals(0, node.getTreeSize());
    assertNull(node.getName());
    assertNull(node.getLeft());
    assertNull(node.getRight());
  }

  @Test
  public void testWalk() throws Exception {
    NullNode.getInstance().walk();
    Mockito.verifyZeroInteractions(getStdOutMock());
  }

}