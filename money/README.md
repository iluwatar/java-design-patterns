---
title: "Money Pattern in Java: Encapsulating Monetary Values with Currency Consistency"
shortTitle: Money
description: "Learn how the Money design pattern in Java ensures currency safety, precision handling, and maintainable financial operations. Explore examples, applicability, and benefits of the pattern."
category: Behavioral
language: en
tag:
  - Encapsulation
  - Precision handling
  - Currency safety
  - Value Object
  - Financial operations
  - Currency
  - Financial
  - Immutable
  - Value Object
---

## Also known as

* Monetary Value Object

## Intent of Money Design Pattern

The Money design pattern provides a robust way to encapsulate monetary values and their associated currencies. It ensures precise calculations, currency consistency, and maintainability of financial logic in Java applications.

## Detailed Explanation of Money Pattern with Real-World Examples

### Real-world example

> Imagine an e-commerce platform where customers shop in their local currencies. The platform needs to calculate order totals, taxes, and discounts accurately while handling multiple currencies seamlessly.

In this example:
- Each monetary value (like a product price or tax amount) is encapsulated in a `Money` object.
- The `Money` class ensures that only values in the same currency are combined and supports safe currency conversion for global operations.

### In plain words

> The Money pattern encapsulates both an amount and its currency, ensuring financial operations are precise, consistent, and maintainable.

### Wikipedia says

> "The Money design pattern encapsulates a monetary value and its currency, allowing for safe arithmetic operations and conversions while preserving accuracy and consistency in financial calculations."

## Programmatic Example of Money Pattern in Java

### Money Class

```java

/**
 * Represents a monetary value with an associated currency.
 * Provides operations for basic arithmetic (addition, subtraction, multiplication),
 * as well as currency conversion while ensuring proper rounding.
 */
@Getter
public class Money {
  private @Getter double amount;
  private @Getter String currency;

  public Money(double amnt, String curr) {
    this.amount = amnt;
    this.currency = curr;
  }

  private double roundToTwoDecimals(double value) {
    return Math.round(value * 100.0) / 100.0;
  }

  public void addMoney(Money moneyToBeAdded) throws CannotAddTwoCurrienciesException {
    if (!moneyToBeAdded.getCurrency().equals(this.currency)) {
      throw new CannotAddTwoCurrienciesException("You are trying to add two different currencies");
    }
    this.amount = roundToTwoDecimals(this.amount + moneyToBeAdded.getAmount());
  }

  public void subtractMoney(Money moneyToBeSubtracted) throws CannotSubtractException {
    if (!moneyToBeSubtracted.getCurrency().equals(this.currency)) {
      throw new CannotSubtractException("You are trying to subtract two different currencies");
    } else if (moneyToBeSubtracted.getAmount() > this.amount) {
      throw new CannotSubtractException("The amount you are trying to subtract is larger than the amount you have");
    }
    this.amount = roundToTwoDecimals(this.amount - moneyToBeSubtracted.getAmount());
  }

  public void multiply(int factor) {
    if (factor < 0) {
      throw new IllegalArgumentException("Factor must be non-negative");
    }
    this.amount = roundToTwoDecimals(this.amount * factor);
  }

  public void exchangeCurrency(String currencyToChangeTo, double exchangeRate) {
    if (exchangeRate < 0) {
      throw new IllegalArgumentException("Exchange rate must be non-negative");
    }
    this.amount = roundToTwoDecimals(this.amount * exchangeRate);
    this.currency = currencyToChangeTo;
  }
}

## When to Use the Money Pattern

The Money pattern should be used in scenarios where:

1. **Currency-safe arithmetic operations**  
   To ensure that arithmetic operations like addition, subtraction, and multiplication are performed only between amounts in the same currency, preventing inconsistencies or errors in calculations.

2. **Accurate rounding for financial calculations**  
   Precise rounding to two decimal places is critical to maintain accuracy and consistency in financial systems.

3. **Consistent currency conversion**  
   When handling international transactions or displaying monetary values in different currencies, the Money pattern facilitates easy and reliable conversion using exchange rates.

4. **Encapsulation of monetary logic**  
   By encapsulating all monetary operations within a dedicated class, the Money pattern improves maintainability and reduces the likelihood of errors.

5. **Preventing errors in financial operations**  
   Strict validation ensures that operations like subtraction or multiplication are only performed when conditions are met, safeguarding against misuse or logical errors.

6. **Handling diverse scenarios in financial systems**  
   Useful in complex systems like e-commerce, banking, and payroll applications where precise and consistent monetary value handling is crucial.

---
## Benefits and Trade-offs of Money Pattern

### Benefits
1. **Precision and Accuracy**  
   The Money pattern ensures precise handling of monetary values, reducing the risk of rounding errors.

2. **Encapsulation of Business Logic**  
   By encapsulating monetary operations, the pattern enhances maintainability and reduces redundancy in financial systems.

3. **Currency Safety**  
   It ensures operations are performed only between amounts of the same currency, avoiding logical errors.

4. **Improved Readability**  
   By abstracting monetary logic into a dedicated class, the code becomes easier to read and maintain.

5. **Ease of Extension**  
   Adding new operations, handling different currencies, or incorporating additional business rules is straightforward.

### Trade-offs
1. **Increased Complexity**  
   Introducing a dedicated `Money` class can add some overhead, especially for small or simple projects.

2. **Potential for Misuse**  
   Without proper validation and handling, incorrect usage of the Money pattern may introduce subtle bugs.

3. **Performance Overhead**  
   Precision and encapsulation might slightly affect performance in systems with extremely high transaction volumes.

---

## Related Design Patterns

1. **Value Object**  
   Money is a classic example of the Value Object pattern, where objects are immutable and define equality based on their value.
   Link:https://martinfowler.com/bliki/ValueObject.html
2. **Factory Method**  
   Factories can be employed to handle creation logic, such as applying default exchange rates or rounding rules.
    Link:https://www.geeksforgeeks.org/factory-method-for-designing-pattern/
---

## References and Credits

- [Patterns of Enterprise Application Architecture](https://martinfowler.com/eaaCatalog/money.html) by Martin Fowler  
- [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)  
