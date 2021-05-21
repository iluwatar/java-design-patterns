package com.iluwatar.clientserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import org.slf4j.Logger;



/**
 * Client Side.
 */

public final class Client {
  /**
   *  Logger.
   */
  private static final Logger LOGG = null;
  /**
   * Port number.
   */
  private static int port = 12_345;

  private Client(){}

  /**
   * class Client.
   *
   * @param args no args
   */


  public static void main(final String[] args) {
    Socket socket = null; //NOPMD
    OutputStream outputStream = null; //NOPMD
    try {
      final var serverIp = InetAddress.getByName("localhost");
      socket = new Socket(serverIp, port);
      outputStream = socket.getOutputStream();
      outputStream.write("Hello, java design pattern!".getBytes("UTF-8")); //NOPMD//NOPMD
    } catch (IOException e) {
      LOGG.error("Ops!", e);
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace(); //NOPMD
          //  log.error("Ops!", e);
        }
      }
      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e) {
          // log.error("Ops!", e);
          e.printStackTrace(); //NOPMD
        }
      }
    }
  }
}