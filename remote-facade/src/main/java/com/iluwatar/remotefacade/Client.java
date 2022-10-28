package com.iluwatar.remotefacade;
import com.iluwatar.remotefacade.assemblersfacades.Customerdtoassembler;
import com.iluwatar.remotefacade.assemblersfacades.RemoteFacade;
import com.iluwatar.remotefacade.domain.Customer;
import com.iluwatar.remotefacade.domain.Store;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
/**
 * The Remote Facade pattern is a design pattern in which a data transfer object is used to
 * serve related information together to avoid multiple calls that can be costly.
 * In this example we will create new Customers amy and john and add their details in a list {@link Store}
 * through the creation of a Data Transfer Object in {@link Customerdtoassembler} and using the {@link RemoteFacade} to
 * call the methods in {@link Customerdtoassembler} and adding them to the list in a single call instead of adding the details separately.
 */
@Slf4j
public class Client {
  /**
   * main App method.
   *
   * @param args .
   */
  public static void main(String[] args) {
    var amy = new Customer("Amy Adams", " 0490490490", " 88 Radford Street");
    var john = new Customer("John Adams", " 0449944994", " 12 Kingsford Street");

    RemoteFacade.makeClient(Customerdtoassembler.makeCustomerdto(amy));
    RemoteFacade.makeClient(Customerdtoassembler.makeCustomerdto(john));

    LOGGER.info("Customer Details: ");
    printCustomerDetails(Store.customers);
  }
  private static void printCustomerDetails(List<Customer> list) {
    list.forEach(customer -> LOGGER.info(String.valueOf(Store.customers)));
  }
}
