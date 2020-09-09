package com.iluwatar.separatedinterface.taxes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DomesticTaxTest {

  private DomesticTax target;

  @Test
  public void testTaxCaluclation(){
    target = new DomesticTax();

    var tax=target.calculate(100.0);
    Assertions.assertEquals(tax,20.0);
  }

}
