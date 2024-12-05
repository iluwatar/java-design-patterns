package com.iluwatar;

import org.junit.Test;

import static org.junit.Assert.*;



import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;


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

  @Test
  public void testPrepareZeroPrice() {
    Book book = new Book("Zero Price Book", 0.00, true, 5);
    BookStore bookStore = DataPreparation.prepareBook(book);

    assertNotNull(bookStore);
    assertEquals(0.00, bookStore.getDiscountPrice(), 0.01);
    assertTrue(bookStore.isInStock());
  }

  @Test
  public void testPrepareNegativeStock() {
    Book book = new Book("Negative Stock Book", 10.00, false, -1);
    BookStore bookStore = DataPreparation.prepareBook(book);

    assertNotNull(bookStore);
    assertFalse(bookStore.isInStock());
  }

  @Test
  public void testPresentInStock() {
    BookStore bookInStock = new BookStore("Wonder Woman Vol. 1", 15.00, 12.00, true);
    String htmlOutput = Presentation.presentBook(bookInStock);

    assertNotNull(htmlOutput);
    assertTrue(htmlOutput.contains("<h1>Wonder Woman Vol. 1</h1>"));
    assertTrue(htmlOutput.contains("<p>Price: $15.0</p>"));
    assertTrue(htmlOutput.contains("<p>Discounted Price: $12.0</p>"));
    assertTrue(htmlOutput.contains("<p>Status: In Stock</p>"));
  }

  @Test
  public void testPresentOutOfStock() {
    BookStore bookOutOfStock = new BookStore("Wonder Woman Vol. 1", 15.00, 12.00, false);
    String htmlOutput = Presentation.presentBook(bookOutOfStock);

    assertNotNull(htmlOutput);
    assertTrue(htmlOutput.contains("<h1>Wonder Woman Vol. 1</h1>"));
    assertTrue(htmlOutput.contains("<p>Price: $15.0</p>"));
    assertTrue(htmlOutput.contains("<p>Discounted Price: $12.0</p>"));
    assertTrue(htmlOutput.contains("<p>Status: Out of Stock</p>"));
  }

  @Test
  public void testMain() {
    // Redirect System.out to capture logger output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    Logger.getLogger("").addHandler(new java.util.logging.ConsoleHandler() {
      {
        setOutputStream(printStream);
      }
    });

    // Run the main method
    App.main(new String[]{});

    // Check output contains expected HTML
    String loggedOutput = outputStream.toString();
    assertTrue(loggedOutput.contains("<h1>Batman Vol. 1: The Court of Owls</h1>"));
    assertTrue(loggedOutput.contains("<p>Price: $11.6</p>"));
    assertTrue(loggedOutput.contains("<p>Discounted Price: $8.7</p>"));
    assertTrue(loggedOutput.contains("<p>Status: In Stock</p>"));
  }
}
