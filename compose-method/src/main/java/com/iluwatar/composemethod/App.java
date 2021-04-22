package com.iluwatar.composemethod;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/**
 * add original method is rough, and the new add method is using the composed method.
 * {@link Node} is the node for list, and this term is to add the positive integer in
 * each node's integer list
 */
@Slf4j
public class App {
  /**
   * Program entry point.
   *
   * @param args command line args.
   */
  public static void main(String[] args) {
    //initialize a list.
    Node head = new Node();
    LOGGER.info("head: {}", head);
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
    LOGGER.info("original method result: {}", AddImpl.addPositiveNodesOriginal(head));
    LOGGER.info("refined method result: {}", AddImpl.addPositiveNodesRefined(head));
  }
}
