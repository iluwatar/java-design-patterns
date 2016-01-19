package com.iluwatar.servant;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Date: 12/28/15 - 9:40 PM
 *
 * @author Jeroen Meulemeester
 */
public class KingTest {

  @Test
  public void testHungrySoberUncomplimentedKing() {
    final King king = new King();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testFedSoberUncomplimentedKing() {
    final King king = new King();
    king.getFed();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testHungryDrunkUncomplimentedKing() {
    final King king = new King();
    king.getDrink();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testHungrySoberComplimentedKing() {
    final King king = new King();
    king.receiveCompliments();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testFedDrunkUncomplimentedKing() {
    final King king = new King();
    king.getFed();
    king.getDrink();
    king.changeMood();
    assertTrue(king.getMood());
  }

  @Test
  public void testFedSoberComplimentedKing() {
    final King king = new King();
    king.getFed();
    king.receiveCompliments();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testFedDrunkComplimentedKing() {
    final King king = new King();
    king.getFed();
    king.getDrink();
    king.receiveCompliments();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testHungryDrunkComplimentedKing() {
    final King king = new King();
    king.getDrink();
    king.receiveCompliments();
    king.changeMood();
    assertFalse(king.getMood());
  }

}