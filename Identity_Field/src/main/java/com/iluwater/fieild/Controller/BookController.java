package com.iluwater.fieild.Controller;

import com.iluwater.fieild.Model.Book;
import com.iluwater.fieild.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  // Create a new book
  @PostMapping
  public Book createBook(@RequestBody String title, String Author) {
    return bookService.createBook(title,Author);
  }

  // Get a book by ID
  @GetMapping("/{id}")
  public Book getBook(@PathVariable Long id) {
    return bookService.getBookById(id);
  }

}
