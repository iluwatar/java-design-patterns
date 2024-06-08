---
title: "Currying Pattern in Java: Enhancing Function Flexibility and Reusability"
shortTitle: Currying
description: "Learn about currying in Java, a technique to simplify functions by breaking them into a sequence of single-argument functions. Discover its applications, benefits, and examples in this comprehensive guide."
category: Functional
language: en
tag:
  - Code simplification
  - Functional decomposition
  - Generic
  - Immutable
---

## Also known as

* Partial Function Application

## Intent of Currying Design Pattern

Currying decomposes a function that takes multiple arguments into a sequence of functions that each take a single argument. This technique is integral in functional programming, enabling the creation of higher-order functions through partial application of its arguments. Using currying in Java can lead to more modular, reusable, and maintainable code.

## Detailed Explanation of Currying Pattern with Real-World Examples

Real-world example

> Currying in programming can be compared to an assembly line in a factory. Imagine a car manufacturing process where each station on the assembly line performs a specific task, such as installing the engine, painting the car, and adding the wheels. Each station takes a partially completed car and performs a single operation before passing it to the next station. Similarly, in currying, a function that requires multiple arguments is broken down into a series of functions, each taking a single argument and returning another function until all arguments are provided. This step-by-step processing simplifies complex tasks by dividing them into manageable, sequential operations, which is especially useful in Java functional programming.

In plain words

> Decompose a function that take multiple arguments into multiple functions that take a single argument.

Wikipedia says

> In mathematics and computer science, currying is the technique of translating a function that takes multiple arguments into a sequence of families of functions, each taking a single argument.

## Programmatic example of Currying Pattern in Java

Consider a librarian who wants to populate their library with books. The librarian wants functions which can create books corresponding to specific genres and authors. Currying makes this possible by writing a curried book builder function and utilising partial application.

We have a `Book` class and `Genre` enum.

```java
public class Book {
    private final Genre genre;
    private final String author;
    private final String title;
    private final LocalDate publicationDate;

    Book(Genre genre, String author, String title, LocalDate publicationDate) {
        this.genre = genre;
        this.author = author;
        this.title = title;
        this.publicationDate = publicationDate;
    }
}
```

```java
public enum Genre {
    FANTASY,
    HORROR,
    SCI_FI
}
```

We could easily create a `Book` object with the following method:

```java
Book createBook(Genre genre, String author, String title, LocalDate publicationDate) {
    return new Book(genre, author, title, publicationDate);
}
```

However, what if we only wanted to create books from the `FANTASY` genre? Passing the `FANTASY` parameter with each method call would be repetitive. Alternatively, we could define a new method specifically for creating `FANTASY` books, but it would be impractical to create a separate method for each genre. The solution is to use a curried function.

```java
static Function<Genre, Function<String, Function<String, Function<LocalDate, Book>>>> book_creator
        = bookGenre
        -> bookAuthor
        -> bookTitle
        -> bookPublicationDate
        -> new Book(bookGenre, bookAuthor, bookTitle, bookPublicationDate);
```

Note that the order of the parameters is important. `genre` must come before `author`, `author` must come before `title` and so on. We must be considerate of this when writing curried functions to take full advantage of partial application. Using the above function, we can define a new function `fantasyBookFunc`, to generate `FANTASY` books as follows:

```java
Function<String, Function<String, Function<LocalDate, Book>>> fantasyBookFunc = Book.book_creator.apply(Genre.FANTASY);
```

Unfortunately, the type signature of `BOOK_CREATOR` and `fantasyBookFunc` are difficult to read and understand. We can improve this by using the [builder pattern](https://java-design-patterns.com/patterns/builder/) and functional interfaces.

```java
public static AddGenre builder() {
    return genre
            -> author
            -> title
            -> publicationDate
            -> new Book(genre, author, title, publicationDate);
}

public interface AddGenre {
    Book.AddAuthor withGenre(Genre genre);
}

public interface AddAuthor {
    Book.AddTitle withAuthor(String author);
}

public interface AddTitle {
    Book.AddPublicationDate withTitle(String title);
}

public interface AddPublicationDate {
    Book withPublicationDate(LocalDate publicationDate);
}
```

The semantics of the `builder` function can easily be understood. The `builder` function returns a function `AddGenre`, which adds the genre to the book. Similarity, the `AddGenre` function returns another function `AddTitle`, which adds the title to the book and so on, until the `AddPublicationDate` function returns a `Book`. For example, we could create a `Book` as follows:

```java
Book book = Book.builder().withGenre(Genre.FANTASY)
    .withAuthor("Author")
    .withTitle("Title")
    .withPublicationDate(LocalDate.of(2000, 7, 2));
```

The below example demonstrates how partial application can be used with the `builder` function to create specialised book builder functions.

```java
public static void main(String[] args) {
    LOGGER.info("Librarian begins their work.");

    // Defining genre book functions
    Book.AddAuthor fantasyBookFunc = Book.builder().withGenre(Genre.FANTASY);
    Book.AddAuthor horrorBookFunc = Book.builder().withGenre(Genre.HORROR);
    Book.AddAuthor scifiBookFunc = Book.builder().withGenre(Genre.SCIFI);

    // Defining author book functions
    Book.AddTitle kingFantasyBooksFunc = fantasyBookFunc.withAuthor("Stephen King");
    Book.AddTitle kingHorrorBooksFunc = horrorBookFunc.withAuthor("Stephen King");
    Book.AddTitle rowlingFantasyBooksFunc = fantasyBookFunc.withAuthor("J.K. Rowling");

    // Creates books by Stephen King (horror and fantasy genres)
    Book shining = kingHorrorBooksFunc.withTitle("The Shining")
            .withPublicationDate(LocalDate.of(1977, 1, 28));
    Book darkTower = kingFantasyBooksFunc.withTitle("The Dark Tower: Gunslinger")
            .withPublicationDate(LocalDate.of(1982, 6, 10));

    // Creates fantasy books by J.K. Rowling
    Book chamberOfSecrets = rowlingFantasyBooksFunc.withTitle("Harry Potter and the Chamber of Secrets")
            .withPublicationDate(LocalDate.of(1998, 7, 2));

    // Create sci-fi books
    Book dune = scifiBookFunc.withAuthor("Frank Herbert")
            .withTitle("Dune")
            .withPublicationDate(LocalDate.of(1965, 8, 1));
    Book foundation = scifiBookFunc.withAuthor("Isaac Asimov")
            .withTitle("Foundation")
            .withPublicationDate(LocalDate.of(1942, 5, 1));

    LOGGER.info("Stephen King Books:");
    LOGGER.info(shining.toString());
    LOGGER.info(darkTower.toString());

    LOGGER.info("J.K. Rowling Books:");
    LOGGER.info(chamberOfSecrets.toString());

    LOGGER.info("Sci-fi Books:");
    LOGGER.info(dune.toString());
    LOGGER.info(foundation.toString());
}
```

Program output:

```
09:04:52.499 [main] INFO com.iluwatar.currying.App -- Librarian begins their work.
09:04:52.502 [main] INFO com.iluwatar.currying.App -- Stephen King Books:
09:04:52.506 [main] INFO com.iluwatar.currying.App -- Book{genre=HORROR, author='Stephen King', title='The Shining', publicationDate=1977-01-28}
09:04:52.506 [main] INFO com.iluwatar.currying.App -- Book{genre=FANTASY, author='Stephen King', title='The Dark Tower: Gunslinger', publicationDate=1982-06-10}
09:04:52.506 [main] INFO com.iluwatar.currying.App -- J.K. Rowling Books:
09:04:52.506 [main] INFO com.iluwatar.currying.App -- Book{genre=FANTASY, author='J.K. Rowling', title='Harry Potter and the Chamber of Secrets', publicationDate=1998-07-02}
09:04:52.506 [main] INFO com.iluwatar.currying.App -- Sci-fi Books:
09:04:52.506 [main] INFO com.iluwatar.currying.App -- Book{genre=SCIFI, author='Frank Herbert', title='Dune', publicationDate=1965-08-01}
09:04:52.506 [main] INFO com.iluwatar.currying.App -- Book{genre=SCIFI, author='Isaac Asimov', title='Foundation', publicationDate=1942-05-01}
```

## When to Use the Currying Pattern in Java

* When functions need to be called with some arguments preset in Java.
* In functional programming languages or paradigms to simplify functions that take multiple arguments.
* To improve code reusability and composability by breaking down functions into simpler, unary functions, enhancing the modularity of Java applications.

## Currying Pattern Java Tutorials

* [Currying in Java (Baeldung)](https://www.baeldung.com/java-currying)
* [What Is Currying in Programming (Towards Data Science)](https://towardsdatascience.com/what-is-currying-in-programming-56fd57103431#:~:text=Currying%20is%20helpful%20when%20you,concise%2C%20and%20more%20readable%20solution.)
* [Why the fudge should I use currying? (DailyJS)](https://medium.com/dailyjs/why-the-fudge-should-i-use-currying-84e4000c8743)

## Real-World Applications of Currying Pattern in Java

* Functional programming languages like Haskell, Scala, and JavaScript.
* Java programming, especially with lambda expressions and streams introduced in Java 8.
* Event handling in UIs where a function with specific parameters needs to be triggered upon an event.
* APIs that require configuration with multiple parameters.

## Benefits and Trade-offs of Currying Pattern

Benefits:

* Increases function reusability by allowing the creation of specialized functions from more generic ones.
* Enhances code readability and maintainability by breaking complex functions into simpler, single-argument functions.
* Facilitates function composition, leading to more declarative and concise code.

Trade-offs:

* Can lead to performance overhead due to the creation of additional closures.
* May make debugging more challenging, as it introduces additional layers of function calls.
* Can be less intuitive for developers unfamiliar with functional programming concepts.
* As shown in the programmatic example above, curried functions with several parameters have a cumbersome type signature in Java.

## Related Java Design Patterns

* Function Composition: Currying is often used in conjunction with function composition to enable more readable and concise code.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): While not the same, currying shares the decorator pattern's concept of wrapping functionality.
* [Factory](https://java-design-patterns.com/patterns/factory/): Currying can be used to create factory functions that produce variations of a function with certain arguments preset.

## References and Credits

* [Functional Programming in Java: Harnessing the Power Of Java 8 Lambda Expressions](https://amzn.to/3TKeZPD)
* [Java 8 in Action: Lambdas, Streams, and functional-style programming](https://amzn.to/3J6vEaW)
* [Modern Java in Action: Lambdas, streams, functional and reactive programming](https://amzn.to/3J6vJLM)
