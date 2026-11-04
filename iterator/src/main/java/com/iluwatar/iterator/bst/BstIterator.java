package com.iluwatar.iterator.bst;

import com.iluwatar.iterator.Iterator;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * An in-order iterator over a binary search tree. For example, a BST of Integer values yields its
 * nodes in natural order (1, 2, 3...).
 *
 * <p>Uses O(h) extra space, where h is the height of the tree, by keeping a stack of the nodes
 * still to be visited. The tree must not be modified during iteration, as this iterator does not
 * detect such changes.
 *
 * @param <T> the type of value stored in the tree nodes
 */
public class BstIterator<T extends Comparable<T>> implements Iterator<TreeNode<T>> {

  private final ArrayDeque<TreeNode<T>> pathStack = new ArrayDeque<>();

  /**
   * Creates an iterator positioned at the smallest node.
   *
   * @param root the root of the tree, or null for an empty tree
   */
  public BstIterator(TreeNode<T> root) {
    pushPathToNextSmallest(root);
  }

  /**
   * Pushes the given node and all of its left descendants onto the stack.
   *
   * @param subtreeRoot the root of the subtree to descend, or null
   */
  private void pushPathToNextSmallest(TreeNode<T> subtreeRoot) {
    for (var node = subtreeRoot; node != null; node = node.getLeft()) {
      pathStack.push(node);
    }
  }

  /**
   * Checks whether another node is available.
   *
   * @return true if this iterator has a next node
   */
  @Override
  public boolean hasNext() {
    return !pathStack.isEmpty();
  }

  /**
   * Returns the next node in in-order traversal.
   *
   * @return the next node
   * @throws NoSuchElementException if there are no more nodes
   */
  @Override
  public TreeNode<T> next() {
    if (pathStack.isEmpty()) {
      throw new NoSuchElementException();
    }
    var current = pathStack.pop();
    pushPathToNextSmallest(current.getRight());
    return current;
  }
}