package com.iluwatar.effectivity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EffectivityTests {
  Person duke;
  Company india = new Company("india");
  Company peninsular = new Company("peninsular");
  Company dublin = new Company("dublin");

  @BeforeEach
  public void setUp() {
    duke = new Person("Duke");
    duke.addEmployment(india, new SimpleDate(1999,12,1));
    duke.addEmployment(peninsular, new SimpleDate(2000,4,1));
    duke.employments()[0].end(new SimpleDate (2000,5,1));
  }

  @Test
  public void testAdditive() {
    assertEquals(2, duke.employments().length);
    Employment actual = null;
    for (int i = 0; i < duke.employments().length; i++) {
      if (duke.employments()[i].isEffectiveOn(new SimpleDate(2000,6,1))) {
        actual = duke.employments()[i];
        break;
      }
    }
    assertNotNull(actual);
    assertEquals(peninsular, actual.company());
    assertEquals("peninsular", actual.company().toString());
  }

  @Test
  public void testRetro() {
    duke.employments()[1].setEffectivity(DateRange.startingOn(new SimpleDate(2000,6,1)));
    duke.addEmployment(new Employment(dublin, new DateRange(new SimpleDate(2000,5,1), new SimpleDate(2000,5,31))));
    Employment april = null;
    for (int i = 0; i < duke.employments().length; i++) {
      if (duke.employments()[i].isEffectiveOn(new SimpleDate(2000,4,10))) {
        april = duke.employments()[i];
        break;
      }
    }
    assertNotNull(april);
    assertEquals(india, april.company());
    Employment may = null;
    for (int i = 0; i < duke.employments().length; i++) {
      if (duke.employments()[i].isEffectiveOn(new SimpleDate(2000,5,10))) {
        may = duke.employments()[i];
        break;
      }
    }
    assertNotNull(may);
    assertEquals(dublin, may.company());
  }

  @Test
  public void correctEmploymentEffectiveDates(){
    Employment employment = new Employment(dublin, new SimpleDate(1,1,1));
    employment.end(new SimpleDate(2,2,2));
    assertFalse(employment.isEffectiveOn(new SimpleDate(0,12,31)));
    assertFalse(employment.isEffectiveOn(new SimpleDate(2,2,3)));
    assertTrue(employment.isEffectiveOn(new SimpleDate(1,1,1)));
    assertTrue(employment.isEffectiveOn(new SimpleDate(1,2,3)));
    assertTrue(employment.isEffectiveOn(new SimpleDate(2,2,2)));
  }
}
