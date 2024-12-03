package com.iluwatar;

import org.junit.Test;

import static org.junit.Assert.*;


public class BookStoreTest {

  @Test
  public void testWithDiscount() {
    Book bookWithDiscount = new Book("Superman Vol. 1", 20.00, true, 5);
    BookStore bookStoreWithDiscount = DataPreparation.prepareBook(bookWithDiscount);

    assertNotNull(bookStoreWithDiscount);
    assertTrue(bookStoreWithDiscount.getName().contains("Superman Vol. 1"));
    assertEquals(15.00, bookStoreWithDiscount.getDiscountPrice(), 0.01);
    assertTrue(bookStoreWithDiscount.isInStock());
  }

  @Test
  public void testWithoutDiscount() {
    Book bookWithoutDiscount = new Book("Superman Vol. 1", 20.00, false, 5);
    BookStore bookStoreWithoutDiscount = DataPreparation.prepareBook(bookWithoutDiscount);

    assertNotNull(bookStoreWithoutDiscount);
    assertTrue(bookStoreWithoutDiscount.getName().contains("Superman Vol. 1"));
    assertEquals(20.00, bookStoreWithoutDiscount.getDiscountPrice(), 0.01);
    assertTrue(bookStoreWithoutDiscount.isInStock());
  }

  @Test
  public void testWithOutOfStock() {
    Book bookOutOfStock = new Book("Superman Vol. 1", 20.00, true, 0);
    BookStore bookStoreOutOfStock = DataPreparation.prepareBook(bookOutOfStock);

    assertNotNull(bookStoreOutOfStock);
    assertTrue(bookStoreOutOfStock.getName().contains("Superman Vol. 1"));
    assertEquals(15.00, bookStoreOutOfStock.getDiscountPrice(), 0.01);
    assertFalse(bookStoreOutOfStock.isInStock());
  }

}
