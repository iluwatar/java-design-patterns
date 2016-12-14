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

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Date: 12/26/15 - 11:44 PM
 *
 * @author Jeroen Meulemeester
 */
public class TreeTest {

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender();
  }

  @After
  public void tearDown() {
    appender.stop();
  }

  /**
   * During the tests, the same tree structure will be used, shown below. End points will be
   * terminated with the {@link NullNode} instance.
   *
   * <pre>
   * root
   * ├── level1_a
   * │   ├── level2_a
   * │   │   ├── level3_a
   * │   │   └── level3_b
   * │   └── level2_b
   * └── level1_b
   * </pre>
   */
  private static final Node TREE_ROOT;

  static {
    final NodeImpl level1B = new NodeImpl("level1_b", NullNode.getInstance(), NullNode.getInstance());
    final NodeImpl level2B = new NodeImpl("level2_b", NullNode.getInstance(), NullNode.getInstance());
    final NodeImpl level3A = new NodeImpl("level3_a", NullNode.getInstance(), NullNode.getInstance());
    final NodeImpl level3B = new NodeImpl("level3_b", NullNode.getInstance(), NullNode.getInstance());
    final NodeImpl level2A = new NodeImpl("level2_a", level3A, level3B);
    final NodeImpl level1A = new NodeImpl("level1_a", level2A, level2B);
    TREE_ROOT = new NodeImpl("root", level1A, level1B);
  }

  /**
   * Verify the number of items in the tree. The root has 6 children so we expect a {@link
   * Node#getTreeSize()} of 7 {@link Node}s in total.
   */
  @Test
  public void testTreeSize() {
    assertEquals(7, TREE_ROOT.getTreeSize());
  }

  /**
   * Walk through the tree and verify if every item is handled
   */
  @Test
  public void testWalk() {
    TREE_ROOT.walk();

    assertTrue(appender.logContains("root"));
    assertTrue(appender.logContains("level1_a"));
    assertTrue(appender.logContains("level2_a"));
    assertTrue(appender.logContains("level3_a"));
    assertTrue(appender.logContains("level3_b"));
    assertTrue(appender.logContains("level2_b"));
    assertTrue(appender.logContains("level1_b"));
    assertEquals(7, appender.getLogSize());
  }

  @Test
  public void testGetLeft() throws Exception {
    final Node level1 = TREE_ROOT.getLeft();
    assertNotNull(level1);
    assertEquals("level1_a", level1.getName());
    assertEquals(5, level1.getTreeSize());

    final Node level2 = level1.getLeft();
    assertNotNull(level2);
    assertEquals("level2_a", level2.getName());
    assertEquals(3, level2.getTreeSize());

    final Node level3 = level2.getLeft();
    assertNotNull(level3);
    assertEquals("level3_a", level3.getName());
    assertEquals(1, level3.getTreeSize());
    assertSame(NullNode.getInstance(), level3.getRight());
    assertSame(NullNode.getInstance(), level3.getLeft());
  }

  @Test
  public void testGetRight() throws Exception {
    final Node level1 = TREE_ROOT.getRight();
    assertNotNull(level1);
    assertEquals("level1_b", level1.getName());
    assertEquals(1, level1.getTreeSize());
    assertSame(NullNode.getInstance(), level1.getRight());
    assertSame(NullNode.getInstance(), level1.getLeft());
  }

  private class InMemoryAppender extends AppenderBase<ILoggingEvent> {
    private List<ILoggingEvent> log = new LinkedList<>();

    public InMemoryAppender() {
      ((Logger) LoggerFactory.getLogger("root")).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    public boolean logContains(String message) {
      return log.stream().anyMatch(event -> event.getMessage().equals(message));
    }

    public int getLogSize() {
      return log.size();
    }
  }

}
