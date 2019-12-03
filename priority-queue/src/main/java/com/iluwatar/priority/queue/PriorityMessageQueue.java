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

package com.iluwatar.priority.queue;

import static java.util.Arrays.copyOf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Keep high Priority message on top using maxHeap.
 *
 * @param <T> :  DataType to push in Queue
 */
public class PriorityMessageQueue<T extends Comparable> {

  private static final Logger LOGGER = LoggerFactory.getLogger(PriorityMessageQueue.class);

  private int size = 0;

  private int capacity;


  private T[] queue;

  public PriorityMessageQueue(T[] queue) {
    this.queue = queue;
    this.capacity = queue.length;
  }

  /**
   * Remove top message from queue.
   */
  public T remove() {
    if (isEmpty()) {
      return null;
    }

    final T root = queue[0];
    queue[0] = queue[size - 1];
    size--;
    maxHeapifyDown();
    return root;
  }

  /**
   * Add message to queue.
   */
  public void add(T t) {
    ensureCapacity();
    queue[size] = t;
    size++;
    maxHeapifyUp();
  }

  /**
   * Check queue size.
   */
  public boolean isEmpty() {
    return size == 0;
  }


  private void maxHeapifyDown() {
    int index = 0;
    while (hasLeftChild(index)) {

      int smallerIndex = leftChildIndex(index);

      if (hasRightChild(index) && right(index).compareTo(left(index)) > 0) {
        smallerIndex = rightChildIndex(index);
      }

      if (queue[index].compareTo(queue[smallerIndex]) > 0) {
        break;
      } else {
        swap(index, smallerIndex);
      }

      index = smallerIndex;


    }

  }

  private void maxHeapifyUp() {
    int index = size - 1;
    while (hasParent(index) && parent(index).compareTo(queue[index]) < 0) {
      swap(parentIndex(index), index);
      index = parentIndex(index);
    }
  }


  // index
  private int parentIndex(int pos) {
    return (pos - 1) / 2;
  }

  private int leftChildIndex(int parentPos) {
    return 2 * parentPos + 1;
  }

  private int rightChildIndex(int parentPos) {
    return 2 * parentPos + 2;
  }

  // value
  private T parent(int childIndex) {
    return queue[parentIndex(childIndex)];
  }

  private T left(int parentIndex) {
    return queue[leftChildIndex(parentIndex)];
  }

  private T right(int parentIndex) {
    return queue[rightChildIndex(parentIndex)];
  }

  // check
  private boolean hasLeftChild(int index) {
    return leftChildIndex(index) < size;
  }

  private boolean hasRightChild(int index) {
    return rightChildIndex(index) < size;
  }

  private boolean hasParent(int index) {
    return parentIndex(index) >= 0;
  }

  private void swap(int fpos, int tpos) {
    T tmp = queue[fpos];
    queue[fpos] = queue[tpos];
    queue[tpos] = tmp;
  }

  private void ensureCapacity() {
    if (size == capacity) {
      capacity = capacity * 2;
      queue = copyOf(queue, capacity);
    }
  }

  /**
   * For debug .. print current state of queue
   */
  public void print() {
    for (int i = 0; i <= size / 2; i++) {
      LOGGER.info(" PARENT : " + queue[i] + " LEFT CHILD : "
          + left(i) + " RIGHT CHILD :" + right(i));
    }
  }

}
