package com.iluwatar.business.delegate;

/**
 * Class for performing service lookups.
 */
public class BusinessLookup {

  private EjbService ejbService;

  private JmsService jmsService;

  /**
   * @param serviceType Type of service instance to be returned.
   * @return Service instance.
   */
  public BusinessService getBusinessService(ServiceType serviceType) {
    if (serviceType.equals(ServiceType.EJB)) {
      return ejbService;
    } else {
      return jmsService;
    }
  }

  public void setJmsService(JmsService jmsService) {
    this.jmsService = jmsService;
  }

  public void setEjbService(EjbService ejbService) {
    this.ejbService = ejbService;
  }
}
