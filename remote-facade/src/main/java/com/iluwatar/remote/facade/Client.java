package com.iluwatar.remote.facade;

import com.iluwatar.remote.facade.AssemblersFacades.CustomerDTOAssembler;
import com.iluwatar.remote.facade.AssemblersFacades.RemoteFacade;
import com.iluwatar.remote.facade.DTOs.CustomerDTO;
import com.iluwatar.remote.facade.Domain.Customer;
import com.iluwatar.remote.facade.Domain.Domain;

/**
 * The Remote Facade pattern is a design pattern in which a data transfer object is used to
 * serve related information together to avoid multiple calls that can be costly.
 * In this example we will create new Customers amy and john and add their details in a list {@link Domain}
 * through the creation of a Data Transfer Object in {@link CustomerDTOAssembler} and using the {@link RemoteFacade} to
 * call the methods in {@link CustomerDTOAssembler} and adding them to the list in a single call instead of adding the details separately.
 */

public class Client {
    public static void main(String[] args) {
        Customer amy = new Customer("Amy Adams" , " 0490490490", " 88 Radford Street");
        Customer john = new Customer("John Adams" , " 0449944994", " 12 Kingsford Street");
        RemoteFacade.makeClient(CustomerDTOAssembler.makeCustomerDTO(amy));
        RemoteFacade.makeClient(CustomerDTOAssembler.makeCustomerDTO(john));

        System.out.println(Domain.getCustomers());


    }
}
