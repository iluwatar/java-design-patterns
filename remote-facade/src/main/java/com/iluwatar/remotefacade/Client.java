package com.iluwatar.remotefacade;
import com.iluwatar.remotefacade.assemblersfacades.Customerdtoassembler;
import com.iluwatar.remotefacade.assemblersfacades.RemoteFacade;
import com.iluwatar.remotefacade.domain.Customer;
import com.iluwatar.remotefacade.domain.Domain;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
/**
 * The Remote Facade pattern is a design pattern in which a data transfer object is used to
 * serve related information together to avoid multiple calls that can be costly.
 * In this example we will create new Customers amy and john and add their details in a list {@link Domain}
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
    printCustomerDetails(Domain.getCustomers());
  }
  private static void printCustomerDetails(ArrayList<String> list) {
    list.forEach(customer -> LOGGER.info(Domain.getCustomers().toString()));
  }
}
