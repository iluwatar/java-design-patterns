package com.iluwatar.clientserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server side.
 */
public class Server {

  /**
   * class Server.
   *
   * @param args no args
   */
  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    Socket socket = null;
    InputStream is = null;
    ByteArrayOutputStream baos = null;
    try {
      serverSocket = new ServerSocket(12345);
      socket = serverSocket.accept();
      is = socket.getInputStream();
      baos = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len;
      while ((len = is.read(buffer)) != -1) {
        baos.write(buffer, 0, len);
      }
      System.out.println(baos.toString());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (serverSocket != null) {
        try {
          serverSocket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
