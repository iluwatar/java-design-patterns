/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package crtp.space;

/**
 * Spaceship class.
 */
public abstract sealed class Spaceship permits WarSpaceship, CargoSpaceship {

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

  /**
   * Builder class.
   *
   * @param <T> Builder derived class that uses itself as type parameter.
   */
  abstract static sealed class Builder<T extends Builder<T>> permits WarSpaceship.Builder,
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

    T withSpeed(Speed speed) {
      this.speed = speed;
      return self();
    }

    T withSize(Size size) {
      this.size = size;
      return self();
    }

    protected abstract T self();

  }


}
