package com.iluwatar.remotefacade.test;

import com.iluwatar.remotefacade.assemblersfacades.Customerdtoassembler;
import com.iluwatar.remotefacade.assemblersfacades.RemoteFacade;
import com.iluwatar.remotefacade.domain.Customer;
import com.iluwatar.remotefacade.domain.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ClientTest {

    @Test
    public void checkClientAMY(){
        Customer AMY = new Customer("Amy Adams", "0490490490", "88 Radford Street");
        RemoteFacade.makeClient(Customerdtoassembler.makeCustomerdto(AMY));
        Assertions.assertEquals("Amy Adams", Store.customers.get(1).getName());
        Assertions.assertEquals("0490490490", Store.customers.get(1).getPhone());
        Assertions.assertEquals("88 Radford Street", Store.customers.get(1).getAddress());

    }
    @Test
    public void checkClientJOHN(){
        Customer JOHN = new Customer("John Adams", "0449944994", "12 Kingsford Street");
        RemoteFacade.makeClient(Customerdtoassembler.makeCustomerdto(JOHN));
        Assertions.assertEquals("John Adams", Store.customers.get(0).getName());
        Assertions.assertEquals("0449944994", Store.customers.get(0).getPhone());
        Assertions.assertEquals("12 Kingsford Street", Store.customers.get(0).getAddress());
    }
    @Test
    public void checkCustomerAddition(){
        Assertions.assertEquals(2, Store.customers.size());
    }
}
