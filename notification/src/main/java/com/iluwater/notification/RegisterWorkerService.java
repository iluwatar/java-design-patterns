package com.iluwater.notification;

/**
 *
 */
public class RegisterWorkerService {
  /**
   *
   * @param claim
   */
  public void registerWorker (RegisterWorkerDTO claim) {
    RegisterWorker cmd = new RegisterWorker(claim);
    cmd.run();
  }
}
