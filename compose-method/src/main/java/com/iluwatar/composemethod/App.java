package com.iluwatar.composemethod;

import java.security.SecureRandom;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/**
 * add original method is rough, and the new
 * add method is using the composed method.
 * In this example, refactor the method to
 * let each method contain only one function
 * and ensure the refactored method's result
 * is equals to the original method.
 * {@link Node} is the node for list, and
 * this term is to add the positive integer in
 * each node's integer list
 */
@Slf4j
public class App { //NOPMD
  /**
   * Program entry point.
   *
   * @param args command line args.
   */
  public static void main(final String[] args) {
    //initialize a list.
    final var head = new Node();
    LOGGER.info("head: {}", head);
    final SecureRandom random = new SecureRandom();
    final var nodes = random.nextInt(10);
    var tail = head; //NOPMD

    //for each node to initialize the integer list.
    head.nums = new int[random.nextInt(10)];
    for (int j = 0; j < head.nums.length; j++) {
      head.nums[j] = random.nextInt(20) - 10;
    }
    for (int i = 0; i < nodes; i++) {
      tail.next = new Node(); //NOPMD
      tail = tail.next;
      tail.nums = new int[random.nextInt(10)]; //NOPMD
      for (int j = 0; j < tail.nums.length; j++) {
        tail.nums[j] = random.nextInt(20) - 10;
      }
    }
    LOGGER.info("original method result: {}", AddImpl.addPositiveNodesOriginal(head));
    LOGGER.info("refined method result: {}", AddImpl.addPositiveNodesRefined(head));
  }
}
