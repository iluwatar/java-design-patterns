package com.iluwatar.publish.subscribe.model;

/**
 * This enum defines the content for {@link Topic} CUSTOMER_SUPPORT.
 */
public enum CustomerSupportContent {

  DE("customer.support@test.de"),
  AT("customer.support@test.at");

  private final String email;

  CustomerSupportContent(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
