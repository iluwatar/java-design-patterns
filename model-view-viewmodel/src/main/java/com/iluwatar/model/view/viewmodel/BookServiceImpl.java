/*
 * The MIT License (MIT)
 *
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Module Model-view-viewmodel is using ZK framework
 * ZK framework is licensed under LGPL and the license can be found at lgpl-3.0.txt
 */

package com.iluwatar.model.view.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {
  private List<Book> designPatternBooks = new ArrayList<>();

  /** Initializes Book Data.
  * To be used and passed along in load method
  * In this case, list design pattern books are initialized to be loaded.
  */
  public BookServiceImpl() {
    designPatternBooks.add(new Book(
        "Head First Design Patterns: A Brain-Friendly Guide",
        "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson",
        "Head First Design Patterns Description"));
    designPatternBooks.add(new Book(
        "Design Patterns: Elements of Reusable Object-Oriented Software",
        "Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides",
        "Design Patterns Description"));
    designPatternBooks.add(new Book(
        "Patterns of Enterprise Application Architecture", "Martin Fowler",
        "Patterns of Enterprise Application Architecture Description"));
    designPatternBooks.add(new Book(
        "Design Patterns Explained", "Alan Shalloway, James Trott",
        "Design Patterns Explained Description"));
    designPatternBooks.add(new Book(
        "Applying UML and Patterns: An Introduction to "
         + "Object-Oriented Analysis and Design and Iterative Development",
        "Craig Larman", "Applying UML and Patterns Description"));
  }

  public List<Book> load() {
    return designPatternBooks;
  }

}
