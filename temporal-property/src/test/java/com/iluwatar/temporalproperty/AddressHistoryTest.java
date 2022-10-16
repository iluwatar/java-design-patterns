package com.iluwatar.temporalproperty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressHistoryTest {
  AddressHistory history;

  private static final String franklin = "961 Franklin St";
  private static final String worcester = "88 Worcester St";


  @BeforeEach
  public void setup(){
    SimpleDate.setToday(new SimpleDate(1996,1,1));
    history = new AddressHistory();
    history.put(new SimpleDate(1994, 3, 1), worcester);
    SimpleDate.setToday(new SimpleDate(1996,8,10));
    history.put(new SimpleDate(1996, 7, 4), franklin);
  }

  @Test
  public void getAddressEqual(){
    assertEquals(worcester, history.get(new SimpleDate(1994, 3, 1)));
    assertEquals(franklin, history.get(new SimpleDate(1996, 7, 4)));
  }

  @Test
  public void throwOnTooEarly(){
    assertThrows(IllegalStateException.class,
            () -> history.get(new SimpleDate(1993, 5, 7)));
  }

  @Test
  public void inbetweenEntriesAddress(){
    assertEquals(worcester, history.get(new SimpleDate(1995, 2, 14)));
    assertEquals(franklin, history.get(new SimpleDate(1997, 3, 6)));
  }

}
