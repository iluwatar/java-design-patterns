package com.iluwater.coarsegrainedlock.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="Customer")
public class Customer {
  private int customerID;
  private String lastName;
  private String firstName;
  private int itemsOrdered;
  private List<Address> addresses;

  public Customer(int customerID,String lastName,String firstName,int itemsOrdered){
    this.customerID=customerID;
    this.lastName=lastName;
    this.firstName=firstName;
    this.itemsOrdered=itemsOrdered;
    this.addresses=new ArrayList<>();
  }

  public int getCustomerID() {
    return customerID;
  }
}
