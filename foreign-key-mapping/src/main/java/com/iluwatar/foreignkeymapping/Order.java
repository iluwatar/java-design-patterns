package com.iluwatar.foreignkeymapping;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Order definition.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
public final class Order implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private int orderNationalId;
  private String orderNumber;
  private int personNationalId;

  @Override
  public String toString() {

    return "Order ID is : " + orderNationalId + " ; Order Number is : " + orderNumber + " ; Person ID is :" + personNationalId;

  }



}