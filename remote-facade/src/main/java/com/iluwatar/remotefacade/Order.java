/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.remotefacade;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * The Order class contains the fine-grained methods for placing and retrieving an order.
 */
public class Order {
  private Socket socket;

  /**
   * Constructor.
   */
  public Order() {
    try {
      InetAddress host = InetAddress.getLocalHost();
      this.socket = new Socket(host.getHostAddress(), 8000);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }

  /**
   * Order a drink.
   * 
   * @param drink order drink specified by {@code drink}
   */
  public void setDrink(String drink) {
    try {
      ObjectOutputStream outStream = new ObjectOutputStream(this.socket.getOutputStream());
      outStream.writeObject(drink);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Order an entree.
   * 
   * @param entree order entree specified by {@code entree}
   */
  public void setEntree(String entree) {
    try {
      ObjectOutputStream outStream = new ObjectOutputStream(this.socket.getOutputStream());
      outStream.writeObject(entree);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Order a side.
   * 
   * @param side order side specified by {@code side}
   */
  public void setSide(String side) {
    try {
      ObjectOutputStream outStream = new ObjectOutputStream(this.socket.getOutputStream());
      outStream.writeObject(side);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieve a drink order.
   * 
   * @return the order
   */
  public String getDrink() {
    try {
      ObjectInputStream inStream = new ObjectInputStream(this.socket.getInputStream());
      String order = (String) inStream.readObject();
      System.out.println("Retrieved order: \n" + order);
      return order;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieve an entree order.
   * 
   * @return the order
   */
  public String getEntree() {
    try {
      ObjectInputStream inStream = new ObjectInputStream(this.socket.getInputStream());
      String order = (String) inStream.readObject();
      System.out.println("Retrieved order: \n" + order);
      return order;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieve a side order.
   * 
   * @return the order
   */
  public String getSide() {
    try {
      ObjectInputStream inStream = new ObjectInputStream(this.socket.getInputStream());
      String order = (String) inStream.readObject();
      System.out.println("Retrieved order: \n" + order);
      return order;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }
}
