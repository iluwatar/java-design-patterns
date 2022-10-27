package com.iluwatar.remote.facade.AssemblersFacades;

import com.iluwatar.remote.facade.DTOs.Customerdto;

/**
 * Use the Assembler to create the facade here.
 */
public class RemoteFacade {
  /**
   * using the  methods in the {@link Customerdtoassembler} accesses the customer details in one single call.
   *
   * @param dataObject .
   */

  public static void makeClient(Customerdto dataObject) {
    Customerdtoassembler.makeCustomer(dataObject);
    Customerdtoassembler.updateCustomer(dataObject);
  }
}
