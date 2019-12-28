/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

/**
 * TreeNode Class, representing one node in a Binary Search Tree. Allows for a generically typed
 * value.
 *
 * @param <T> generically typed to accept various data types for the val property
 */
public class TreeNode<T extends Comparable<T>> {

  private T val;
  private TreeNode<T> left;
  private TreeNode<T> right;

  /**
   * Creates a TreeNode with a given value, and null children.
   *
   * @param val The value of the given node
   */
  public TreeNode(T val) {
    this.val = val;
    this.left = null;
    this.right = null;
  }

  public T getVal() {
    return val;
  }

  public TreeNode<T> getLeft() {
    return left;
  }

  private void setLeft(TreeNode<T> left) {
    this.left = left;
  }

  public TreeNode<T> getRight() {
    return right;
  }

  private void setRight(TreeNode<T> right) {
    this.right = right;
  }

  /**
   * Inserts new TreeNode based on a given value into the subtree represented by self.
   *
   * @param valToInsert The value to insert as a new TreeNode
   */
  public void insert(T valToInsert) {
    var parent = getParentNodeOfValueToBeInserted(valToInsert);
    parent.insertNewChild(valToInsert);
  }

  /**
   * Fetch the Parent TreeNode for a given value to insert into the BST.
   *
   * @param valToInsert Value of the new TreeNode to be inserted
   * @return Parent TreeNode of `valToInsert`
   */
  private TreeNode<T> getParentNodeOfValueToBeInserted(T valToInsert) {
    TreeNode<T> parent = null;
    var curr = this;

    while (curr != null) {
      parent = curr;
      curr = curr.traverseOneLevelDown(valToInsert);
    }

    return parent;
  }

  /**
   * Returns left or right child of self based on a value that would be inserted; maintaining the
   * integrity of the BST.
   *
   * @param value The value of the TreeNode that would be inserted beneath self
   * @return The child TreeNode of self which represents the subtree where `value` would be inserted
   */
  private TreeNode<T> traverseOneLevelDown(T value) {
    if (this.isGreaterThan(value)) {
      return this.left;
    }
    return this.right;
  }

  /**
   * Add a new Child TreeNode of given value to self. WARNING: This method is destructive (will
   * overwrite existing tree structure, if any), and should be called only by this class's insert()
   * method.
   *
   * @param valToInsert Value of the new TreeNode to be inserted
   */
  private void insertNewChild(T valToInsert) {
    if (this.isLessThanOrEqualTo(valToInsert)) {
      this.setRight(new TreeNode<>(valToInsert));
    } else {
      this.setLeft(new TreeNode<>(valToInsert));
    }
  }

  private boolean isGreaterThan(T val) {
    return this.val.compareTo(val) > 0;
  }

  private boolean isLessThanOrEqualTo(T val) {
    return this.val.compareTo(val) < 1;
  }

  @Override
  public String toString() {
    return val.toString();
  }

}
