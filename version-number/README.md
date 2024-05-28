---
title: Version Number
category: Data access
language: en
tag:
    - Compatibility
    - Data access
    - Persistence
    - State tracking
    - Versioning
---

## Also known as

* Entity Versioning
* Versioning

## Intent

Ensure data consistency and integrity by tracking changes to data with version numbers.

## Explanation

Real-world example

> Consider a library system where multiple librarians can update the details of books simultaneously. Each book entry in the library's database has a version number. When a librarian wants to update a book's details, the system checks the version number of the entry. If the version number matches the current version in the database, the update proceeds, and the version number is incremented. If the version number has changed, it means another librarian has already updated the book details, prompting the system to notify the librarian of the conflict and suggesting a review of the latest changes. This ensures that updates do not overwrite each other unintentionally, maintaining data integrity and consistency.

In plain words

> Version Number pattern grants protection against concurrent updates to same entity.

Wikipedia says

> The Version Number pattern is a technique used to manage concurrent access to data in databases and other data stores. It involves associating a version number with each record, which is incremented every time the record is updated. This pattern helps ensure that when multiple users or processes attempt to update the same data simultaneously, conflicts can be detected and resolved.

**Programmatic Example**

Alice and Bob are working on the book, which stored in the database. Our heroes are making changes simultaneously, and we need some mechanism to prevent them from overwriting each other.

We have a `Book` entity, which is versioned, and has a copy-constructor:

```java
@Getter
@Setter
public class Book {
    
    private long id;
    private String title = "";
    private String author = "";
    private long version = 0; // version number

    public Book(Book book) {
        this.id = book.id;
        this.title = book.title;
        this.author = book.author;
        this.version = book.version;
    }
}
```

We also have `BookRepository`, which implements concurrency control:

```java
public class BookRepository {
    
  private final Map<Long, Book> collection = new HashMap<>();

  public void update(Book book) throws BookNotFoundException, VersionMismatchException {
    if (!collection.containsKey(book.getId())) {
      throw new BookNotFoundException("Not found book with id: " + book.getId());
    }

    var latestBook = collection.get(book.getId());
    if (book.getVersion() != latestBook.getVersion()) {
      throw new VersionMismatchException(
        "Tried to update stale version " + book.getVersion()
          + " while actual version is " + latestBook.getVersion()
      );
    }

    // update version, including client representation - modify by reference here
    book.setVersion(book.getVersion() + 1);

    // save book copy to repository
    collection.put(book.getId(), new Book(book));
  }

  public Book get(long bookId) throws BookNotFoundException {
    if (!collection.containsKey(bookId)) {
      throw new BookNotFoundException("Not found book with id: " + bookId);
    }

    // return copy of the book
    return new Book(collection.get(bookId));
  }
}
```

Here's the version number pattern in action:

```java
public class App {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws
            BookDuplicateException,
            BookNotFoundException,
            VersionMismatchException {
        var bookId = 1;

        var bookRepository = new BookRepository();
        var book = new Book();
        book.setId(bookId);
        bookRepository.add(book); // adding a book with empty title and author
        LOGGER.info("An empty book with version {} was added to repository", book.getVersion());

        // Alice and Bob took the book concurrently
        final var aliceBook = bookRepository.get(bookId);
        final var bobBook = bookRepository.get(bookId);

        aliceBook.setTitle("Kama Sutra"); // Alice has updated book title
        bookRepository.update(aliceBook); // and successfully saved book in database
        LOGGER.info("Alice updates the book with new version {}", aliceBook.getVersion());

        // now Bob has the stale version of the book with empty title and version = 0
        // while actual book in database has filled title and version = 1
        bobBook.setAuthor("Vatsyayana Mallanaga"); // Bob updates the author
        try {
            LOGGER.info("Bob tries to update the book with his version {}", bobBook.getVersion());
            bookRepository.update(bobBook); // Bob tries to save his book to database
        } catch (VersionMismatchException e) {
            // Bob update fails, and book in repository remained untouchable
            LOGGER.info("Exception: {}", e.getMessage());
            // Now Bob should reread actual book from repository, do his changes again and save again
        }
    }
}
```

Program output:

```
14:51:04.119 [main] INFO com.iluwatar.versionnumber.App -- An empty book with version 0 was added to repository
14:51:04.122 [main] INFO com.iluwatar.versionnumber.App -- Alice updates the book with new version 1
14:51:04.122 [main] INFO com.iluwatar.versionnumber.App -- Bob tries to update the book with his version 0
14:51:04.123 [main] INFO com.iluwatar.versionnumber.App -- Exception: Tried to update stale version 0 while actual version is 1
```

## Applicability

* Use when you need to handle concurrent data modifications in a distributed system.
* Suitable for systems where data consistency and integrity are crucial.
* Ideal for applications using databases that support versioning or row versioning features.

## Tutorials

* [JPA entity versioning (byteslounge.com)](https://www.byteslounge.com/tutorials/jpa-entity-versioning-version-and-optimistic-locking)
* [Optimistic Locking in JPA (Baeldung)](https://www.baeldung.com/jpa-optimistic-locking)
* [Versioning Entity (java2s.com)](http://www.java2s.com/Tutorial/Java/0355__JPA/VersioningEntity.htm)

## Known Uses

* Hibernate (Java Persistence API) uses version numbers to implement optimistic locking.
* Microsoft SQL Server and Oracle databases support version-based concurrency control.
* Apache CouchDB and other NoSQL databases implement versioning for conflict resolution.
* [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#index-versioning)
* [Apache Solr](https://lucene.apache.org/solr/guide/6_6/updating-parts-of-documents.html)

## Consequences

Benefits:

* Improves data consistency and integrity.
* Reduces the likelihood of lost updates in concurrent environments.
* Provides a mechanism for conflict detection and resolution.

Trade-offs:

* Requires additional logic for version checking and handling conflicts.
* Can lead to increased complexity in database schema and application logic.
* Potential performance overhead due to version checks and conflict resolution.

## Related Patterns

* [Optimistic Offline Lock](https://java-design-patterns.com/patterns/optimistic-offline-lock/): Uses version numbers to detect conflicts rather than preventing them from occurring.
* Pessimistic Offline Lock: An alternative approach to concurrency control where data is locked for updates to prevent conflicts.

## Credits

* [Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems](https://amzn.to/3y6yv1z)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Java Persistence with Hibernate](https://amzn.to/44tP1ox)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
