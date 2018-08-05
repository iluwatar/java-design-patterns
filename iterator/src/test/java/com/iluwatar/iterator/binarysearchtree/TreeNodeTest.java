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
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Comprehensive tests for TreeNode POJO
 * @param <T> generically typed for TreeNode
 */
public class TreeNodeTest<T> {

  @Test
  void treeNodeGetters() {
    TreeNode<T> node = new TreeNode(1);
    assertEquals(node.getVal(), 1);
    assertNull(node.getLeft(), "Left child should be initialized as null.");
    assertNull(node.getRight(), "Right child should be initialized as null.");
  }

  @Test
  void treeNodeSetters() {
    TreeNode<T> node = new TreeNode(3);
    node.setLeft(new TreeNode(1));
    assertEquals(node.getLeft().getVal(), 1, "Left node has a value of 1.");
    node.setRight(new TreeNode(5));
    assertEquals(node.getRight().getVal(), 5, "Right node has a value of 5");
  }

  @Test
  void treeNodeToString() {
    TreeNode<T> node = new TreeNode(3);
    assertEquals(node.toString(), "3", "node.toString() should return string value.");
  }

}
