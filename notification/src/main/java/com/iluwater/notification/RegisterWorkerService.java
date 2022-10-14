package com.iluwater.notification;

/**
 *
 */
public class RegisterWorkerService {
  /**
   *
   * @param claim
   */
  public void registerWorker (RegisterWorkerDto claim) {
    RegisterWorker cmd = new RegisterWorker(claim);
    cmd.run();
  }
}
