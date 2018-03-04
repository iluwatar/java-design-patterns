package com.iluwatar.trampoline;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 * Test for trampoline pattern.
 * */
public class TrampolineAppTest {


  @Test
  public void testTrampolineWithFactorialFunction() throws IOException {
    int result = TrampolineApp.loop(10, 1).result();
    assertEquals("Be equal", 3628800, result);
  }

}