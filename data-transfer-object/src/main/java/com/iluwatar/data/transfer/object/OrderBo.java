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

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Client requests the data transfer object through Business object.
 * Business object fills the data transfer object.
 */
public class OrderBo {

  JSONArray orders;
  JSONArray customers;
  JSONArray products;

  public OrderBo() {
    //Read data
    readData();
  }

  /**
   * returns order details of given order index(order no.)
   * as OrderDto object.
   * 
   * @param orderIndex : Index or id of the order.
   * @return order details as orderDto object.
   */
  public OrderDto getOrderDetails(int orderIndex) {
    OrderDto retval = new OrderDto();
    
    //getting order object
    JSONObject order = (JSONObject) orders.get(orderIndex);
    
    //getting corresponding customer object
    int customerIndex = ((Long) order.get("customerIndex")).intValue();
    JSONObject customer = (JSONObject) customers.get(customerIndex);
    
    //getting corresponding product object
    int productIndex = ((Long) order.get("productIndex")).intValue();
    JSONObject product = (JSONObject) products.get(productIndex);

    //filling the DTO
    retval.setOrderIndex(orderIndex);
    retval.setCustomerName((String) customer.get("name"));
    retval.setAddress((String) customer.get("address"));
    retval.setProductName((String) product.get("name"));
    retval.setPrice((double) product.get("price"));
    
    return retval;
  }

  /**
   * Reads data from json files into class variables
   */
  private void readData() {
    JSONParser parser = new JSONParser();
    try {
      this.orders = (JSONArray) parser.parse(new FileReader("data/orders.json"));
      this.customers = (JSONArray) parser.parse(new FileReader("data/customers.json"));
      this.products = (JSONArray) parser.parse(new FileReader("data/products.json"));
    } catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(-1);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(-1);
    }
  }

}
