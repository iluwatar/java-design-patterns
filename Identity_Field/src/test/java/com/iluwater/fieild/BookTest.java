package com.iluwater.fieild;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
  @Test
  void checkIdNotNull()
  {
    Book book= new Book("Design patterns","someone");
    assertNotNull(book.getId());
  }
  void checkTwoIdsNotEqual()
  {
    Book book= new Book("Design patterns","someone");
    Book book2= new Book("Head first","someone");
    assertNotEquals(book.getId(),book2.getId());
  }
  void checkTitleNotNull()
  {
    Book book= new Book("Design patterns","someone");
    assertNotNull(book.getTitle());
  }
  void checkAuthorNotNull()
  {
    Book book= new Book("Design patterns","someone");
    assertNotNull(book.getAuthor());
  }

}