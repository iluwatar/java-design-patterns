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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link BookRepository}
 */
class BookRepositoryTest {
  private final long bookId = 1;
  private final BookRepository bookRepository = new BookRepository();

  @BeforeEach
  void setUp() throws BookDuplicateException {
    var book = new Book();
    book.setId(bookId);
    bookRepository.add(book);
  }

  @Test
  void testDefaultVersionRemainsZeroAfterAdd() throws BookNotFoundException {
    var book = bookRepository.get(bookId);
    assertEquals(0, book.getVersion());
  }

  @Test
  void testAliceAndBobHaveDifferentVersionsAfterAliceUpdate() throws BookNotFoundException, VersionMismatchException {
    final var aliceBook = bookRepository.get(bookId);
    final var bobBook = bookRepository.get(bookId);

    aliceBook.setTitle("Kama Sutra");
    bookRepository.update(aliceBook);

    assertEquals(1, aliceBook.getVersion());
    assertEquals(0, bobBook.getVersion());
    var actualBook = bookRepository.get(bookId);
    assertEquals(aliceBook.getVersion(), actualBook.getVersion());
    assertEquals(aliceBook.getTitle(), actualBook.getTitle());
    assertNotEquals(aliceBook.getTitle(), bobBook.getTitle());
  }

  @Test
  void testShouldThrowVersionMismatchExceptionOnStaleUpdate() throws BookNotFoundException, VersionMismatchException {
    final var aliceBook = bookRepository.get(bookId);
    final var bobBook = bookRepository.get(bookId);

    aliceBook.setTitle("Kama Sutra");
    bookRepository.update(aliceBook);

    bobBook.setAuthor("Vatsyayana Mallanaga");
    try {
      bookRepository.update(bobBook);
    } catch (VersionMismatchException e) {
      assertEquals(0, bobBook.getVersion());
      var actualBook = bookRepository.get(bookId);
      assertEquals(1, actualBook.getVersion());
      assertEquals(aliceBook.getVersion(), actualBook.getVersion());
      assertEquals("", bobBook.getTitle());
      assertNotEquals(aliceBook.getAuthor(), bobBook.getAuthor());
    }
  }
}
