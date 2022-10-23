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

import com.iluwatar.iterator.Iterator;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * An in-order implementation of a BST Iterator. For example, given a BST with Integer values,
 * expect to retrieve TreeNodes according to the Integer's natural ordering (1, 2, 3...)
 *
 * @param <T> This Iterator has been implemented with generic typing to allow for TreeNodes of
 *            different value types
 */
public class BstIterator<T extends Comparable<T>> implements Iterator<TreeNode<T>> {

  private final ArrayDeque<TreeNode<T>> pathStack;

  public BstIterator(TreeNode<T> root) {
    pathStack = new ArrayDeque<>();
    pushPathToNextSmallest(root);
  }

  /**
   * This BstIterator manages to use O(h) extra space, where h is the height of the tree It achieves
   * this by maintaining a stack of the nodes to handle (pushing all left nodes first), before
   * handling self or right node.
   *
   * @param node TreeNode that acts as root of the subtree we're interested in.
   */
  private void pushPathToNextSmallest(TreeNode<T> node) {
    while (node != null) {
      pathStack.push(node);
      node = node.getLeft();
    }
  }

  /**
   * Checks if there exists next element.
   *
   * @return true if this iterator has a "next" element
   */
  @Override
  public boolean hasNext() {
    return !pathStack.isEmpty();
  }

  /**
   * Gets the next element.
   *
   * @return TreeNode next. The next element according to our in-order traversal of the given BST
   * @throws NoSuchElementException if this iterator does not have a next element
   */
  @Override
  public TreeNode<T> next() throws NoSuchElementException {
    if (pathStack.isEmpty()) {
      throw new NoSuchElementException();
    }
    var next = pathStack.pop();
    pushPathToNextSmallest(next.getRight());
    return next;
  }

}
