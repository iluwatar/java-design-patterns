package com.iluwatar.servant;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Date: 12/28/15 - 9:52 PM
 *
 * @author Jeroen Meulemeester
 */
public class QueenTest {

  @Test
  public void testNotFlirtyUncomplemented() throws Exception {
    final Queen queen = new Queen();
    queen.setFlirtiness(false);
    queen.changeMood();
    assertFalse(queen.getMood());
  }
  
  @Test
  public void testNotFlirtyComplemented() throws Exception {
    final Queen queen = new Queen();
    queen.setFlirtiness(false);
    queen.receiveCompliments();
    queen.changeMood();
    assertFalse(queen.getMood());
  }
  
  @Test
  public void testFlirtyUncomplemented() throws Exception {
    final Queen queen = new Queen();
    queen.changeMood();
    assertFalse(queen.getMood());
  }
  
  @Test
  public void testFlirtyComplemented() throws Exception {
    final Queen queen = new Queen();
    queen.receiveCompliments();
    queen.changeMood();
    assertTrue(queen.getMood());
  }
  
}