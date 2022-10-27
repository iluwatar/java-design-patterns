package com.iluwatar.remotefacade.assemblersfacades;
import com.iluwatar.remotefacade.dto.Customerdto;
import com.iluwatar.remotefacade.domain.Customer;
import com.iluwatar.remotefacade.domain.Domain;

/**
 * act as server with business logic.
 */
public class Customerdtoassembler {
  /**
  * create a dataObject for a customer with their details.
  *
  * @param cstmr .
  */
  public static Customerdto makeCustomerdto(Customer cstmr) {
    Customerdto customer = new Customerdto();
    customer.name = cstmr.getName();
    customer.phone = cstmr.getPhone();
    customer.address = cstmr.getAddress();
    return customer;
  }
  /**
   * update Customers details.
   *
   * @param dataObject .
   */
  public static void updateCustomer(Customerdto dataObject) {
    Customer c = null;
    for (Customer cstmr : Domain.customers) {
      if (cstmr.getName().equals(dataObject.name)) {
        c = cstmr;
        break;
      }
    }
    if (c != null) {
      c.setAddress(dataObject.address);
      c.setPhone(dataObject.phone);
    }
  }
  /**
  * create a customer using a single data call on the dataObject.
   *
  * @param dataObject .
   */
  public static void  makeCustomer(Customerdto dataObject) {
    Customer c = new Customer(dataObject.name, dataObject.phone, dataObject.address);
    Domain.customers.add(c);
  }
}
