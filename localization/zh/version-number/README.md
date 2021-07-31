---
layout: pattern
title: Version Number
folder: versionnumber
permalink: /patterns/versionnumber/zh
description: Entity versioning with version number
language: zh

categories:
 - Concurrency

tags:
 - Data access
 - Microservices
---

## 名字 / 分类

版本号

## 或称

实体版本控制，乐观锁。

## 目的

解决多个客户端尝试同时更新同一实体时的并发冲突。

## 解释

现实世界的例子

> 爱丽丝（Alice）和鲍勃（Bob）正在管理书，该书存储在数据库中。 我们的英雄们正在同时进行更改，我们需要某种机制来防止他们相互覆盖。

通俗地说

> 版本号模式可防止对同一实体进行并发更新。

维基百科说

> 乐观并发控制假设多个事务可以频繁完成而不会互相干扰。 在运行时，事务使用数据资源而不获取这些资源的锁。 在提交之前，每个事务都将验证没有其他事务修改了已读取的数据。如果检查发现有冲突的修改，则提交的事务将回滚并可以重新启动。

**程序示例**

我们有`Book` 已版本化的实体，它有一个复制构造函数。

```java
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

  // getters and setters are omitted here
}
```

我们还有一个 `BookRepository`, 它实现了并发控制。

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

这是实践中的并发控制：

```java
var bookId = 1;
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
```

程序输出：

```java
Alice updates the book with new version 1
Bob tries to update the book with his version 0
Exception: Tried to update stale version 0 while actual version is 1
```

## 类图

![alt text](../../version-number/etc/version-number.urm.png "Version Number pattern class diagram")

## 适用性

将版本号用于：

* 解决对数据的并发写访问
* 强的数据一致性

## 教程
* [Version Number Pattern Tutorial](http://www.java2s.com/Tutorial/Java/0355__JPA/VersioningEntity.htm)

## 已知用途
 * [Hibernate](https://vladmihalcea.com/jpa-entity-version-property-hibernate/)
 * [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#index-versioning)
 * [Apache Solr](https://lucene.apache.org/solr/guide/6_6/updating-parts-of-documents.html)

## 意义
版本号模式允许实现并发控制，通常通过乐观离线锁模式来完成。

## 相关模式
* [Optimistic Offline Lock](https://martinfowler.com/eaaCatalog/optimisticOfflineLock.html)

## 鸣谢
* [Optimistic Locking in JPA](https://www.baeldung.com/jpa-optimistic-locking)
* [JPA entity versioning](https://www.byteslounge.com/tutorials/jpa-entity-versioning-version-and-optimistic-locking)
* [J2EE Design Patterns](http://ommolketab.ir/aaf-lib/axkwht7wxrhvgs2aqkxse8hihyu9zv.pdf)
