package com.iluwatar.remote.facade;

import java.io.Serializable;
import java.util.List;

public class InvoiceDTO implements Serializable {
    public CustomerDTO customer;
    public ProductDTO[] shoppingCart;
    public String GST;
}
