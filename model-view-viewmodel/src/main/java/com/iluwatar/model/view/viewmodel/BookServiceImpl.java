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
