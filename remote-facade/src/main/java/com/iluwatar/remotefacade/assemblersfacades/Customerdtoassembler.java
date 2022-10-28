package com.iluwatar.remotefacade.assemblersfacades;
import com.iluwatar.remotefacade.domain.Customer;
import com.iluwatar.remotefacade.domain.Store;
import com.iluwatar.remotefacade.dto.Customerdto;

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
  * create a customer using a single data call on the dataObject.
   *
  * @param dataObject .
   */
  public static void  makeCustomer(Customerdto dataObject) {
    Customer c = new Customer(dataObject.name, dataObject.phone, dataObject.address);
    Store.customers.add(c);
  }
}
