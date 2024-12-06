package com.iluwater.fieild;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
  void checkIdNotNull()
  {
    Client client= new Client("John","jhon9201@gmail.com");
    assertNotNull(client.getId());
  }
  void checkTwoIdsNotEqual()
  {
    Client client= new Client("John","jhon9201@gmail.com");
    Client client2= new Client("Sara","sara4825@gmail.com");
    assertNotEquals(client.getId(),client2.getId());
  }
  void checkEmails()
  {
    Client client= new Client("John","jhon9201@gmail.com");
    Client client2= new Client("Sara","sara4825@gmail.com");
    assertNotEquals(client.getEmail(),client2.getEmail());
  }
}