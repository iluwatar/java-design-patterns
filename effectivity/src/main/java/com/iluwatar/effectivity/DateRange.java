package com.iluwatar.effectivity;

/**
 * DateRange consists of a start and an end date, and a method includes to test
 * if a {@link SimpleDate} is within those start and end dates.
 */
public class DateRange {
  private final SimpleDate startDate;
  private final SimpleDate endDate;

  public DateRange(SimpleDate startDate, SimpleDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Returns a date range that starts at the given date and never ends.
   *
   * @param date The starting date.
   * @return A date range starting at date, and ending at integer max year, month, day.
   */
  public static DateRange startingOn(SimpleDate date) {
    return new DateRange(date,
            new SimpleDate(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
  }

  /**
   * Returns if the given date is within the start and end bounds of this range.
   *
   * @param arg Date to check is within bounds.
   * @return True if the date is within bounds (inclusive)
   */
  public boolean includes(SimpleDate arg) {
    return (getStartDate().compareTo(arg) <= 0 & getEndDate().compareTo(arg) >= 0);
  }

  public SimpleDate getStartDate() {
    return startDate;
  }

  public SimpleDate getEndDate() {
    return endDate;
  }
}
