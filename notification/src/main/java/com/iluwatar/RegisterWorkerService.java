package com.iluwatar;

/**
 * Service used to register a worker.
 * This represents the basic framework of a service layer which can be built upon.
 */
public class RegisterWorkerService {
  /**
   * Creates and runs a command object to do the work needed,
   * in this case, register a worker in the system.
   *
   * @param registration worker to be registered if possible
   */
  public void registerWorker(RegisterWorkerDto registration) {
    var cmd = new RegisterWorker(registration);
    cmd.run();
  }
}
