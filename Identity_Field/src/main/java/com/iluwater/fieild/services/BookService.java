package com.iluwater.fieild.services;
import com.iluwater.fieild.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *  Book Service Class to implement the logic.
 */
@Service
public class BookService {

  private final BookRepository bookRepository;
  /**
   *  BookService constructor.
   */
  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }
  /**
   *  Create a new book.
   */
  public Book createBook(String title, String author) {
    return bookRepository.createBook(title, author);
  }
  /**
   *  ID Getter.
   */
  public Book getBookById(Long id) {
    return bookRepository.getBookById(id);
  }
}
