package com.iluwatar.corruption.system.legacy;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The class represents an order in the legacy system.
 * The class is used by the legacy system to store the data.
 */
@Data
@AllArgsConstructor
public class LegacyOrder {
  private String id;
  private String customer;

  private String item;
  private int qty;
  private int price;
}
