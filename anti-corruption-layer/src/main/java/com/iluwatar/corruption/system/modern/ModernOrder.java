package com.iluwatar.corruption.system.modern;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The class represents an order in the modern system.
 */
@Data
@AllArgsConstructor
public class ModernOrder {
  private String id;
  private Customer customer;

  private Shipment shipment;

  private String extra;


}
