package com.iluwatar.foreignkeymapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Person definition.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
public final class Person implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private int personNationalId;
  private String lastName;
  private String firstName;
  private long age;

  @Override
  public String toString() {

    return "Person ID is : " + personNationalId + " ; Person Last Name is : " + lastName + " ; Person First Name is : " + firstName + " ; Age is :" + age;

  }
/*
  public void placeOrder(String text) {
    Order order = new Order(AppDbSimulatorImplementation.getOrderListSize()+1, text, this.personNationalId);
    AppDbSimulatorImplementation.insertOrder(order);
  }*/

  public List<Order> getAllOrder(List<Order> orderList) {
    List<Order> orders = new ArrayList<>();
    for (Order order : orderList) {
      if (this.getPersonNationalId() == order.getPersonNationalId()) {
        orders.add(order);
      }
    }
    return orders;
  }


}