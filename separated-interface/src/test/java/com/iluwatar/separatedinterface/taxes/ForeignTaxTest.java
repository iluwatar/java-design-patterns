package com.iluwatar.separatedinterface.taxes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ForeignTaxTest {

  private ForeignTax target;

  @Test
  public void testTaxCaluclation(){
    target = new ForeignTax();

    var tax=target.calculate(100.0);
    Assertions.assertEquals(tax,60.0);
  }

}
