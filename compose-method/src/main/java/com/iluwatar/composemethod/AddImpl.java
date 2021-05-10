package com.iluwatar.composemethod;

/**
 * implement adding.
 */
public class AddImpl { //NOPMD
  /**
   * default added number.
   */
  private static final int DEFAULT_NUMBER = 0;

  /**
   * original add method.
   *
   * @param head the head of the list.
   * @return the sum of positive numbers.
   */
  public static int addPositiveNodesOriginal(final Node head) {
    Node tail = head;
    int rtn = 0;
    while (tail != null) {
      if (tail.nums.length > 0) {
        for (int i = 0; i < tail.nums.length; i++) {
          if (tail.nums[i] > 0) {
            rtn += tail.nums[i];
          }
        }
      }
      tail = tail.next;
    }
    return rtn;
  }

  /**
   * refined adding method.
   *
   * @param head the head of the list.
   * @return the sum of the positive number.
   */
  public static int addPositiveNodesRefined(final Node head) {
    Node tail = head;
    int rtn = 0;
    while (nodeDetect(tail)) {
      rtn += numberGrowth(tail);
      tail = nextNode(tail);
    }
    return rtn;
  }

  /**
   * point to the next node of the current node.
   *
   * @param tail the current node.
   * @return the next node of the current node.
   */
  public static Node nextNode(final Node tail) {
    return tail.next;
  }

  /**
   * add a list of positive number or skip when the list is empty.
   *
   * @param tail the current node.
   * @return the added result.
   */
  public static int numberGrowth(final Node tail) {
    int result;
    if (lengthDetect(tail)) {
      result = grow(tail.nums); //NOPMD
    } else {
      result = DEFAULT_NUMBER; //NOPMD
    }
    return result;
  }

  /**
   * check whether the node is null.
   *
   * @param tail current node.
   * @return whether it is null.
   */
  public static boolean nodeDetect(final Node tail) {
    return tail != null;
  }

  /**
   * check the length of list is positive.
   *
   * @param tail the current node.
   * @return whether its list is positive.
   */
  public static boolean lengthDetect(final Node tail) {
    return tail.nums.length > 0;
  }

  /**
   * compute the total sum of the list of positive numbers.
   *
   * @param nums the list of numbers.
   * @return the sum of the numbers.
   */
  public static int grow(int[] nums) { //NOPMD
    int rtn = 0;
    for (final int x :
            nums) {
      rtn += numGrow(x);
    }
    return rtn;
  }

  /**
   * judge whether the number is positive.
   *
   * @param adder the number for judgement.
   * @return if number is positive, return it. Otherwise, return default number.
   */
  public static int numGrow(final int adder) {
    int result;
    if (isPositive(adder)) {
      result = adder;
    } else {
      result = DEFAULT_NUMBER;
    }
    return result;
  }

  /**
   * check the number is positive.
   *
   * @param adder the number.
   * @return whethr is positive.
   */
  public static boolean isPositive(final int adder) {
    return adder > 0;
  }
}
