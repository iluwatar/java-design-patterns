---
title: Object Mother
category: Creational
language: en
tag:
 - Instantiation
---

## Object Mother
It is used to define a factory of immutable content with separated builder and factory interfaces.

## Class diagram
![alt text](./etc/object-mother.png "Object Mother")

## Applicability
Use the Object Mother pattern when

* You want consistent objects over several tests
* You want to reduce code for creation of objects in tests
* Every test should run with fresh data


## Understanding the Object Mother Pattern

### Real-World Scenario
Imagine you're developing a Java application for a travel agency. In your system, there are different types of travelers, such as tourists, business travelers, and travel agents, each with specific attributes and behaviors. To perform thorough testing, you need to create and manipulate these traveler objects in various contexts. The Object Mother Pattern can help you generate consistent and predefined traveler objects for testing purposes, ensuring that your tests are based on known, reliable data.

### In Plain Terms
The Object Mother Pattern is a design pattern used in Java to simplify the creation of objects with specific configurations, especially for testing. Instead of manually constructing objects with varying properties for each test case, you create a dedicated "Object Mother" class or method that produces these objects with predefined settings. This ensures that you have consistent and predictable test data, making your tests more reliable and easier to manage.

### Overview from a Testing Perspective
The Object Mother Pattern is a testing-related design pattern that assists in maintaining a consistent and reliable testing environment. It allows you to define and create objects with specific attributes, helping you ensure that your tests produce consistent and predictable results, making it easier to spot issues and maintain your test suite.

### Practical Usage in Testing
In software testing, especially unit testing, the Object Mother Pattern is invaluable. It helps ensure that your tests are not influenced by unpredictable data, thus making your tests more robust and repeatable. By centralizing the creation of test objects in an Object Mother, you can easily adapt your test data to different scenarios.

### Example in Java
Here's an illustrative Java example of the Object Mother Pattern within the context of a travel agency application:

```java
class Traveler {
    private String name;
    private int age;
    private boolean isBusinessTraveler;

    // Constructor and methods for the traveler
    // ...

    // Getter and setter methods
    // ...
}

class TravelerMother {
    public static Traveler createTourist(String name, int age) {
        Traveler traveler = new Traveler();
        traveler.setName(name);
        traveler.setAge(age);
        traveler.setBusinessTraveler(false);
        return traveler;
    }

    public static Traveler createBusinessTraveler(String name, int age) {
        Traveler traveler = new Traveler();
        traveler.setName(name);
        traveler.setAge(age);
        traveler.setBusinessTraveler(true);
        return traveler;
    }
}

public class TravelAgency {
    public static void main(String[] args) {
        // Using the Object Mother to create traveler objects for testing
        Traveler tourist = TravelerMother.createTourist("Alice", 28);
        Traveler businessTraveler = TravelerMother.createBusinessTraveler("Bob", 35);

        // Now you have consistent traveler objects for testing.
    }
}

```

In this example, TravelerMother is the Object Mother class responsible for generating predefined Traveler objects with specific configurations. This approach ensures that you have consistent test data for various scenarios in a travel agency application, enhancing the reliability and effectiveness of your testing efforts.

## Credits

* [Answer by David Brown](http://stackoverflow.com/questions/923319/what-is-an-objectmother) to the stackoverflow question: [What is an ObjectMother?](http://stackoverflow.com/questions/923319/what-is-an-objectmother)
* [c2wiki - Object Mother](http://c2.com/cgi/wiki?ObjectMother)
* [Nat Pryce - Test Data Builders: an alternative to the Object Mother pattern](http://www.natpryce.com/articles/000714.html)
