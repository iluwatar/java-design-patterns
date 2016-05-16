/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.data.transfer.object;

/**
 * This example is of online phone orders. We have products, customers and their orders.
 * Products, customers and orders data are saved in their respective json files. 
 * <p>
 * products.json is an array of product object. Product object contains product name, price, etc..
 * <p>
 * customers.json is an array of customer object. Customer object contains customer name, address, etc..
 * <p>
 * Orders.json is an array of order object. Order contains the unique identifier of the customer who 
 * placed the order and unique identifier of the product he/she ordered.
 * <p>
 * I considered the index of product in products json array as its unique identifier for simplicity in coding. 
 * Same is for the customer in customers json array and order in orders json array.
 * <p>
 * OrderDto is the Data transfer object for order details i.e orderIndex, productName, customerName etc..
 * CLient requests for the OrderDto object of a specific orderId through OrderBo object.
 */

public class OrderDto {

  public int orderIndex;
  public String productName;
  public String customerName;
  public String address;
  public double price;

  public OrderDto() {
  }
  
  @Override
  public String toString() {
    return "orderIndex : " + orderIndex 
      + "\nproductName : " + productName 
      + "\ncustomerName : " + customerName 
      + "\nadress : " + address 
      + "\nprice : " + price 
      + "\n";
  }

  public void setOrderIndex(int orderIndex) {
    this.orderIndex = orderIndex;
  }

  public void setProductName(String name) {
    this.productName = name;
  }

  public void setCustomerName(String name) {
    this.customerName = name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getOrderIndex() {
    return this.orderIndex;
  }

  public String getProductName() {
    return this.productName;
  }

  public String getCustomerName() {
    return this.customerName;
  }

  public String getAddress() {
    return this.address;
  }

  public double getPrice() {
    return this.price;
  }

}
