package crtp;

public final class WarSpaceship extends Spaceship {

  private final Weapon weapon;

  private WarSpaceship(Builder builder) {
    super(builder);
    this.weapon = builder.weapon;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  static final class Builder extends Spaceship.Builder<Builder> {

    private Weapon weapon;

    public Builder(String name, int fuelCapacity) {
      super(name, fuelCapacity);
    }

    @Override
    protected Builder self() {
      return this;
    }

    Builder withWeapon(Weapon weapon) {
      this.weapon = weapon;
      return this;
    }

    WarSpaceship build() {
      return new WarSpaceship(this);
    }
  }

}
