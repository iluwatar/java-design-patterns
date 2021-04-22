package com.iluwatar.clientserver;

import ch.qos.logback.classic.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    Socket socket = null;
    InputStream is = null;
    ByteArrayOutputStream baos = null;
    try{
      /**
       * need to specify a server port
       */
      serverSocket = new ServerSocket(12345);
      /**
       * server waiting for connection
       */
      socket = serverSocket.accept();
      /**
       * read the input from client
       */
      is = socket.getInputStream();
      baos = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len;
      while ((len=is.read(buffer))!=-1){
        baos.write(buffer,0,len);
      }
      System.out.println(baos.toString());
    }
    catch (IOException e){
      e.printStackTrace();
    }
    /**
     * close all the resources
     */
    finally {
      if(is!=null){
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if(socket!=null){
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if(is!=null){
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if(serverSocket!=null){
        try {
          serverSocket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
