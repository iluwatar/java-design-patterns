package com.iluwatar;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

  // Initialize the logger
  private static final Logger logger = Logger.getLogger(App.class.getName());

  public static void main(String[] args) {
    // Create instances of Money
    Money usdAmount1 = new Money(50.00, "USD");
    Money usdAmount2 = new Money(20.00, "USD");

    // Demonstrate addition
    try {
      usdAmount1.addMoney(usdAmount2);
      logger.log(Level.INFO, "Sum in USD: {0}", usdAmount1.getAmount());
    } catch (CannotAddTwoCurrienciesException e) {
      logger.log(Level.SEVERE, "Error adding money: {0}", e.getMessage());
    }

    // Demonstrate subtraction
    try {
      usdAmount1.subtractMoney(usdAmount2);
      logger.log(Level.INFO, "Difference in USD: {0}", usdAmount1.getAmount());
    } catch (CannotSubtractException e) {
      logger.log(Level.SEVERE, "Error subtracting money: {0}", e.getMessage());
    }

    // Demonstrate multiplication
    try {
      usdAmount1.multiply(2);
      logger.log(Level.INFO, "Multiplied Amount in USD: {0}", usdAmount1.getAmount());
    } catch (IllegalArgumentException e) {
      logger.log(Level.SEVERE, "Error multiplying money: {0}", e.getMessage());
    }

    // Demonstrate currency conversion
    try {
      double exchangeRateUsdToEur = 0.85; // Example exchange rate
      usdAmount1.exchangeCurrency("EUR", exchangeRateUsdToEur);
      logger.log(Level.INFO, "USD converted to EUR: {0} {1}", new Object[]{usdAmount1.getAmount(), usdAmount1.getCurrency()});
    } catch (IllegalArgumentException e) {
      logger.log(Level.SEVERE, "Error converting currency: {0}", e.getMessage());
    }

  }
}

