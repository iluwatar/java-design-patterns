package com.iluwatar.composemethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * to test public methods.
 */
class PublicMethodTest {//NOPMD

  @Test
  /**
   * test next node.
   */
  void nextTest() {
    final Node node1 = new Node();
    final Node node2 = new Node();
    node1.setNext(node2);
    Node tail = node1;
    tail = AddImpl.nextNode(tail);
    assertEquals(tail, node2, "correctly point to the next node.");
  }

  @Test
  /**
   * test next node is null.
   */
  void nextNullTest() {
    final Node node1 = new Node();
    Node tail = node1;
    tail = AddImpl.nextNode(tail);
    assertNull(tail, "next is null.");
  }

  @Test
  /**
   * test number for growth.
   */
  void growthTest() {
    final Node node1 = new Node();
    node1.setNums(new int[]{1});
    assertEquals(1, AddImpl.numberGrowth(node1), "correctly added.");
  }

  @Test
  /**
   * test null number list.
   */
  void nullGrowthTest() {
    final Node node1 = new Node();
    node1.setNums(new int[0]);
    assertEquals(0, AddImpl.numberGrowth(node1), "check empty list.");
  }

  @Test
  /**
   * test add method.
   */
  void originalTest() {
    final Node head = new Node();
    head.setNums(new int[]{1});
    assertEquals(1, AddImpl.addPositiveNodesOriginal(head), "correct added.");
  }

  @Test
  /**
   * test null node.
   */
  void nullOriginalTest() {
    assertEquals(0, AddImpl.addPositiveNodesOriginal(null), "check when the node is null.");
  }

  @Test
  /**
   * test refined method.
   */
  void refinedTest() {
    final Node head = new Node();
    head.setNums(new int[]{1});
    assertEquals(1, AddImpl.addPositiveNodesRefined(head), "check the calculation of the method.");
  }

  @Test
  /**
   * test null node.
   */
  void nullRefinedTest() {
    assertEquals(0, AddImpl.addPositiveNodesRefined(null), "check when the node is null.");
  }

  @Test
  /**
   * test node detect.
   */
  void detectTest() {
    final Node head = new Node();
    assertTrue(AddImpl.nodeDetect(head), "node is not null.");
  }

  @Test
  /**
   * test null node.
   */
  void detectNullTest() {
    assertFalse(AddImpl.nodeDetect(null), "the node is null.");
  }

  @Test
  /**
   * number list length detect.
   */
  void lengthTest() {
    final Node head = new Node();
    head.setNums(new int[]{1});
    assertTrue(AddImpl.lengthDetect(head), "the length of the list is not null.");
  }

  @Test
  /**
   * number list null detect.
   */
  void zeroLengthDetect() {
    final Node head = new Node();
    head.setNums(new int[0]);
    assertFalse(AddImpl.lengthDetect(head), "the length of list of the node is 0.");
  }

  @Test
  /**
   * grow test.
   */
  void growTest() {
    final Node head = new Node();
    head.setNums(new int[]{1, 2});
    assertEquals(3, AddImpl.grow(head.getNums()), "correctly added.");
  }

  @Test
  /**
   * null list grow test.
   */
  void nullGrowTest() {
    final int[] nums = new int[0];
    assertEquals(0, AddImpl.grow(nums), "correct result when list length is 0.");
  }

  @Test
  /**
   * adder test.
   */
  void adderTest() {
    int num = 7;//NOPMD
    assertEquals(7, AddImpl.numGrow(num), "number is positive.");
  }

  @Test
  /**
   * null adder test.
   */
  void negativeAdderTest() {
    final int num = -9;
    assertEquals(0, AddImpl.numGrow(num), "number is negative");
  }

  @Test
  /**
   * positive detect test.
   */
  void positiveTest() {
    int num = 7;//NOPMD
    assertTrue(AddImpl.isPositive(num), "number is positive.");
  }

  @Test
  /**
   * negetive test.
   */
  void negativeTest() {
    final int num = -9;
    assertFalse(AddImpl.isPositive(num), "number is negative.");
  }
}
