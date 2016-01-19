package com.iluwatar.doubledispatch;

/**
 * 
 * Rectangle has coordinates and can be checked for overlap against other Rectangles.
 *
 */
public class Rectangle {

  private int left;
  private int top;
  private int right;
  private int bottom;

  /**
   * Constructor
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
