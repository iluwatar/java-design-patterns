package com.iluwatar.temporalproperty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {
  private Customer martin;
  private String franklin = "961 Franklin St";
  private String worcester = "88 Worcester St";

  private SimpleDate jul1 = new SimpleDate(1996, 7, 1);
  private SimpleDate jul15 = new SimpleDate(1996, 7, 15);

  @BeforeEach
  public void setUp () {
    // putting different addresses as time changes
    SimpleDate.setToday(new SimpleDate(1996,1,1));
    martin = new Customer (15, "Martin");
    martin.putAddress(worcester, new SimpleDate(1994, 3, 1));
    SimpleDate.setToday(new SimpleDate(1996,8,10));
    martin.putAddress(franklin, new SimpleDate(1996, 7, 4));
    SimpleDate.setToday(new SimpleDate(2000,9,11));
  }

  @Test
  public void locationAtTime(){
    assertEquals(worcester, martin.getAddress(jul1));
    assertEquals(franklin, martin.getAddress(jul15));
  }

  @Test public void locationToday(){
    SimpleDate.setToday(new SimpleDate(1994, 4, 5));
    assertEquals(worcester, martin.getAddress());
    SimpleDate.setToday(new SimpleDate(1998, 2, 3));
    assertEquals(franklin, martin.getAddress());
  }

  @Test public void idGet(){
    assertEquals(15, martin.getId());
  }

  @Test public void nameGetSet(){
    assertEquals("Martin", martin.getName());
    martin.setName("John");
    assertEquals("John", martin.getName());
  }



}
