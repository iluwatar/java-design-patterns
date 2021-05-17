package com.iluwatar.composemethod;

/**
 * the data structure to create list.
 */
public class Node { //NOPMD
  /**
   * the list of numbers.
   */
  private int[] nums;
  /**
   * the pointer of the next node.
   */
  private Node next;

  /**
   * set number array.
   *
   * @param nums number array to set.
   */
  public void setNums(int[] nums) {
    this.nums = nums;
  }

  /**
   * get number array.
   *
   * @return number array.
   */
  public int[] getNums() {
    return this.nums;
  }

  /**
   * set next node.
   *
   * @param next next node to set.
   */
  public void setNext(Node next) {
    this.next = next;
  }

  /**
   * get next node.
   *
   * @return next node.
   */
  public Node getNext() {
    return this.next;
  }
}
