/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.nullobject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Date: 12/26/15 - 11:47 PM
 *
 * @author Jeroen Meulemeester
 */
public class NullNodeTest {

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
  }

}
