package com.iluwatar.servicelocator;

/**
 * This is a single service implementation of a sample service. This is the actual service that will
 * process the request. The reference for this service is to be looked upon in the JNDI server that
 * can be set in the web.xml deployment descriptor
 *
 * @author saifasif
 */
public class ServiceImpl implements Service {

  private final String serviceName;
  private final int id;

  /**
   * Constructor
   */
  public ServiceImpl(String serviceName) {
    // set the service name
    this.serviceName = serviceName;

    // Generate a random id to this service object
    this.id = (int) Math.floor(Math.random() * 1000) + 1;
  }

  @Override
  public String getName() {
    return serviceName;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void execute() {
    System.out.println("Service " + getName() + " is now executing with id " + getId());
  }
}
