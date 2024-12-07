package com.iluwater.fieild.Services;
import com.iluwater.fieild.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }
  public Book createBook(String title,String author) {
    return bookRepository.createBook(title,author);
  }
  public Book getBookById(Long id) {
    return bookRepository.getBookById(id);
  }
}
