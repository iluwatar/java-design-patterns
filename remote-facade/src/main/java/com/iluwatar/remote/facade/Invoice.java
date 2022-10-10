package com.iluwatar.remote.facade;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private Customer customer;
    private List<Product> shoppingCart;
    private static final double GST = 0.1;

    public Customer getCustomer() { return customer; }
    public List<Product> getShoppingCart() { return shoppingCart; }

    public Invoice(Customer cstmr) {
        customer = cstmr;
        shoppingCart = new ArrayList<Product>();
    }
    /**
    Adding a product to shopping cart
     @param prdct
     @return void
    */
    public void addProduct(Product prdct) {
        shoppingCart.add(prdct);
    }
    /**
     Example business computatioon for an online store
     @return void
     */
    public double totalCart() {
        double total = 0;
        for (Product prdct: shoppingCart) {
            total += prdct.getPrice();
        }
        total = total*GST;
        return total;
    }


}
