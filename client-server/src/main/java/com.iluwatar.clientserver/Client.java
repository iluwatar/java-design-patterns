package com.iluwatar.clientserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Client Side.
 */

public class Client {
  /**
   * class Client.
   *
   * @param args no args
   */
  public static void main(String[] args) {
    Socket socket = null;
    OutputStream os = null;
    try {
      InetAddress serverIp = InetAddress.getByName("127.0.0.1");
      int port = 12345;
      socket = new Socket(serverIp, port);
      os = socket.getOutputStream();
      os.write("Hello, java design pattern!".getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (os != null) {
        try {
          os.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
