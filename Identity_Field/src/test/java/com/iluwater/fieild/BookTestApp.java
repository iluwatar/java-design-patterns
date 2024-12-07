package com.iluwater.fieild;

public class BookTestApp {
  public static void main(String... args) {
    BookTest bt=new BookTest();
    bt.checkAuthorNotNull();
    bt.checkIdNotNull();
    bt.checkTitleNotNull();
    bt.checkTwoIdsNotEqual();
    bt.checkSearch();

  }
  }
