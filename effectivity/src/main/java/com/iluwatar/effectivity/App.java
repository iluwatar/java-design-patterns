package com.iluwatar.effectivity;

/**
 * The Effectivity pattern adds a time period to an object to show when it is effective.
 *
 * <p>Many facts are only true for a certain period of time, and so a way to
 * describe these facts is to mark them with a period of time. </p>
 *
 * <p>In this example, each {@link Person} has {@link Employment} at a {@link Company}
 * over certain periods of time. Each {@link Employment} contains a {@link DateRange}
 * that describes the range over which the employment is valid.</p>
 */
public class App {
  /**
   * Main function.
   *
   * @param args Unused arguments.
   */
  public static void main(String[] args) {
    Person bob = new Person("Bob");
    Company aaInc = new Company("AA inc");
    Company bbCo = new Company("BB Company");

    // Bob was employed at A Inc from the 2nd of June 2003, until 4th of February 2008
    DateRange aaIncPeriod = new DateRange(new SimpleDate(2003, 6, 2),
            new SimpleDate(2008, 2, 4));
    Employment aaIncEmployment = new Employment(aaInc, aaIncPeriod);
    bob.addEmployment(aaIncEmployment);

    // Then, Bob started working at B Company from the 19th of March 2008,
    // and has continued working there since.
    bob.addEmployment(bbCo, new SimpleDate(2008, 3, 19));
  }

}
