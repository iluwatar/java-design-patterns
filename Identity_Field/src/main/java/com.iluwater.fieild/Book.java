/*
 * The Book class represents a book entity in the application.
 *
 * <p>This class extends DomainObject and inherits its unique identifier.
 * It includes additional fields and methods specific to a book's attributes,
 * such as title and author.</p>
 */
package com.iluwater.fieild;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Book extends DomainObject {
  private String title;
  private String author;


  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }

}
