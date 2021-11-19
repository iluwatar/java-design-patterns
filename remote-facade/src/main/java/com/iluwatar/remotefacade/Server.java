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
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Server class acts as the remote interface for the client to interact with.
 * The Order and OrderFacade classes send orders to the Server and the Server sends
 * the orders back.
 */
public class Server {
  private static ServerSocket server;

  /**
   * Server main entry point.
   */
  public static void main(String[] args) {

    try {
      server = new ServerSocket(8000);
    } catch (IOException e) {
      e.printStackTrace();
    }

    while (true) {
      try {
        Socket socket = server.accept();

        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        String order = (String) inStream.readObject();
        System.out.println("Received Order:\n" + order);
  
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject(order);
  
        inStream.close();
        outStream.close();
        socket.close();
  
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}