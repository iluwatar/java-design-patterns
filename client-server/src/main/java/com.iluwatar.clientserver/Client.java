package com.iluwatar.clientserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
public class Client {
  public static void main(String[] args) {
    Socket socket = null;
    OutputStream os = null;
    try{
      /**
       * specify the ip address and port number
       */
      InetAddress serverIP = InetAddress.getByName("127.0.0.1");
      int port = 12345;
      /**
       * create a socket connection
       */
      socket = new Socket(serverIP,port);
      /**
       * send message to IO stream
       */
      os = socket.getOutputStream();
      os.write("Hello, java design pattern!".getBytes());
    }
    catch (Exception e){
      e.printStackTrace();
    }finally {
      if(socket!=null){
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if(os!=null){
        try {
          os.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
