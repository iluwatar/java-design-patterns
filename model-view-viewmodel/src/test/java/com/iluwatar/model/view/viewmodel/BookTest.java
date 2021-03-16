/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.model.view.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BookTest {

  BookViewModel bvm = new BookViewModel();
  
  @Test
  void testLoadData() {
    assertNotNull(bvm.getBookList());
    assertTrue(bvm.getBookList().get(0).toString().contains("Head First Design Patterns"));
  }

  @Test
  void testSelectedData() {
    bvm.setSelectedBook(new Book("Head First Design Patterns: A Brain-Friendly Guide",
        "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson",
        "Head First Design Patterns Description"));
    assertNotNull(bvm.getSelectedBook());
    assertEquals("Book(name=Head First Design Patterns: A Brain-Friendly Guide, author=Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson, description=Head First Design Patterns Description)", bvm.getSelectedBook().toString());
  }

  @Test
  void testDeleteData() {
    bvm.setSelectedBook(new Book("Head First Design Patterns: A Brain-Friendly Guide",
        "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson",
        "Head First Design Patterns Description"));
    assertTrue(bvm.getBookList().get(0).toString().contains("Head First Design Patterns"));
    bvm.deleteBook();
    assertFalse(bvm.getBookList().get(0).toString().contains("Head First Design Patterns"));
  }

}