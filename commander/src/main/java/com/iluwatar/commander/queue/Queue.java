/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Sepp�l�
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

package com.iluwatar.commander.queue;

import com.iluwatar.commander.exceptions.IsEmptyException;

/**
 * Queue data structure implementation.
 * @param <T> is the type of object the queue will hold.
 */

public class Queue<T> {

  Node<T> front;
  Node<T> rear;
  public int size = 0;

  class Node<T> {
    T value;
    Node<T> next;

    Node(T obj, Node<T> b) {
      value = obj;
      next = b;
    }
  }

  /**
   * Queue constructor
   */
  
  Queue() {
    front = null;
    rear = null;
    size = 0;
  }
  
  boolean isEmpty() {
    if (size == 0) {
      return true;
    } else {
      return false;
    }
  }
  
  void enqueue(T obj) {
    if (front == null) {
      front = new Node(obj, null);
      rear = front;
    } else {
      Node temp = new Node(obj, null);
      rear.next = temp;
      rear = temp;
    }
    size++;
  }
  
  T dequeue() throws IsEmptyException {
    if (isEmpty()) {
      throw new IsEmptyException();
    } else {
      Node temp = front;
      front = front.next;
      size = size - 1;
      return ((T) temp.value);
    }
  }
  
  T peek() throws IsEmptyException {
    if (isEmpty()) {
      throw new IsEmptyException();
    } else {
      return ((T)front.value);
    }
  }
}
