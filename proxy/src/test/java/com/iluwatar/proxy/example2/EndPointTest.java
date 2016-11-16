package com.iluwatar.proxy.example2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class EndPointTest {
  
  private EndPoint endPoint;
  private HealthMonitor monitor;
  private HealthMonitor monitor2;
  
  @Before
  public void setUp() {
    endPoint = new EndPoint("TCP Endpoint");
    monitor = Mockito.mock(HealthMonitor.class);
    monitor2 = Mockito.mock(HealthMonitor.class);
    endPoint.addMonitor(monitor);
    endPoint.addMonitor(monitor2);
  }
  
  @Test
  public void allHealthMonitorsAreNotifiedIfEndpointIsDisconnected() {
    endPoint.disconnect();
    
    verify(monitor).disconnected(endPoint);
    verify(monitor2).disconnected(endPoint);
  }
  
  @Test
  public void allHealthMonitorsAreNotifiedIfEndpointIsConnected() {
    endPoint.connected();
    
    verify(monitor).connected(endPoint);
    verify(monitor2).connected(endPoint);
  }
}
