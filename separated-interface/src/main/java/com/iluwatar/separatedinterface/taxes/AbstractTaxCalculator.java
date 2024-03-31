package com.iluwatar.separatedinterface.taxes;

import com.iluwatar.separatedinterface.invoice.TaxCalculator;

public abstract class AbstractTaxCalculator implements TaxCalculator {
    protected static final double TAX_PERCENTAGE = 0.0;

    public abstract double calculate(double amount);

    // Pull up this method
    public double getTaxPercentage() {
        return TAX_PERCENTAGE;
    }
}