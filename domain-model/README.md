---
title: Domain Model
category: Structural
language: en
tag:
    - Business
    - Domain
---

## Also known as

* Conceptual Model
* Domain Object Model

## Intent

The Domain Model pattern aims to create a conceptual model in your software that matches the real-world system it's designed to represent. It involves using rich domain objects that encapsulate both data and behavior relevant to the application domain.

## Explanation

Real world example

> Let's assume that we need to build an e-commerce web application. While analyzing requirements you will notice that there are few nouns you talk about repeatedly. It’s your Customer, and a Product the customer looks for. These two are your domain-specific classes and each of that classes will include some business logic specific to its domain.

In plain words

> The Domain Model is an object model of the domain that incorporates both behavior and data.

Programmatic Example

In the example of the e-commerce app, we need to deal with the domain logic of customers who want to buy products and return them if they want. We can use the domain model pattern and create classes `Customer` and `Product` where every single instance of that class incorporates both behavior and data and represents only one record in the underlying table.

```java
public class Customer {
    // Customer properties and methods
}

public class Product {
    // Product properties and methods
}
```

Data Access Objects (DAOs): These objects provide an abstract interface to the database. They are used to retrieve domain entities and save changes back to the database. In the provided code, CustomerDaoImpl and ProductDaoImpl are the DAOs.

```java
public class CustomerDaoImpl implements CustomerDao {
    // Implementation of the methods defined in the CustomerDao interface
}

public class ProductDaoImpl implements ProductDao {
    // Implementation of the methods defined in the ProductDao interface
}
```

Domain Logic: This is encapsulated within the domain entities. For example, the Customer class has methods like buyProduct() and returnProduct() which represent the actions a customer can perform.

```java
public class Customer {
    // ...

    public void buyProduct(Product product) {
        // Implementation of buying a product
    }

    public void returnProduct(Product product) {
        // Implementation of returning a product
    }
}
```

Application: The App class uses the domain entities and their methods to implement the business logic of the application.

```java
public class App {
    public static void main(String[] args) {
        // Create customer and products
        // Perform actions like buying and returning products
    }
}
```

The program output:

```java
17:52:28.690[main]INFO com.iluwatar.domainmodel.Customer-Tom balance:USD30.00 
17:52:28.695[main]INFO com.iluwatar.domainmodel.Customer-Tom didn't bought anything
17:52:28.699[main]INFO com.iluwatar.domainmodel.Customer-Tom want to buy Eggs($10.00)...
17:52:28.705[main]INFO com.iluwatar.domainmodel.Customer-Tom bought Eggs!
17:52:28.705[main]INFO com.iluwatar.domainmodel.Customer-Tom balance:USD20.00
17:52:28.705[main]INFO com.iluwatar.domainmodel.Customer-Tom want to buy Butter($20.00)...
17:52:28.712[main]INFO com.iluwatar.domainmodel.Customer-Tom bought Butter!
17:52:28.712[main]INFO com.iluwatar.domainmodel.Customer-Tom balance:USD0.00
17:52:28.712[main]INFO com.iluwatar.domainmodel.Customer-Tom want to buy Cheese($20.00)...
17:52:28.712[main]ERROR com.iluwatar.domainmodel.Customer-Not enough money!
17:52:28.712[main]INFO com.iluwatar.domainmodel.Customer-Tom balance:USD0.00
17:52:28.712[main]INFO com.iluwatar.domainmodel.Customer-Tom want to return Butter($20.00)...
17:52:28.721[main]INFO com.iluwatar.domainmodel.Customer-Tom returned Butter!
17:52:28.721[main]INFO com.iluwatar.domainmodel.Customer-Tom balance:USD20.00
17:52:28.721[main]INFO com.iluwatar.domainmodel.Customer-Tom want to buy Cheese($20.00)...
17:52:28.726[main]INFO com.iluwatar.domainmodel.Customer-Tom bought Cheese!
17:52:28.737[main]INFO com.iluwatar.domainmodel.Customer-Tom balance:USD0.00
17:52:28.738[main]INFO com.iluwatar.domainmodel.Customer-Tom bought:Eggs-$10.00,Cheese-$20.00
```

## Class diagram

![Domain Model class diagram](./etc/domain-model.urm.png "domain model")

## Applicability

* Appropriate in complex applications with rich business logic.
* When the business logic or domain complexity is high and requires a model that closely represents real-world entities and their relationships.
* Suitable for applications where domain experts are involved in the development process to ensure the model accurately reflects domain concepts.

## Known Uses

* Enterprise applications (ERP, CRM systems)
* Financial systems (banking, trading platforms)
* Healthcare applications (patient records management)
* E-commerce platforms (product catalogs, shopping carts)

## Consequences

Benefits:

* Improved Communication: Provides a common language for developers and domain experts, enhancing understanding and collaboration.
* Flexibility: Encapsulates business logic within domain entities, making it easier to modify and extend without affecting other system parts.
* Maintainability: A well-structured domain model can simplify maintenance and evolution of the application over time.
* Reusability: Domain classes can often be reused across different projects within the same domain.

Trade-offs:

* Complexity: Can introduce complexity, especially in simple applications where a domain model might be overkill.
* Performance Concerns: Rich domain objects with complex behaviors might lead to performance bottlenecks, requiring careful optimization.
* Learning Curve: Requires a good understanding of the domain and may involve a steep learning curve for developers unfamiliar with the domain concepts.

## Related Patterns

* [Data Access Object (DAO)](https://java-design-patterns.com/patterns/dao/): For abstracting and encapsulating all access to the data source.
* [Service Layer](https://java-design-patterns.com/patterns/service-layer/): Defines an application's boundary with a layer of services that establishes a set of available operations and coordinates the application's response in each operation.
* [Repository](https://java-design-patterns.com/patterns/repository/): Mediates between the domain and data mapping layers, acting like an in-memory domain object collection.
* [Unit of Work](https://java-design-patterns.com/patterns/unit-of-work/): Maintains a list of objects affected by a business transaction and coordinates the writing out of changes.

## Credits

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3vMCjnP)
* [Implementing Domain-Driven Design](https://amzn.to/4cUX4OL)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321127420&linkId=18acc13ba60d66690009505577c45c04)
* [Domain Model Pattern](https://martinfowler.com/eaaCatalog/domainModel.html)
* [Architecture patterns: domain model and friends](https://inviqa.com/blog/architecture-patterns-domain-model-and-friends)
