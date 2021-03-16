package com.iluwatar.model.view.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class BookViewModel {
  
  @WireVariable
  private List<Book> bookList;
  private Book selectedBook;
  private BookService bookService = new BookServiceImpl();
  
  public Book getSelectedBook() {
    return selectedBook;
  }

  @NotifyChange("selectedBook")
  public void setSelectedBook(Book selectedBook) {
    this.selectedBook = selectedBook;
  }

  public List<Book> getBookList() {
    return bookService.load();
  }
  
  /** Deleting a book.
   * Details pending.
   */
  @Command
  @NotifyChange({"selectedBook","bookList"})
  public void deleteBook() {
    if (selectedBook != null) {
      getBookList().remove(selectedBook);
    }
  }

}
