package com.iluwatar.leaderfollower;

import org.junit.Assert;
import org.junit.Test;

public class HandleSetTest {

  @Test
  public void testFireEvent() throws InterruptedException {
    HandleSet handleSet = new HandleSet();
    handleSet.fireEvent(new Work(10));
    Assert.assertTrue(handleSet.getQueue().size() == 1);
  }

  @Test
  public void testGetEvent() throws InterruptedException {
    HandleSet handleSet = new HandleSet();
    handleSet.fireEvent(new Work(10));
    Work work = handleSet.getPayLoad();
    Assert.assertTrue(work.distance == 10);
    Assert.assertTrue(handleSet.getQueue().size() == 0);
  }

}
