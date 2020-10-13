/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link BookRepository}
 */
class BookRepositoryTest {
  @Test
  void testBookRepository() throws BookDuplicateException, BookNotFoundException, VersionMismatchException {
    final long bookId = 1;

    BookRepository bookRepository = new BookRepository();
    Book book = new Book();
    book.setId(bookId);
    bookRepository.add(book);

    assertEquals(0, book.getVersion());

    final Book aliceBook = bookRepository.get(bookId);
    final Book bobBook = bookRepository.get(bookId);

    assertEquals(aliceBook.getTitle(), bobBook.getTitle());
    assertEquals(aliceBook.getAuthor(), bobBook.getAuthor());
    assertEquals(aliceBook.getVersion(), bobBook.getVersion());

    aliceBook.setTitle("Kama Sutra");
    bookRepository.update(aliceBook);

    assertEquals(1, aliceBook.getVersion());
    assertEquals(0, bobBook.getVersion());
    assertEquals(aliceBook.getVersion(), bookRepository.get(bookId).getVersion());
    assertEquals(aliceBook.getTitle(), bookRepository.get(bookId).getTitle());
    assertNotEquals(aliceBook.getTitle(), bobBook.getTitle());

    bobBook.setAuthor("Vatsyayana Mallanaga");
    try {
      bookRepository.update(bobBook);
    } catch (VersionMismatchException e) {
      assertEquals(0, bobBook.getVersion());
      assertEquals(1, bookRepository.get(bookId).getVersion());
      assertEquals(aliceBook.getVersion(), bookRepository.get(bookId).getVersion());
      assertEquals("", bobBook.getTitle());
      assertNotEquals(aliceBook.getAuthor(), bobBook.getAuthor());
    }
  }
}
