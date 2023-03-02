package crtp;

public sealed abstract class Spaceship permits WarSpaceship, CargoSpaceship {

  private final String name;
  private final int fuelCapacity;
  private final Speed speed;
  private final Size size;

  protected Spaceship(Builder<? extends Builder<?>> builder) {
    this.name = builder.name;
    this.fuelCapacity = builder.fuelCapacity;
    this.speed = builder.speed;
    this.size = builder.size;
  }

  public String getName() {
    return name;
  }

  public int getFuelCapacity() {
    return fuelCapacity;
  }

  public Speed getSpeed() {
    return speed;
  }

  public Size getSize() {
    return size;
  }

  static abstract sealed class Builder<T extends Builder<T>> permits WarSpaceship.Builder,
      CargoSpaceship.Builder {

    private final String name;
    private final int fuelCapacity;
    private Speed speed;
    private Size size;

    public Builder(String name, int fuelCapacity) {
      if (name == null) {
        throw new IllegalArgumentException("Name can't be null");
      }
      if (fuelCapacity <= 0) {
        throw new IllegalArgumentException("Fuel capacity can't be equal or less than zero");
      }
      this.name = name;
      this.fuelCapacity = fuelCapacity;
    }

    //    Builder withSize(Speed speed) {
    T withSpeed(Speed speed) {
      this.speed = speed;
//      return this;
      return self();
    }

    //    Builder withSize(Size size) {
    T withSize(Size size) {
      this.size = size;
//      return this;
      return self();
    }

    protected abstract T self();

//    Spaceship build() {
//      return new Spaceship(this);
//    }
  }


}
