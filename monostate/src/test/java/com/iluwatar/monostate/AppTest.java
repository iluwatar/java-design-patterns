package com.iluwatar.monostate;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {

  @Test
  public void testSameStateAmonstAllInstances() {
    LoadBalancer balancer = new LoadBalancer();
    LoadBalancer balancer2 = new LoadBalancer();
    balancer.addServer(new Server("localhost", 8085, 6));
    // Both should have the same number of servers.
    Assert.assertTrue(balancer.getNoOfServers() == balancer2.getNoOfServers());
    // Both Should have the same LastServedId
    Assert.assertTrue(balancer.getLastServedId() == balancer2.getLastServedId());
  }

  @Test
  public void testMain() {
    String[] args = {};
    App.main(args);
    Assert.assertTrue(LoadBalancer.getLastServedId() == 2);
  }

}
