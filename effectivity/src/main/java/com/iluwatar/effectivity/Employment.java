package com.iluwatar.effectivity;

/**
 * Employment denotes an employment with a given company over some date range.
 */
public class Employment {
  private DateRange effective;
  private Company company;

  Company company() {
    return company;
  }

  Employment(Company company, SimpleDate startDate) {
    this.company = company;
    effective = DateRange.startingOn(startDate);
  }

  Employment(Company company, DateRange effective) {
    this.company = company;
    this.effective = effective;
  }

  void end(SimpleDate endDate) {
    effective = new DateRange(effective.getStartDate(), endDate);
  }

  boolean isEffectiveOn(SimpleDate arg) {
    return effective.includes(arg);
  }

  void setEffectivity(DateRange arg) {
    effective = arg;
  }
}
