package com.iluwater.datatransferhash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

  /**
  * Guarantee the execution of the main method in {@link DataTransferHashApp#main(String[])} would not
  * throw exception.
  */

  @Test
  void shouldExecuteApplicationWithoutException() {
    assertDoesNotThrow(() -> DataTransferHashApp.main(new String[]{}));
  }

  /**
  * Add a positive test to check whether the presentation tier could receive data as expected.
  */
  @Test
  void testReceiveDataFromBusiness() {
    var hash= new DataTransferHashObject();
    var business=new Business();
    var presentation=new Presentation();
    business.createHash("Alice", "88887777", hash);
    assertEquals(presentation.getData("Alice", hash),"88887777","test receive data from business succeed");

  }

  /**
  * Add a positive test to check whether the business object could receive data as expected.
  */
  @Test
  void testReceiveDataFromPresentation(){
    var hash= new DataTransferHashObject();
    var business=new Business();
    var presentation=new Presentation();
    presentation.createHash("Trump", "33521179", hash);
    assertEquals(business.getData("Trump", hash),"33521179","test receive data from presentation succeed");
  }

  /**
  * Add a negative test to demonstrate the presentation tier would not receive data if its key is wrong
  */
  @Test
  void testIllegalRequest() {
    var hash = new DataTransferHashObject();
    var business = new Business();
    var presentation = new Presentation();
    business.createHash("Bob", "88887777", hash);
    assertNull(presentation.getData("Alice", hash),"test illegal request succeed");
  }
}