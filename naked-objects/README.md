---
title: Naked Objects
category: Architectural
language: en
tag:
  - Decoupling
  - Extensibility
  - Presentation
---

## Intent

> The naked object design pattern is a way to build user interfaces (UIs) for software applications that is based on the idea of direct manipulation. This means that users interact directly with the underlying domain objects of the application, without any intermediary UI elements.

> The naked object pattern is implemented by exposing the domain objects to the user in a way that is both meaningful and accessible. This is typically done by generating a UI automatically from the domain object definitions. The UI presents the domain objects to the user in a simple and straightforward way, allowing them to create, retrieve, update, and delete objects, as well as invoke methods on them.

> The naked object pattern has a number of advantages, including:

> 1. <ins>Reduced development time and cost</ins>: The naked object pattern can significantly reduce the time and cost required to develop and maintain a software application. This is because the UI is generated automatically, and the domain objects are designed to be both user-visible and manipulatable.

> 2. <ins>Empowered users</ins>: The naked object pattern gives users direct access to the underlying domain objects of the application. This allows them to interact with the application in a way that is natural and intuitive.

> 3. <ins>Increased flexibility and adaptability</ins>: The naked object pattern is highly flexible and adaptable. This is because the UI is generated from the domain object definitions, which means that the UI can be easily changed as the domain model evolves.

## Explanation

**In plain words**

> Imagine you are building a software application to manage a customer database. You could use the naked object pattern to build the UI for this application as follows:

> 1. Define the domain objects for your application. This includes objects such as Customer, Order, and Product.

> 2. Implement the business logic for your application on these domain objects. For example, you could implement methods to create a new customer, add a product to an order, or calculate the total price of an order.

> 3. Use a naked object framework to generate a UI for your application from the domain object definitions.

> The generated UI will present the domain objects to the user in a simple and straightforward way. The user will be able to create, retrieve, update, and delete customers, orders, and products, as well as invoke methods on them.

For example, the user could create a new customer by entering the customer's name, address, and phone number into the UI. The user could also retrieve a list of all customers by clicking a button. To update a customer's information, the user could simply change the corresponding values in the UI and click a save button.

**Wikipedia says**

> Naked objects is an architectural pattern used in software engineering. It is defined by three principles:
>
> 1. All business logic should be encapsulated onto the domain objects. This principle is not unique to naked objects; it is a strong commitment to encapsulation.
> 2. The user interface should be a direct representation of the domain objects, with all user actions consisting of creating, retrieving, or invoking methods on domain objects. This principle is not unique to naked objects: it is an interpretation of an object-oriented user interface.
>
> The naked object pattern's innovative feature arises by combining the 1st and 2nd principles into a 3rd principle: 3. The user interface shall be entirely automatically created from the definitions of the domain objects. This may be done using reflection or source code generation.

## Programmatic example

> Certainly, here's a programmatic example section for the Naked Objects pattern:

## Programmatic Example

In the context of the Naked Objects pattern, let's consider a simplified example with domain objects representing books and authors. The example demonstrates how the Naked Objects pattern can be applied to create a user interface for managing a library catalog.

Suppose we have the following domain objects in a Java-based application:

````java
@DomainObject
public class Book {
    @Property
    private String title;

    @Property
    private String author;

    @Action
    public void borrow() {
        // Implement borrowing logic here
    }
}

@DomainObject
public class Author {
    @Property
    private String name;

    @Collection
    public List<Book> getBooks() {
        // Implement logic to retrieve books by this author
    }

    @Action
    public Book createBook(String title) {
        // Implement logic to create a new book by this author
    }
}
````


> In this example, we define two domain objects: Book and Author. The Book class has properties for the title and author, as well as an action to borrow the book. The Author class has a property for the author's name, a collection of books they have written, and an action to create a new book.

> With the Naked Objects framework or tool, the user interface for managing books and authors can be automatically generated based on these domain object definitions. Users can interact with the generated UI to create, retrieve, update, and delete books and authors directly through a user-friendly interface.
> Here's how you can use these domain objects to create and interact with books and authors programmatically:

```java
var author = new Author();
author.setName("J.K. Rowling");
var book = author.createBook("Harry Potter and the Philosopher's Stone");
book.setAuthor(author);
book.borrow();

var booksByAuthor = author.getBooks();

```
> This example demonstrates how the Naked Objects pattern can be implemented in a Java-based application with domain objects for books and authors. Users can directly manipulate these domain objects through the generated user interface.


## Applicability

> The naked objects pattern is applicable to a wide range of software applications, but it is particularly well-suited for applications where users need to have direct access to the underlying data model. This is often the case in business applications, such as:

* Customer relationship management (CRM) systems
* Enterprise resource planning (ERP) systems
* Human resources (HR) systems
* Order management systems
* Inventory management systems
* Asset management systems
* Project management systems
* Knowledge management systems

> The naked objects pattern can also be used to build UIs for scientific and engineering applications, such as:

* Data analysis applications
* Simulation applications
* Modeling applications
* Visualization applications
>In general, the naked objects pattern is a good choice for any application where users need to be able to create, retrieve, update, and delete data, as well as run complex reports and analyses on that data.

## Known uses
> 1. Here are some specific examples of applications that have been built using the naked objects pattern:

> 2. The Department of Social and Family Affairs in Ireland uses the naked objects pattern for its Child Benefit Administration system.

> 3. The UK National Health Service uses the naked objects pattern for its Electronic Patient Record system.

> 4. The Australian Taxation Office uses the naked objects pattern for its tax return processing system.

> 5. The US Department of Defense uses the naked objects pattern for its logistics management system.

## Quick start

Apache Isis is a Java framework that implements the naked objects pattern. Check out their [starter app](https://isis.apache.org/docs/2.0.0-M9/starters/simpleapp.html) to get started on building an application.

## Credits

* [Richard Pawson - Naked Objects](http://downloads.nakedobjects.net/resources/Pawson%20thesis.pdf)
* [Apache Isis - Introducing Apache Isis](https://isis.apache.org/versions/1.16.0/pages/downloadable-presentations/resources/downloadable-presentations/IntroducingApacheIsis-notes.pdf)


````
