package com.iluwatar.composemethod;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodEqualTest {
  @Test
  void methodsEqual() {
    Node head = new Node();
    Random random = new Random();
    int nodes = random.nextInt(10);
    Node tail = head;

    //for each node to initialize the integer list.
    head.nums = new int[random.nextInt(10)];
    for (int j = 0; j < head.nums.length; j++) {
      head.nums[j] = random.nextInt(20) - 10;
    }
    for (int i = 0; i < nodes; i++) {
      tail.next = new Node();
      tail = tail.next;
      tail.nums = new int[random.nextInt(10)];
      for (int j = 0; j < tail.nums.length; j++) {
        tail.nums[j] = random.nextInt(20) - 10;
      }
    }
    assertEquals(AddImpl.addPositiveNodesRefined(tail), AddImpl.addPositiveNodesOriginal(tail));
  }
}
