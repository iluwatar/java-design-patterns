package com.iluwatar.type.object;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * 
 * @author JAPO
 *
 */
public class BreedTest {
  
  /**
   * 
   * Test if Monster created by the correct attributes, as requested
   */
  @Test
  public void testBreedBreedTypeIntString() throws Exception {
    
    Breed beast = new Breed(BreedType.BEAST, 25, "Beast attack");    
    Breed beastHunter = new Breed(BreedType.BEAST_HUNTER, beast, 30, null);
        
    assertNotNull(BreedType.BEAST);
    assertNotNull(BreedType.BEAST_HUNTER);
    assertNotNull(BreedType.BEAST_HUNTER.toString());
    
  }

}
