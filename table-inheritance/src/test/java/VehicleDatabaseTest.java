import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iluwatar.table.inheritance.Car;
import com.iluwatar.table.inheritance.Truck;
import com.iluwatar.table.inheritance.Vehicle;
import com.iluwatar.table.inheritance.VehicleDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link VehicleDatabase} class.
 * Tests saving, retrieving, and printing vehicles of different types.
 */
class VehicleDatabaseTest {

  private VehicleDatabase vehicleDatabase;

  /**
   * Sets up a new instance of {@link VehicleDatabase} before each test.
   */
  @BeforeEach
  public void setUp() {
    vehicleDatabase = new VehicleDatabase();
  }

  /**
   * Tests saving a {@link Car} to the database and retrieving it.
   */
  @Test
  void testSaveAndRetrieveCar() {
    Car car = new Car(2020, "Toyota", "Corolla", 4, 1);
    vehicleDatabase.saveVehicle(car);

    Vehicle retrievedVehicle = vehicleDatabase.getVehicle(car.getId());
    assertNotNull(retrievedVehicle);
    assertEquals(car.getId(), retrievedVehicle.getId());
    assertEquals(car.getMake(), retrievedVehicle.getMake());
    assertEquals(car.getModel(), retrievedVehicle.getModel());
    assertEquals(car.getYear(), retrievedVehicle.getYear());

    Car retrievedCar = vehicleDatabase.getCar(car.getId());
    assertNotNull(retrievedCar);
    assertEquals(car.getNumDoors(), retrievedCar.getNumDoors());
  }

  /**
   * Tests saving a {@link Truck} to the database and retrieving it.
   */
  @Test
  void testSaveAndRetrieveTruck() {
    Truck truck = new Truck(2018, "Ford", "F-150", 60, 2);
    vehicleDatabase.saveVehicle(truck);

    Vehicle retrievedVehicle = vehicleDatabase.getVehicle(truck.getId());
    assertNotNull(retrievedVehicle);
    assertEquals(truck.getId(), retrievedVehicle.getId());
    assertEquals(truck.getMake(), retrievedVehicle.getMake());
    assertEquals(truck.getModel(), retrievedVehicle.getModel());
    assertEquals(truck.getYear(), retrievedVehicle.getYear());

    Truck retrievedTruck = vehicleDatabase.getTruck(truck.getId());
    assertNotNull(retrievedTruck);
    assertEquals(truck.getLoadCapacity(), retrievedTruck.getLoadCapacity());
  }

  /**
   * Tests saving multiple vehicles to the database and printing them.
   */
  @Test
  void testPrintAllVehicles() {
    Car car = new Car(2020, "Toyota", "Corolla", 4, 1);
    Truck truck = new Truck(2018, "Ford", "F-150", 60, 2);
    vehicleDatabase.saveVehicle(car);
    vehicleDatabase.saveVehicle(truck);

    vehicleDatabase.printAllVehicles();

    Vehicle retrievedCar = vehicleDatabase.getVehicle(car.getId());
    Vehicle retrievedTruck = vehicleDatabase.getVehicle(truck.getId());

    assertNotNull(retrievedCar);
    assertNotNull(retrievedTruck);
  }

  /**
   * Tests the constructor of {@link Car} with valid values.
   */
  @Test
  void testCarConstructor() {
    Car car = new Car(2020, "Toyota", "Corolla", 4, 1);
    assertEquals(2020, car.getYear());
    assertEquals("Toyota", car.getMake());
    assertEquals("Corolla", car.getModel());
    assertEquals(4, car.getNumDoors());
    assertEquals(1, car.getId()); // Assuming the ID is auto-generated in the constructor
  }

  /**
   * Tests the constructor of {@link Car} with invalid number of doors (negative value).
   */
  @Test
  void testCarConstructorWithInvalidNumDoors() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Car(2020, "Toyota", "Corolla", -4, 1);
    });
    assertEquals("Number of doors must be positive.", exception.getMessage());
  }

  /**
   * Tests the constructor of {@link Car} with zero doors.
   */
  @Test
  void testCarConstructorWithZeroDoors() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Car(2020, "Toyota", "Corolla", 0, 1);
    });
    assertEquals("Number of doors must be positive.", exception.getMessage());
  }

  /**
   * Tests the constructor of {@link Truck} with invalid load capacity (negative value).
   */
  @Test
  void testTruckConstructorWithInvalidLoadCapacity() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Truck(2018, "Ford", "F-150", -60, 2);
    });
    assertEquals("Load capacity must be positive.", exception.getMessage());
  }

  /**
   * Tests the constructor of {@link Truck} with zero load capacity.
   */
  @Test
  void testTruckConstructorWithZeroLoadCapacity() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Truck(2018, "Ford", "F-150", 0, 2);
    });
    assertEquals("Load capacity must be positive.", exception.getMessage());
  }

  /**
   * Tests setting invalid number of doors in {@link Car} using setter (negative value).
   */
  @Test
  void testSetInvalidNumDoors() {
    Car car = new Car(2020, "Toyota", "Corolla", 4, 1);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      car.setNumDoors(-2);
    });
    assertEquals("Number of doors must be positive.", exception.getMessage());
  }

  /**
   * Tests setting invalid load capacity in {@link Truck} using setter (negative value).
   */
  @Test
  void testSetInvalidLoadCapacity() {
    Truck truck = new Truck(2018, "Ford", "F-150", 60, 2);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      truck.setLoadCapacity(-10);
    });
    assertEquals("Load capacity must be positive.", exception.getMessage());
  }
}


