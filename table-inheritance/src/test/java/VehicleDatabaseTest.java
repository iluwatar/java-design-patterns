import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iluwatar.Vehicle_Database;
import com.iluwatar.Truck;
import com.iluwatar.Car;
import com.iluwatar.Vehicle;


public class VehicleDatabaseTest {

  private Vehicle_Database vehicleDatabase;

  @BeforeEach
  public void setUp() {
    vehicleDatabase = new Vehicle_Database();
  }

  @Test
  public void testSaveAndRetrieveCar() {
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
    assertEquals(car.GetNumDoors(), retrievedCar.GetNumDoors());
  }

  @Test
  public void testSaveAndRetrieveTruck() {
    Truck truck = new Truck(2018, "Ford", "F-150", 60,2);
    vehicleDatabase.saveVehicle(truck);

    Vehicle retrievedVehicle = vehicleDatabase.getVehicle(truck.getId());
    assertNotNull(retrievedVehicle);
    assertEquals(truck.getId(), retrievedVehicle.getId());
    assertEquals(truck.getMake(), retrievedVehicle.getMake());
    assertEquals(truck.getModel(), retrievedVehicle.getModel());
    assertEquals(truck.getYear(), retrievedVehicle.getYear());

    Truck retrievedTruck = vehicleDatabase.getTruck(truck.getId());
    assertNotNull(retrievedTruck);
    assertEquals(truck.GetLoadCapacity(), retrievedTruck.GetLoadCapacity());
  }

  @Test
  public void testPrintAllVehicles() {
    Car car = new Car(2020, "Toyota", "Corolla", 4, 1);
    Truck truck = new Truck(2018, "Ford", "F-150", 60,2);
    vehicleDatabase.saveVehicle(car);
    vehicleDatabase.saveVehicle(truck);

    vehicleDatabase.printAllVehicles();

    Vehicle retrievedCar = vehicleDatabase.getVehicle(car.getId());
    Vehicle retrievedTruck = vehicleDatabase.getVehicle(truck.getId());

    assertNotNull(retrievedCar);
    assertNotNull(retrievedTruck);
  }
}
