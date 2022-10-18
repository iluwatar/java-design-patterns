package com.iluwatar.auditlog;

/**
 * The AuditLog pattern is a simple log of changes, intended to be easily written and non-intrusive.
 *
 * <p>An audit log is the simplest method of tracking temporal information. The key idea is that
 * whenever something significant happens, a record is written to indicate what has happened and
 * when it has occurred. </p>
 *
 * <p>In this particular example, an audit log {@link AuditLog} is updated whenever
 * properties of the {@link Customer} class change, which is then stored within the file
 * <a href="file:./etc/log.txt">/etc/log.txt</a>. </p>
 */
public class App {
  /**
   * Main method.
   *
   * @param args Arguments for main method (unused).
   */
  public static void main(String[] args) {
    SimpleDate.setToday(new SimpleDate(2000, 5, 7));
    Customer john = new Customer("John Smith", 1);
    john.setAddress("1234 Street St", new SimpleDate(1999, 1, 4));

    // change dates to simulate time passing
    SimpleDate.setToday(new SimpleDate(2001, 6, 3));

    // note that john's address and name have changed in the past
    john.setName("John Johnson", new SimpleDate(2001, 4, 17));
    john.setAddress("4321 House Ave", new SimpleDate(2000, 8, 23));


  }
}
