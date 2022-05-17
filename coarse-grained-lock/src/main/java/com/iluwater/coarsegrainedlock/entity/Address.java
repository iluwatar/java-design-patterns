package com.iluwater.coarsegrainedlock.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Address")
public class Address {
  private Customer customer;
  private int customerID;
  private String city;
  private String state;

  public Address(Customer customer,String city,String state){
    this.customer=customer;
    this.customerID=customer.getCustomerID();
    this.city=city;
    this.state=state;
  }
}
