package com.iluwater.fieild;

import com.iluwater.fieild.Services.BookRepository;
import com.iluwater.fieild.Services.BookService;
import org.mockito.Mock;

public class BookTestApp {
  private BookRepository bookRepository;
  public static void main(String... args) {

    BookTest bt=new BookTest();
  bt.checkAuthorNotNull();
  bt.checkIdNotNull();
  bt.checkTitleNotNull();
  bt.checkTwoIdsNotEqual();

  }
  }
