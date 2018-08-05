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

/**
 * This TreeNode class allows for a generically typed value.
 * @param <T> generically typed to accept various data types for the val property
 */
public class TreeNode<T> {

  private T val;
  private TreeNode<T> left;
  private TreeNode<T> right;

  /**
   * Creates a TreeNode with a given value, and null children
   * @param val The value of the given node
   */
  public TreeNode(T val) {
    this.val = val;
    this.left = null;
    this.right = null;
  }

  T getVal() {
    return val;
  }

  TreeNode<T> getLeft() {
    return left;
  }

  void setLeft(TreeNode<T> left) {
    this.left = left;
  }

  TreeNode<T> getRight() {
    return right;
  }

  void setRight(TreeNode<T> right) {
    this.right = right;
  }

  @Override
  public String toString() {
    return val.toString();
  }

}
