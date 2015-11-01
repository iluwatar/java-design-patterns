package com.iluwatar.leaderfollower;

/**
 * Represents a Unit of Work which workers can process. In this example only the {@link Work}
 * class implements this
 * @author amit
 *
 */
public interface Handle {
  public int getPayLoad();
}
