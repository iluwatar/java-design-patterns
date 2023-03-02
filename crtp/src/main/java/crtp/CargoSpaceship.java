package crtp;

public final class CargoSpaceship extends Spaceship {

  private final Cargo cargo;

  private CargoSpaceship(Builder builder) {
    super(builder);
    this.cargo = builder.cargo;
  }

  public Cargo getCargo() {
    return cargo;
  }

  static final class Builder extends Spaceship.Builder<Builder> {

    private Cargo cargo;

    public Builder(String name, int fuelCapacity) {
      super(name, fuelCapacity);
    }

    @Override
    protected Builder self() {
      return this;
    }

    Builder withCargo(Cargo cargo) {
      this.cargo = cargo;
      return this;
    }

    CargoSpaceship build() {
      return new CargoSpaceship(this);
    }
  }

}
