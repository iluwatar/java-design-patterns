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
  public void nextTest() {
    final Node node1 = new Node();
    final Node node2 = new Node();
    node1.next = node2;
    Node tail = node1;
    tail = AddImpl.nextNode(tail);
    assertEquals(tail, node2, "correctly point to the next node.");
  }

  @Test
  /**
   * test next node is null.
   */
  public void nextNullTest() {
    final Node node1 = new Node();
    Node tail = node1;
    tail = AddImpl.nextNode(tail);
    assertNull(tail, "next is null.");
  }

  @Test
  /**
   * test number for growth.
   */
  public void growthTest() {
    final Node node1 = new Node();
    node1.nums = new int[]{1};
    assertEquals(AddImpl.numberGrowth(node1), 1, "correctly added.");
  }

  @Test
  /**
   * test null number list.
   */
  public void nullGrowthTest() {
    final Node node1 = new Node();
    node1.nums = new int[0];
    assertEquals(AddImpl.numberGrowth(node1), 0, "check empty list.");
  }

  @Test
  /**
   * test add method.
   */
  public void originalTest() {
    final Node head = new Node();
    head.nums = new int[]{1};
    assertEquals(AddImpl.addPositiveNodesOriginal(head), 1, "correct added.");
  }

  @Test
  /**
   * test null node.
   */
  public void nullOriginalTest() {
    assertEquals(AddImpl.addPositiveNodesOriginal(null), 0, "check when the node is null.");
  }

  @Test
  /**
   * test refined method.
   */
  public void refinedTest() {
    final Node head = new Node();
    head.nums = new int[]{1};
    assertEquals(AddImpl.addPositiveNodesRefined(head), 1, "check the calculation of the method.");
  }

  @Test
  /**
   * test null node.
   */
  public void nullRefinedTest() {
    assertEquals(AddImpl.addPositiveNodesRefined(null), 0, "check when the node is null.");
  }

  @Test
  /**
   * test node detect.
   */
  public void detectTest() {
    final Node head = new Node();
    assertTrue(AddImpl.nodeDetect(head), "node is not null.");
  }

  @Test
  /**
   * test null node.
   */
  public void detectNullTest() {
    assertFalse(AddImpl.nodeDetect(null), "the node is null.");
  }

  @Test
  /**
   * number list length detect.
   */
  public void lengthTest() {
    final Node head = new Node();
    head.nums = new int[]{1};
    assertTrue(AddImpl.lengthDetect(head), "the length of the list is not null.");
  }

  @Test
  /**
   * number list null detect.
   */
  public void zeroLengthDetect() {
    final Node head = new Node();
    head.nums = new int[0];
    assertFalse(AddImpl.lengthDetect(head), "the length of list of the node is 0.");
  }

  @Test
  /**
   * grow test.
   */
  public void growTest() {
    final Node head = new Node();
    head.nums = new int[]{1, 2};
    assertEquals(AddImpl.grow(head.nums), 3, "correctly added.");
  }

  @Test
  /**
   * null list grow test.
   */
  public void nullGrowTest() {
    final int[] nums = new int[0];
    assertEquals(AddImpl.grow(nums), 0, "correct result when list length is 0.");
  }

  @Test
  /**
   * adder test.
   */
  public void adderTest() {
    int num = 7;//NOPMD
    assertEquals(AddImpl.numGrow(num), 7, "number is positive.");
  }

  @Test
  /**
   * null adder test.
   */
  public void negativeAdderTest() {
    final int num = -9;
    assertEquals(AddImpl.numGrow(num), 0, "number is negative");
  }

  @Test
  /**
   * positive detect test.
   */
  public void positiveTest() {
    int num = 7;//NOPMD
    assertTrue(AddImpl.isPositive(num), "number is positive.");
  }

  @Test
  /**
   * negetive test.
   */
  public void negativeTest() {
    final int num = -9;
    assertFalse(AddImpl.isPositive(num), "number is negative.");
  }
}
