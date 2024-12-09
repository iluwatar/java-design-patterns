# Book Data Processing and Presentation

This example demonstrates a simple pipeline for processing book data and rendering it as an HTML view. It involves three main steps: **data preparation**, **business logic processing**, and **presentation**. The structure of the program aligns with the principles of the **Presentation-Abstraction-Control (PAC)** design pattern.

---

## Problem Addressed
This implementation solves the issue described in [Issue #1323](https://github.com/iluwatar/java-design-patterns/issues/1323) of the [java-design-patterns repository](https://github.com/iluwatar/java-design-patterns). The goal is to showcase how to split application logic into phases while keeping the layers loosely coupled.

---

## Overview of the Solution
The program is divided into the following parts:

1. **Data Layer**
    - The `Book` class represents the raw data for a book.
    - The `BookStore` class holds the processed book data for display.

2. **Logic Layer**
    - The `DataPreparation` class handles the business logic. It computes whether a discount is applied and checks stock availability.

3. **Presentation Layer**
    - The `Presentation` class generates an HTML view of the book data.

---

## How to Run the Code

1. Clone the repository:
   ```bash
   git clone https://github.com/iluwatar/java-design-patterns.git
   cd java-design-patterns
   ```

2. Compile and run the `App` class:
   ```bash
   javac com/iluwatar/*.java
   java com.iluwatar.App
   ```

3. Expected Output:  
   The program generates an HTML representation of the book data and prints it to the console:
   ```html
   <div class='book'>
       <h1>Batman Vol. 1: The Court of Owls</h1>
       <p>Price: $11.6</p>
       <p>Discounted Price: $8.7</p>
       <p>Status: In Stock</p>
   </div>
   ```

---

## Code Structure

- `Book`: Represents raw book data with fields like `name`, `price`, `discount`, and `stock`.
- `BookStore`: Represents processed data including `discountPrice` and `inStock` status.
- `DataPreparation`: Contains logic to calculate the discount price and determine stock status.
- `Presentation`: Generates the HTML view of the book data.
- `App`: Entry point that orchestrates the flow.

---

## Key Features

- **Layered Architecture**: The application is divided into layers for better separation of concerns.
- **Scalable Design**: The modular approach makes it easier to extend functionality, such as adding new data preparation rules or presentation formats.
- **Reusability**: Each layer operates independently, promoting code reuse.

---

## Related Resources

- **GitHub Issue**: [Issue #1323](https://github.com/iluwatar/java-design-patterns/issues/1323)
- **Repository**: [java-design-patterns](https://github.com/iluwatar/java-design-patterns)
- **Design Pattern**: [Two-Step-View-Pattern](https://martinfowler.com/eaaCatalog/twoStepView.html)



