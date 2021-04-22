package com.iluwatar.composemethod;

/**
 * implement adding.
 */
public class AddImpl {
  private static final int DEFAULT_NUMBER = 0;

  /**
   * original add method.
   *
   * @param head the head of the list.
   * @return the sum of positive numbers.
   */
  public static int addPositiveNodesOriginal(Node head) {
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
  public static int addPositiveNodesRefined(Node head) {
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
  public static Node nextNode(Node tail) {
    return tail.next;
  }

  /**
   * add a list of positive number or skip when the list is empty.
   *
   * @param tail the current node.
   * @return the added result.
   */
  public static int numberGrowth(Node tail) {
    if (!lengthDetect(tail)) {
      return DEFAULT_NUMBER;
    }
    return grow(tail.nums);
  }

  public static boolean nodeDetect(Node tail) {
    return tail != null;
  }

  public static boolean lengthDetect(Node tail) {
    return tail.nums.length > 0;
  }

  /**
   * compute the total sum of the list of positive numbers.
   *
   * @param nums the list of numbers.
   * @return the sum of the numbers.
   */
  public static int grow(int[] nums) {
    int rtn = 0;
    for (int i = 0; i < nums.length; i++) {
      rtn += numGrow(nums[i]);
    }
    return rtn;
  }

  /**
   * judge whether the number is positive.
   *
   * @param adder the number for judgement.
   * @return if number is positive, return it. Otherwise, return default number.
   */
  public static int numGrow(int adder) {
    if (isPositive(adder)) {
      return adder;
    }
    return DEFAULT_NUMBER;
  }

  public static boolean isPositive(int a) {
    return a > 0;
  }
}
