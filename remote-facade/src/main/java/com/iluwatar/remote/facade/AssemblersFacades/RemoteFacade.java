package com.iluwatar.remote.facade.AssemblersFacades;

import com.iluwatar.remote.facade.DTOs.CustomerDTO;
import com.iluwatar.remote.facade.Domain.Customer;

public class RemoteFacade {
    /**
     * using the makeCustomer and updateCustomer methods in the Assembler
     * RemoteFacade accesses the customer details in one single call
    */

    public static void makeClient(CustomerDTO dataObject){
      CustomerDTOAssembler.makeCustomer(dataObject);
      CustomerDTOAssembler.updateCustomer(dataObject);
    }
}
