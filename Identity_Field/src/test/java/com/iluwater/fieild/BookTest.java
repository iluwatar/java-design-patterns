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

package com.iluwater.fieild;

import com.iluwater.fieild.controller.BookController;
import com.iluwater.fieild.model.Book;
import com.iluwater.fieild.services.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
  @Mock
  private BookService bookService;
  BookController bookController;

  {
    bookController = new BookController(bookService);
  }

  @Test
  void checkIdNotNull()
  {
    Book book;
    book = bookController.createBook("Design patterns","someone");
    assertNotNull(book.getId());
  }
  @Test
  void checkTwoIdsNotEqual()
  {
    Book book;
    book = bookController.createBook("Design patterns","someone");
    Book book2 = bookController.createBook("Head first","someone");
    assertNotEquals(book.getId(),book2.getId());
  }
  @Test
  void checkTitleNotNull()
  {
    Book book;
    book = bookController.createBook("Design patterns","someone");
    assertNotNull(book.getTitle());
  }
  @Test
  void checkAuthorNotNull()
  {
    Book book;
    book = bookController.createBook("Design patterns", "someone");
    assertNotNull(book.getAuthor());
  }
  void checkSearch()
  {
    Book book = bookController.createBook("Design patterns","someone");
    assertNotNull(bookController.getBook(book.getId()));

  }

}