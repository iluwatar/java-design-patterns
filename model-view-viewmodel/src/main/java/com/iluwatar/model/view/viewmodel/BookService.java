package com.iluwatar.model.view.viewmodel;

import java.util.List;

public interface BookService {
  /* List all books
   * @return all books
   */
  public List<Book> load();
}
