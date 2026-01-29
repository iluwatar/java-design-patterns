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
package com.iluwatar.iterator.bst

// ABOUTME: Represents a node in a generic Binary Search Tree with insert and traversal support.
// ABOUTME: Maintains BST invariant where left children are smaller and right children are larger.

/**
 * TreeNode Class, representing one node in a Binary Search Tree. Allows for a generically typed
 * value.
 *
 * @param T generically typed to accept various data types for the val property
 */
class TreeNode<T : Comparable<T>>(
    val `val`: T,
    var left: TreeNode<T>? = null,
    var right: TreeNode<T>? = null
) {

    /**
     * Inserts new TreeNode based on a given value into the subtree represented by self.
     *
     * @param valToInsert The value to insert as a new TreeNode
     */
    fun insert(valToInsert: T) {
        val parent = getParentNodeOfValueToBeInserted(valToInsert)
        parent.insertNewChild(valToInsert)
    }

    /**
     * Fetch the Parent TreeNode for a given value to insert into the BST.
     *
     * @param valToInsert Value of the new TreeNode to be inserted
     * @return Parent TreeNode of `valToInsert`
     */
    private fun getParentNodeOfValueToBeInserted(valToInsert: T): TreeNode<T> {
        var parent: TreeNode<T>? = null
        var curr: TreeNode<T>? = this

        while (curr != null) {
            parent = curr
            curr = curr.traverseOneLevelDown(valToInsert)
        }

        return parent!!
    }

    /**
     * Returns left or right child of self based on a value that would be inserted; maintaining the
     * integrity of the BST.
     *
     * @param value The value of the TreeNode that would be inserted beneath self
     * @return The child TreeNode of self which represents the subtree where `value` would be inserted
     */
    private fun traverseOneLevelDown(value: T): TreeNode<T>? {
        return if (isGreaterThan(value)) left else right
    }

    /**
     * Add a new Child TreeNode of given value to self. WARNING: This method is destructive (will
     * overwrite existing tree structure, if any), and should be called only by this class's insert()
     * method.
     *
     * @param valToInsert Value of the new TreeNode to be inserted
     */
    private fun insertNewChild(valToInsert: T) {
        if (isLessThanOrEqualTo(valToInsert)) {
            right = TreeNode(valToInsert)
        } else {
            left = TreeNode(valToInsert)
        }
    }

    private fun isGreaterThan(value: T): Boolean = `val`.compareTo(value) > 0

    private fun isLessThanOrEqualTo(value: T): Boolean = `val`.compareTo(value) < 1

    override fun toString(): String = `val`.toString()
}
