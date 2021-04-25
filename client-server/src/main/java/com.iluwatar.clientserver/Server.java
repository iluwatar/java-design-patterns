package com.iluwatar.clientserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * Server side.
 */
@Slf4j
public final class Server {

  /**
   *  Logger.
   */
  private static final Logger LOGG = null;

  private Server(){}

  /**
   * class Server.
   *
   * @param args no args
   */

  public static void main(final String[] args) {
    ServerSocket serverSocket = null;
    Socket socket = null;
    InputStream inputStream = null;
    ByteArrayOutputStream baos;
    try {
      serverSocket = new ServerSocket(12_345);
      socket = serverSocket.accept();
      inputStream = socket.getInputStream();
      baos = new ByteArrayOutputStream();
      final byte[] buffer = new byte[1024];
      int len = inputStream.read(buffer);
      while (len != -1) {
        len = inputStream.read(buffer);
        baos.write(buffer, 0, len);
      }
      LOGGER.info(baos.toString("UTF-8"));
    } catch (IOException e) {
      LOGG.error("Ops!", e);
    } finally {
      try {
        inputStream.close();
        socket.close();
        serverSocket.close();
      } catch (IOException e) {
        LOGG.error("Ops!", e);
      }
    }
  }
}