package crtp.space;

public enum Weapon {

  GUNSHIP_AUTOCANNON, LIGHT_PULSE_CANNON, PLASMA_CANNON, RAILGUN_CANNON;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
