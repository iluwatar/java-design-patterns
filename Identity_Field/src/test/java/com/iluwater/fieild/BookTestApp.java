package com.iluwater.fieild;

import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;

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
