package com.iluwatar.separatedinterface.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class InvoiceGeneratorTest {

  private InvoiceGenerator target;

  @Test
  public void testGenerateTax() {
    var productCost = 50.0;
    var tax = 10.0;
    TaxCalculator taxCalculatorMock = mock(TaxCalculator.class);
    doReturn(tax).when(taxCalculatorMock).calculate(productCost);

    target = new InvoiceGenerator(productCost, taxCalculatorMock);

    Assertions.assertEquals(target.getAmountWithTax(), productCost + tax);
    verify(taxCalculatorMock, times(1)).calculate(productCost);
  }

}
