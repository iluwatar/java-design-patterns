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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iluwatar.data.transfer.object;

import org.junit.Test;

/**
 * Printing all orders
 */
public class DtoTest {
  
  /*
  * Printing all orders
  */
  @Test
  public void testAllOrders() {
    OrderBo bo = new OrderBo();
    OrderDto dto0 = bo.getOrderDetails(0);
    OrderDto dto1 = bo.getOrderDetails(1);
    OrderDto dto2 = bo.getOrderDetails(2);
    System.out.println("\n\n-------------Order 0-----------------");
    System.out.println(dto0.toString());
    System.out.println("\n\n-------------Order 1-----------------");
    System.out.println(dto1.toString());
    System.out.println("\n\n-------------Order 2-----------------");
    System.out.println(dto2.toString());
  }
  
}
