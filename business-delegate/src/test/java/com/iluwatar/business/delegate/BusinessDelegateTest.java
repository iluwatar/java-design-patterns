package com.iluwatar.business.delegate;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

/**
 * The Business Delegate pattern adds an abstraction layer between the presentation and business
 * tiers. By using the pattern we gain loose coupling between the tiers. The Business Delegate
 * encapsulates knowledge about how to locate, connect to, and interact with the business objects
 * that make up the application.
 * 
 * <p>Some of the services the Business Delegate uses are instantiated directly, and some can be
 * retrieved through service lookups. The Business Delegate itself may contain business logic too
 * potentially tying together multiple service calls, exception handling, retrying etc.
 */
public class BusinessDelegateTest {

  private EjbService ejbService;

  private JmsService jmsService;

  private BusinessLookup businessLookup;

  private BusinessDelegate businessDelegate;

  /**
   * This method sets up the instance variables of this test class. It is executed before the
   * execution of every test.
   */
  @Before
  public void setup() {
    ejbService = spy(new EjbService());
    jmsService = spy(new JmsService());

    businessLookup = spy(new BusinessLookup());
    businessLookup.setEjbService(ejbService);
    businessLookup.setJmsService(jmsService);

    businessDelegate = spy(new BusinessDelegate());
    businessDelegate.setLookupService(businessLookup);
  }

  /**
   * In this example the client ({@link Client}) utilizes a business delegate (
   * {@link BusinessDelegate}) to execute a task. The Business Delegate then selects the appropriate
   * service and makes the service call.
   */
  @Test
  public void testBusinessDelegate() {

    // setup a client object
    Client client = new Client(businessDelegate);

    // set the service type
    businessDelegate.setServiceType(ServiceType.EJB);

    // action
    client.doTask();

    // verifying that the businessDelegate was used by client during doTask() method.
    verify(businessDelegate).doTask();
    verify(ejbService).doProcessing();

    // set the service type
    businessDelegate.setServiceType(ServiceType.JMS);

    // action
    client.doTask();

    // verifying that the businessDelegate was used by client during doTask() method.
    verify(businessDelegate, times(2)).doTask();
    verify(jmsService).doProcessing();
  }
}
