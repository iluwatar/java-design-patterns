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
package com.iluwatar.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.iluwatar.entity.Car;
import com.iluwatar.entity.Freighter;
import com.iluwatar.entity.Train;
import com.iluwatar.entity.Truck;
import com.iluwatar.entity.Vehicle;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
* Tests for {@link VehicleService} exercising CRUD operations and verifying that all concrete
* Vehicle subtypes are persisted and retrieved correctly via the single-table inheritance strategy.
*/
@SpringBootTest
class VehicleServiceTest {


 @Autowired private VehicleService vehicleService;


 private Vehicle savedCar;
 private Vehicle savedTruck;
 private Vehicle savedTrain;
 private Vehicle savedFreighter;


 @BeforeEach
 void setUp() {
   savedCar = vehicleService.saveVehicle(new Car("Tesla", "Model S", 4, 825));
   savedTruck = vehicleService.saveVehicle(new Truck("Ford", "F-150", 3325, 14000));
   savedTrain = vehicleService.saveVehicle(new Train("Siemens", "Velaro", 600, 8));
   savedFreighter = vehicleService.saveVehicle(new Freighter("Boeing", "747F", 100000, 8130));
 }


 @AfterEach
 void tearDown() {
   vehicleService.deleteVehicle(savedCar);
   vehicleService.deleteVehicle(savedTruck);
   vehicleService.deleteVehicle(savedTrain);
   vehicleService.deleteVehicle(savedFreighter);
 }


 @Test
 void saveVehicle_shouldPersistCarWithCorrectType() {
   assertTrue(savedCar.getVehicleId() > 0);
   assertInstanceOf(Car.class, savedCar);
   assertEquals("Tesla", savedCar.getManufacturer());
   assertEquals("Model S", savedCar.getModel());
   assertEquals(825, ((Car) savedCar).getEngineCapacity());
 }


 @Test
 void saveVehicle_shouldPersistTruckWithCorrectType() {
   assertTrue(savedTruck.getVehicleId() > 0);
   assertInstanceOf(Truck.class, savedTruck);
   assertEquals(14000, ((Truck) savedTruck).getTowingCapacity());
 }


 @Test
 void saveVehicle_shouldPersistTrainWithCorrectType() {
   assertTrue(savedTrain.getVehicleId() > 0);
   assertInstanceOf(Train.class, savedTrain);
   assertEquals(8, ((Train) savedTrain).getNoOfCarriages());
 }


 @Test
 void saveVehicle_shouldPersistFreighterWithCorrectType() {
   assertTrue(savedFreighter.getVehicleId() > 0);
   assertInstanceOf(Freighter.class, savedFreighter);
   assertEquals(8130.0, ((Freighter) savedFreighter).getFlightLength());
 }


 @Test
 void getVehicle_shouldReturnCorrectVehicleById() {
   Vehicle fetched = vehicleService.getVehicle(savedCar.getVehicleId());
   assertTrue(fetched != null);
   assertInstanceOf(Car.class, fetched);
   assertEquals(savedCar.getVehicleId(), fetched.getVehicleId());
 }


 @Test
 void getVehicle_shouldReturnNullForNonexistentId() {
   Vehicle result = vehicleService.getVehicle(Integer.MAX_VALUE);
   assertNull(result);
 }


 @Test
 void getAllVehicles_shouldReturnAllSavedVehicles() {
   List<Vehicle> all = vehicleService.getAllVehicles();
   assertTrue(all != null);
   // At minimum the 4 vehicles saved in @BeforeEach must be present
   assertTrue_containsId(all, savedCar.getVehicleId());
   assertTrue_containsId(all, savedTruck.getVehicleId());
   assertTrue_containsId(all, savedTrain.getVehicleId());
   assertTrue_containsId(all, savedFreighter.getVehicleId());
 }


 @Test
 void updateVehicle_shouldPersistChanges() {
   Car car = (Car) savedCar;
   car.setModel("Model X");
   vehicleService.updateVehicle(car);


   Vehicle updated = vehicleService.getVehicle(car.getVehicleId());
   assertEquals("Model X", updated.getModel());
 }


 @Test
 void deleteVehicle_shouldRemoveFromRepository() {
   // Save a temporary vehicle and delete it
   Vehicle tmp = vehicleService.saveVehicle(new Car("BMW", "M3", 4, 3000));
   int tmpId = tmp.getVehicleId();


   vehicleService.deleteVehicle(tmp);


   assertNull(vehicleService.getVehicle(tmpId));
 }


 // Helper: assert that a specific vehicle id exists in the list
 private void assertTrue_containsId(List<Vehicle> vehicles, int id) {
   boolean found = vehicles.stream().anyMatch(v -> v.getVehicleId() == id);
   if (!found) {
     throw new AssertionError("Expected vehicle with id=" + id + " in list but was not found.");
   }
 }
}

