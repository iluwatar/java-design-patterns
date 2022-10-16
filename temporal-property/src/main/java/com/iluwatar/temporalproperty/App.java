package com.iluwatar.temporalproperty;


/**
 * The Temporal Property pattern is a property that changes over time.
 *
 * <p>Instead of recording a property of a class as it is now, it may be useful
 * to consider about some points in time where this property has changed.</p>
 *
 * <p>In this example, the {@link Customer} class has an address with temporal property.
 * In particular, the history of all addresses are stored within a {@link AddressHistory}
 * instance which is an implementation of Temporal Property described as a Temporal Collection</p>
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    Customer john = new Customer(1234, "John");

    // set today's calandar date to 1st January 2000
    SimpleDate.setToday(new SimpleDate(2000, 1, 1));

    // set john's address at different points in time

    // Set the address where john lived on the 4th of february 1996 to 124 ave
    john.putAddress("124 ave", new SimpleDate(1996, 2, 4));
    // Set the address john lives in today to 123 place
    john.putAddress("123 place");

    // set the calandar day to tomorrow, the 2nd of January 2000
    SimpleDate.setToday(new SimpleDate(2000, 1, 2));

    // get john's address today
    System.out.println("John lives at " + john.getAddress() + " today");
    // get the address that john lived at on the 3rd of June 1997
    System.out.println("John lived at " + john.getAddress(new SimpleDate(1997, 7, 3))
            + " on the 3rd of June 1997");


  }
}
