package com.iluwatar.money;

import lombok.Getter;

/**
 * This enum class represents currency of {@link Money} with USD and EUR predefined.
 */
public enum Currency {
  USD(100, "USD"),
  EUR(100, "USD"),
  CNY(100, "CNY");

  /**
   * cent factor, e.g., 100 means regarding 100 "USD" as 1 real USD.
   */
  @Getter
  private final int centFactor;

  /**
   * currency string representation.
   */
  @Getter
  private final String representation;

  Currency(final int centFactor, final String representation) {
    this.centFactor = centFactor;
    this.representation = representation;
  }
}
