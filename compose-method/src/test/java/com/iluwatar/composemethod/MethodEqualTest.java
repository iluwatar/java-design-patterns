package com.iluwatar.composemethod;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * to test whether the two methods are equal.
 */
class MethodEqualTest {//NOPMD

  @Test
  /**
   * test two methods are equal.
   */
  void methodsEqual() {
    final Node head = new Node();
    final Random random = new Random();
    final int nodes = random.nextInt(10);
    Node tail = head;

    //for each node to initialize the integer list.
    int[] numbers = new int[random.nextInt(10)];
    for (int j = 0; j < numbers.length; j++) {
      numbers[j] = random.nextInt(20) - 10;
    }
    head.setNums(numbers);
    for (int i = 0; i < nodes; i++) {
      tail.setNext(new Node());//NOPMD
      tail = tail.getNext();
      int[] nums = new int[random.nextInt(10)];//NOPMD
      for (int j = 0; j < nums.length; j++) {
        nums[j] = random.nextInt(20) - 10;
      }
      tail.setNums(nums);
    }
    assertEquals(AddImpl.addPositiveNodesRefined(tail), AddImpl.addPositiveNodesOriginal(tail), "Two results are equals.");
  }
}
