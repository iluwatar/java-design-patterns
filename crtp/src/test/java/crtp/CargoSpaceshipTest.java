package crtp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CargoSpaceshipTest {

  @Test
  void testMissingName() {
    assertThrows(IllegalArgumentException.class, () -> new CargoSpaceship.Builder(null, 100));
  }

  @Test
  void testNegativeOrNullFuelCapacity() {
    assertThrows(IllegalArgumentException.class,
        () -> new CargoSpaceship.Builder("Planet Express", -1));
    assertThrows(IllegalArgumentException.class,
        () -> new CargoSpaceship.Builder("Planet Express", 0));
  }

  @Test
  void testBuildCargoShip() {
    final var ship = new CargoSpaceship.Builder("Planet Express", 200)
        .withSpeed(Speed.QUARTER_C)
        .withCargo(Cargo.FOOD)
        .withSize(Size.MEDIUM)
        .build();

    assertNotNull(ship);
    assertNotNull(ship.toString());
    assertEquals(Speed.QUARTER_C, ship.getSpeed());
    assertEquals(Cargo.FOOD, ship.getCargo());
    assertEquals(Size.MEDIUM, ship.getSize());
  }


}