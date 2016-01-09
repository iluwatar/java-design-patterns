package com.iluwatar.proxy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/28/15 - 9:02 PM
 *
 * @author Jeroen Meulemeester
 */
public class WizardTest {

  @Test
  public void testToString() throws Exception {
    final String[] wizardNames = {"Gandalf", "Dumbledore", "Oz", "Merlin"};
    for (final String name : wizardNames) {
      assertEquals(name, new Wizard(name).toString());
    }
  }

}