package com.iluwater.fieild;

public class ClientTestApp {
  public static void main(String... args) {
    ClientTest c=new ClientTest();
    c.checkEmailNotNull();
    c.checkEmails();
    c.checkIdNotNull();
    c.checkTwoIdsNotEqual();
    System.out.println("End of testing");
  }
}
