package crtp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class WarSpaceshipTest {

  @Test
  void testMissingName() {
    assertThrows(IllegalArgumentException.class, () -> new WarSpaceship.Builder(null, 100));
  }

  @Test
  void testNegativeOrNullFuelCapacity() {
    assertThrows(IllegalArgumentException.class, () -> new WarSpaceship.Builder("Gravity", -1));
    assertThrows(IllegalArgumentException.class, () -> new WarSpaceship.Builder("Gravity", 0));
  }

  @Test
  void testBuildCargoShip() {
    final var ship = new WarSpaceship.Builder("Gravity", 500)
        .withSpeed(Speed.HALF_C)
        .withWeapon(Weapon.PLASMA_CANNON)
        .withSize(Size.LARGE)
        .build();

    assertNotNull(ship);
    assertNotNull(ship.toString());
    assertEquals(Speed.HALF_C, ship.getSpeed());
    assertEquals(Weapon.PLASMA_CANNON, ship.getWeapon());
    assertEquals(Size.LARGE, ship.getSize());
  }


}