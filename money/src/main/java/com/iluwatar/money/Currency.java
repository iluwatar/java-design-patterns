package com.iluwatar.money;

public enum Currency {
  USD(100, "USD"),
  EUR(100, "USD");

  private int centFactor;
  private String stringRepresentation;

  Currency(int centFactor, String stringRepresentation) {
    this.centFactor = centFactor;
    this.stringRepresentation = stringRepresentation;
  }
}
