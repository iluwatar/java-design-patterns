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

package com.iluwatar.doubledispatch;

/**
 * Rectangle has coordinates and can be checked for overlap against other Rectangles.
 */
public class Rectangle {

  private int left;
  private int top;
  private int right;
  private int bottom;

  /**
   * Constructor.
   */
  public Rectangle(int left, int top, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  public int getLeft() {
    return left;
  }

  public int getTop() {
    return top;
  }

  public int getRight() {
    return right;
  }

  public int getBottom() {
    return bottom;
  }

  boolean intersectsWith(Rectangle r) {
    return !(r.getLeft() > getRight() || r.getRight() < getLeft() || r.getTop() > getBottom() || r
        .getBottom() < getTop());
  }

  @Override
  public String toString() {
    return String.format("[%d,%d,%d,%d]", getLeft(), getTop(), getRight(), getBottom());
  }
}
