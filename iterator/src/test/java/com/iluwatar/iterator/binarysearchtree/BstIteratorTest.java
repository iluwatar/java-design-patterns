/**
 * The MIT License Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.iterator.binarysearchtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.iterator.AppTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class BstIteratorTest<T> extends AppTest {

  private TreeNode<T> nonEmptyRoot;
  private TreeNode<T> emptyRoot;

  @BeforeAll
  void createTrees() {
    nonEmptyRoot = new TreeNode(Integer.valueOf(5));
    nonEmptyRoot.setLeft(new TreeNode(3));
    nonEmptyRoot.setRight(new TreeNode(7));
    nonEmptyRoot.getLeft().setLeft(new TreeNode(1));
    nonEmptyRoot.getLeft().setRight(new TreeNode(4));
    nonEmptyRoot.getRight().setLeft(new TreeNode(6));

    emptyRoot = null;
  }

  @Test
  void nextWithEmptyTree() {
    BstIterator iter = new BstIterator(emptyRoot);
    assertThrows(IllegalStateException.class, iter::next,
        "next() should throw an IllegalStateException if hasNext() is false.");
  }

  @Test
  void nextWithPopulatedTree() {
    BstIterator iter = new BstIterator(nonEmptyRoot);
    assertEquals(iter.next().getVal(), 1, "First Node is 1.");
    assertEquals(iter.next().getVal(), 3, "Second Node is 3.");
    assertEquals(iter.next().getVal(), 4, "Third Node is 4.");
    assertEquals(iter.next().getVal(), 5, "Fourth Node is 5.");
    assertEquals(iter.next().getVal(), 6, "Fifth Node is 6.");
    assertEquals(iter.next().getVal(), 7, "Sixth Node is 7.");
  }

  @Test
  void hasNextWithEmptyTree() {
    BstIterator iter = new BstIterator(emptyRoot);
    assertFalse(iter.hasNext(), "hasNext() should return false for empty tree.");
  }

  @Test
  void hasNextWithPopulatedTree() {
    BstIterator iter = new BstIterator(nonEmptyRoot);
    assertTrue(iter.hasNext(), "hasNext() should return true for populated tree.");
  }

  @Test
  void nextAndHasNextAcrossEntirePopulatedTree() {
    BstIterator iter = new BstIterator(nonEmptyRoot);
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(iter.next().getVal(), 1, "First Node is 1.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(iter.next().getVal(), 3, "Second Node is 3.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(iter.next().getVal(), 4, "Third Node is 4.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(iter.next().getVal(), 5, "Fourth Node is 5.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(iter.next().getVal(), 6, "Fifth Node is 6.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(iter.next().getVal(), 7, "Sixth Node is 7.");
    assertFalse(iter.hasNext(), "Iterator hasNext() should be false, end of tree.");
  }

}
