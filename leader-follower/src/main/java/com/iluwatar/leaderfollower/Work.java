package com.iluwatar.leaderfollower;

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
