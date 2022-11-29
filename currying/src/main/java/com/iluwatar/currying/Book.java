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
package com.iluwatar.currying;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;
import lombok.AllArgsConstructor;

/**
 * Book class.
 */
@AllArgsConstructor
public class Book {
  private final Genre genre;
  private final String author;
  private final String title;
  private final LocalDate publicationDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(author, book.author)
            && Objects.equals(genre, book.genre)
            && Objects.equals(title, book.title)
            && Objects.equals(publicationDate, book.publicationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, genre, title, publicationDate);
  }

  @Override
  public String toString() {
    return "Book{" + "genre=" + genre + ", author='" + author + '\''
            + ", title='" + title + '\'' + ", publicationDate=" + publicationDate + '}';
  }

  /**
   * Curried book builder/creator function.
   */
  static Function<Genre, Function<String, Function<String, Function<LocalDate, Book>>>> book_creator
      = bookGenre
          -> bookAuthor
              -> bookTitle
                  -> bookPublicationDate
                      -> new Book(bookGenre, bookAuthor, bookTitle, bookPublicationDate);

  /**
   * Implements the builder pattern using functional interfaces to create a more readable book
   * creator function. This function is equivalent to the BOOK_CREATOR function.
   */
  public static AddGenre builder() {
    return genre
        -> author
            -> title
                -> publicationDate
                    -> new Book(genre, author, title, publicationDate);
  }

  /**
   * Functional interface which adds the genre to a book.
   */
  public interface AddGenre {
    Book.AddAuthor withGenre(Genre genre);
  }

  /**
   * Functional interface which adds the author to a book.
   */
  public interface AddAuthor {
    Book.AddTitle withAuthor(String author);
  }

  /**
   * Functional interface which adds the title to a book.
   */
  public interface AddTitle {
    Book.AddPublicationDate withTitle(String title);
  }

  /**
   * Functional interface which adds the publication date to a book.
   */
  public interface AddPublicationDate {
    Book withPublicationDate(LocalDate publicationDate);
  }
}