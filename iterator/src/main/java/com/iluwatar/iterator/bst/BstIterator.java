/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
 * BST迭代器的有序实现。例如，给定一个具有Integer值的BST，期望根据Integer的自然顺序(1,2,3…)检索TreeNodes
 *
 * @param <T> This Iterator has been implemented with generic typing to allow for TreeNodes of
 *            different value types
 *           这个迭代器是用泛型类型实现的，允许使用不同值类型的TreeNodes
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
   * 这个BstIterator设法使用O(h)额外的空间，其中h是树的高度。它通过维护一个要处理的节点堆栈(首先推入所有左节点)来实现这一点，然后再处理self或右节点。
   *
   * @param node TreeNode that acts as root of the subtree we're interested in.
   *             TreeNode作为我们感兴趣的子树的根。
   */
  private void pushPathToNextSmallest(TreeNode<T> node) {
    while (node != null) {
      pathStack.push(node);
      node = node.getLeft();
    }
  }

  /**
   * Checks if there exists next element.
   * 检查是否存在下一个元素。
   *
   * @return true if this iterator has a "next" element
   *      如果该迭代器具有“next”元素，则为True
   */
  @Override
  public boolean hasNext() {
    return !pathStack.isEmpty();
  }

  /**
   * Gets the next element.
   *
   * @return TreeNode next. The next element according to our in-order traversal of the given BST
   *                        根据给定BST的顺序遍历下一个元素
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
