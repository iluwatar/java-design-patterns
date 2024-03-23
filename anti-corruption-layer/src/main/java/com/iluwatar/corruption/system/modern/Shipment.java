package com.iluwatar.corruption.system.modern;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The class represents a shipment in the modern system.
 * The class is used by the modern system to store the data.
 */
@Data
@AllArgsConstructor
public class Shipment {
  private String item;
  private int qty;
  private int price;
}
