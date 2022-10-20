package com.iluwatar.remote.facade.AssemblersFacades;

import com.iluwatar.remote.facade.Domain.Customer;
import com.iluwatar.remote.facade.DTOs.CustomerDTO;
import com.iluwatar.remote.facade.Domain.Domain;

public class CustomerDTOAssembler {
    /**
     * create a dataObject for a customer with their details
     * @param cstmr
     */
    public static CustomerDTO makeCustomerDTO (Customer cstmr) {
        CustomerDTO customer = new CustomerDTO();
        customer.name = cstmr.getName();
        customer.phone = cstmr.getPhone();
        customer.address = cstmr.getAddress();
        return customer;
    }
    /**
     * update Customers details
     * @param dataObject
     */
    public static void updateCustomer(CustomerDTO dataObject) {
        Customer c = null;
        for(Customer cstmr : Domain.customers) {
            if (cstmr.getName().equals(dataObject.name)){
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
     * create a customer using a single data call on the dataObject
     * @param dataObject
     */
    public static void  makeCustomer(CustomerDTO dataObject){
        Customer c = new Customer(dataObject.name, dataObject.phone, dataObject.address);
        Domain.customers.add(c);
    }


}
