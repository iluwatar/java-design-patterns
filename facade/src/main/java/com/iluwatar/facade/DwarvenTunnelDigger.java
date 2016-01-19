package com.iluwatar.facade;

/**
 * 
 * DwarvenTunnelDigger is one of the goldmine subsystems.
 *
 */
public class DwarvenTunnelDigger extends DwarvenMineWorker {

  @Override
  public void work() {
    System.out.println(name() + " creates another promising tunnel.");
  }

  @Override
  public String name() {
    return "Dwarven tunnel digger";
  }
}
