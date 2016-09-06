package com.iluwatar.proxy.example2;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class AnnouncerTest {
  
  private EndPoint endPoint = mock(EndPoint.class);
  private HealthMonitor monitor1 = mock(HealthMonitor.class);
  private HealthMonitor monitor2 = mock(HealthMonitor.class);
  private Announcer<HealthMonitor> announcer = Announcer.to(HealthMonitor.class);
  
  @Before
  public void setUp() {
    announcer.registerListener(monitor1);
    announcer.registerListener(monitor2);
  }
  
  @Test
  public void announcesEventToRegisteredListenersInOrderOfAddition() {
    InOrder order = inOrder(monitor1, monitor2);
    
    announcer.announce().disconnected(endPoint);
    
    order.verify(monitor1).disconnected(same(endPoint));
    order.verify(monitor2).disconnected(same(endPoint));
  }
  
  @Test
  public void doesNotNotifyRemovedListeners() {
    announcer.removeListener(monitor1);
    
    announcer.announce().disconnected(endPoint);
    
    verify(monitor1, never()).disconnected(any(EndPoint.class));
    verify(monitor2).disconnected(same(endPoint));
  }
}
