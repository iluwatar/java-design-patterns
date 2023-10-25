---
title: Step Builder
category: Creational
language: en
tag:
 - Instantiation
---

# Step Builder Pattern

## Explanation

The Step Builder pattern is a creational design pattern used to construct a complex object step by step. It provides a fluent interface to create an object with a large number of possible configurations, making the code more readable and reducing the need for multiple constructors or setter methods.

## Intent
An extension of the Builder pattern that fully guides the user through the creation of the object with no chances of confusion.
The user experience will be much more improved by the fact that he will only see the next step methods available, NO build method until is the right time to build the object.

## Real World Example

Imagine you are building a configuration object for a database connection. The connection has various optional parameters such as host, port, username, password, and others. Using the Step Builder pattern, you can set these parameters in a clean and readable way:

```java
DatabaseConnection connection = new DatabaseConnection.Builder()
    .setHost("localhost")
    .setPort(3306)
    .setUsername("user")
    .setPassword("password")
    .setSSL(true)
    .build();
```

## In Plain Words

The Step Builder pattern allows you to construct complex objects by breaking down the construction process into a series of steps. Each step corresponds to setting a particular attribute or configuration option of the object. This results in more readable and maintainable code, especially when dealing with objects that have numerous configuration options.

## Wikipedia Says

According to Wikipedia, the Step Builder pattern is a creational design pattern in which an object is constructed step by step. It involves a dedicated 'director' class, which orchestrates the construction process through a series of 'builder' classes, each responsible for a specific aspect of the object's configuration. This pattern is particularly useful when dealing with objects that have a large number of optional parameters.

## Programmatic Example

Assuming you have a class `Product` with several configurable attributes, a Step Builder for it might look like this:

```java
public class Product {
    private String name;
    private double price;
    private int quantity;

    // private constructor to force the use of the builder
    private Product(String name, double price, int quantity) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;

    }

    public static class Builder {
        private String name;
        private double price;
        private int quantity;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Product build() {
            return new Product(name, price, quantity);
        }
    }
}

// Usage
Product product = new Product.Builder()
    .setName("Example Product")
    .setPrice(29.99)
    .setQuantity(100)
    .build();
```

This example demonstrates how you can use the Step Builder pattern to create a `Product` object with a customizable set of attributes. Each method in the builder corresponds to a step in the construction process.

## Applicability
Use the Step Builder pattern when the algorithm for creating a complex object should be independent of the parts that make up the object and how they're assembled the construction process must allow different representations for the object that's constructed when in the process of constructing the order is important.

## Another example with class diagram
![alt text](./etc/step-builder.png "Step Builder")


## Credits

* [Marco Castigliego - Step Builder](http://rdafbn.blogspot.co.uk/2012/07/step-builder-pattern_28.html)
