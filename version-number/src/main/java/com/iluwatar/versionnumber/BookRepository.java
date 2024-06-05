/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.versionnumber;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This repository represents simplified database.
 * As a typical database do, repository operates with copies of object.
 * So client and repo has different copies of book, which can lead to concurrency conflicts
 * as much as in real databases.
 */
public class BookRepository {
  private final ConcurrentHashMap<Long, Book> collection = new ConcurrentHashMap<>();
  private final Object lock = new Object();

  /**
   * Adds book to collection.
   * Actually we are putting copy of book (saving a book by value, not by reference);
   */
  public void add(Book book) throws BookDuplicateException {
    if (collection.containsKey(book.getId())) {
      throw new BookDuplicateException("Duplicated book with id: " + book.getId());
    }

    // add copy of the book
    collection.put(book.getId(), new Book(book));
  }

  /**
   * Updates book in collection only if client has modified the latest version of the book.
   */
  public void update(Book book) throws BookNotFoundException, VersionMismatchException {
    if (!collection.containsKey(book.getId())) {
      throw new BookNotFoundException("Not found book with id: " + book.getId());
    }

    // used synchronized block to ensure only one thread compares and update the version
    synchronized (lock) {
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
  }

  /**
   * Returns book representation to the client.
   * Representation means we are returning copy of the book.
   */
  public Book get(long bookId) throws BookNotFoundException {
    if (!collection.containsKey(bookId)) {
      throw new BookNotFoundException("Not found book with id: " + bookId);
    }

    // return copy of the book
    return new Book(collection.get(bookId));
  }
}
