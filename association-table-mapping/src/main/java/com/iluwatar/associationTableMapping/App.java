/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.iluwatar.associationTableMapping;

import java.util.HashSet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Objects can handle multi-valued fields by using collections as field values. Relational databases
 * don't have this feature and are constrained to single-valued fields only. When you're mapping a
 * one-to-many association you can handle this using Foreign Key Mapping, essentially using a
 * foreign key for the single-valued end of the association. But a many-to-many association can't do
 * this because there is no single-valued end to hold the foreign key. The solution is create an
 * extra table to record the relationship. Then use Association Table Mapping to map the
 * multi-valued field to this link table.
 * <p>
 * In this example is used Spring data repository for database access (described in Repository
 * pattern). In this example we show how to save three authors {@link Author} and two books
 * {@link Book} into database and they have many-to-many relationship. Then we read all book's from
 * database and show their titles and authors.
 */
public class App {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext("applicationContext.xml");
    BookRepository bookRepository = context.getBean(BookRepository.class);

    bookRepository.deleteAll();
    // prepare authors
    final Author authorA = new Author("Author A");
    final Author authorB = new Author("Author B");
    final Author authorC = new Author("Author C");
    // save all books and authors
    bookRepository.save(new HashSet<Book>() {
      {
        add(new Book("Book A", new HashSet<Author>() {
          {
            add(authorA);
            add(authorB);
          }
        }));

        add(new Book("Book B", new HashSet<Author>() {
          {
            add(authorA);
            add(authorC);
          }
        }));
      }
    });
    // print all books
    for (Book book : bookRepository.findAll()) {
      System.out.println(book);
    }

    context.close();
  }
}
