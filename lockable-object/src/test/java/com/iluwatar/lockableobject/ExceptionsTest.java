package com.iluwatar.lockableobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionsTest {

  private String msg = "test";

  @Test
  void testException(){
    Exception e;
    try{
      throw new LockingException(msg);
    }
    catch(LockingException ex){
      e = ex;
    }
    Assertions.assertEquals(msg, e.getMessage());
  }
}
