package com.iluwatar.remote.facade.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class acting as a database to store details in an Arraylist.
 */
public class Domain {
  public static List<Customer> customers = new ArrayList<Customer>();
  /**
  * get the details of the customers in the array to ensure correctness.
  */

  public static ArrayList<String> getCustomers() {
    ArrayList<String> s = new ArrayList<>();
    for (int i = 0; i < customers.size(); i++) {
      s.add(customers.get(i).getName() + customers.get(i).getPhone() + customers.get(i).getAddress());
    }
    return s;
  }

}
