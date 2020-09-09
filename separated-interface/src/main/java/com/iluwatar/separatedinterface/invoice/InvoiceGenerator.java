package com.iluwatar.separatedinterface.invoice;

/**
 * InvoiceGenerator class generates an invoice, accepting the product cost and calculating the total
 * price payable inclusive tax (calculated by {@link TaxCalculator})
 */
public class InvoiceGenerator {

  /**
   * The TaxCalculator interface to calculate the payable tax.
   */
  private final TaxCalculator taxCalculator;

  /**
   * The base product amount without tax.
   */
  private final double amount;

  public InvoiceGenerator(double amount, TaxCalculator taxCalculator) {
    this.amount = amount;
    this.taxCalculator = taxCalculator;
  }

  public double getAmountWithTax() {
    return amount + taxCalculator.calculate(amount);
  }

}