package com.iluwatar.effectivity;

/**
 * Class that is used to wrap year, month, day values without correction.
 * Note that this class also does not consider different time zones being used in an implementation.
 */
public class SimpleDate implements Comparable<SimpleDate> {
  private final int year;
  private final int month;
  private final int day;

  private static SimpleDate today = new SimpleDate(0, 0, 0);

  SimpleDate(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SimpleDate that = (SimpleDate) o;
    return year == that.year && month == that.month && day == that.day;
  }

  @Override
  public int compareTo(SimpleDate otherDate) {
    if (equals(otherDate)) {
      return 0;
    }

    if (year > otherDate.year) {
      return 1;
    } else if (year < otherDate.year) {
      return -1;
    }

    if (month > otherDate.month) {
      return 1;
    } else if (month < otherDate.month) {
      return -1;
    }

    if (day > otherDate.day) {
      return 1;
    } else {
      return -1;
    }
  }

  /**
   * Set the value considered to be today by the simple date.
   *
   * @param newToday The date to be set as 'today'.
   */
  public static void setToday(SimpleDate newToday) {
    today = newToday;
  }

  /**
   * Gets the date set to be today.
   *
   * @return The date set to day
   */
  public static SimpleDate getToday() {
    return today;
  }

  @Override
  public String toString() {
    return "" + year + ", " + month + ", " + day;
  }
}