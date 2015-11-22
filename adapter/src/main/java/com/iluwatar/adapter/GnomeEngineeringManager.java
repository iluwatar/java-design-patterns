package com.iluwatar.adapter;

/**
 * GnomeEngineering manager uses {@link Engineer} to operate devices.
 */
public class GnomeEngineeringManager implements Engineer {

  private Engineer engineer;

  public GnomeEngineeringManager() {

  }

  public GnomeEngineeringManager(Engineer engineer) {
    this.engineer = engineer;
  }

  @Override
  public void operateDevice() {
    engineer.operateDevice();
  }

  public void setEngineer(Engineer engineer) {
    this.engineer = engineer;
  }
}
