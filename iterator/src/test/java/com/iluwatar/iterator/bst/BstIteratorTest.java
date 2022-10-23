/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.iterator.bst;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class BstIteratorTest {

  private TreeNode<Integer> nonEmptyRoot;
  private TreeNode<Integer> emptyRoot;

  @BeforeAll
  void createTrees() {
    nonEmptyRoot = new TreeNode<>(5);
    nonEmptyRoot.insert(3);
    nonEmptyRoot.insert(7);
    nonEmptyRoot.insert(1);
    nonEmptyRoot.insert(4);
    nonEmptyRoot.insert(6);

    emptyRoot = null;
  }

  @Test
  void nextForEmptyTree() {
    var iter = new BstIterator<>(emptyRoot);
    assertThrows(NoSuchElementException.class, iter::next,
        "next() should throw an IllegalStateException if hasNext() is false.");
  }

  @Test
  void nextOverEntirePopulatedTree() {
    var iter = new BstIterator<>(nonEmptyRoot);
    assertEquals(Integer.valueOf(1), iter.next().getVal(), "First Node is 1.");
    assertEquals(Integer.valueOf(3), iter.next().getVal(), "Second Node is 3.");
    assertEquals(Integer.valueOf(4), iter.next().getVal(), "Third Node is 4.");
    assertEquals(Integer.valueOf(5), iter.next().getVal(), "Fourth Node is 5.");
    assertEquals(Integer.valueOf(6), iter.next().getVal(), "Fifth Node is 6.");
    assertEquals(Integer.valueOf(7), iter.next().getVal(), "Sixth Node is 7.");
  }

  @Test
  void hasNextForEmptyTree() {
    var iter = new BstIterator<>(emptyRoot);
    assertFalse(iter.hasNext(), "hasNext() should return false for empty tree.");
  }

  @Test
  void hasNextForPopulatedTree() {
    var iter = new BstIterator<>(nonEmptyRoot);
    assertTrue(iter.hasNext(), "hasNext() should return true for populated tree.");
  }

  @Test
  void nextAndHasNextOverEntirePopulatedTree() {
    var iter = new BstIterator<>(nonEmptyRoot);
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(Integer.valueOf(1), iter.next().getVal(), "First Node is 1.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(Integer.valueOf(3), iter.next().getVal(), "Second Node is 3.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(Integer.valueOf(4), iter.next().getVal(), "Third Node is 4.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(Integer.valueOf(5), iter.next().getVal(), "Fourth Node is 5.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(Integer.valueOf(6), iter.next().getVal(), "Fifth Node is 6.");
    assertTrue(iter.hasNext(), "Iterator hasNext() should be true.");
    assertEquals(Integer.valueOf(7), iter.next().getVal(), "Sixth Node is 7.");
    assertFalse(iter.hasNext(), "Iterator hasNext() should be false, end of tree.");
  }

}
