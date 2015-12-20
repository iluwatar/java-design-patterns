package com.iluwatar.leaderfollower;

/**
 * A unit of work to be processed by the Workers. Implements Handle.
 * 
 * @author amit
 *
 */
public class Work implements Handle {
  public final int distance;

  public Work(int distance) {
    this.distance = distance;
  }

  @Override
  public int getPayLoad() {
    return distance;
  }


}
