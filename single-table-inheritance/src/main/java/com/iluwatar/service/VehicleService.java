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

import com.iluwatar.entity.Vehicle;
import com.iluwatar.repository.VehicleRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * A service class that is used to provide the business logic
 * for the Vehicle class and connect to the database to
 * perform the CRUD operations on the root Vehicle class.
 *
 * @see Vehicle
 */
@Service
@AllArgsConstructor
public class VehicleService {

  private final VehicleRepository vehicleRepository;

  /**
   * A method to save all the vehicles to the database.
   *
   * @param vehicle Vehicle bbject
   * @see Vehicle
   */
  public Vehicle saveVehicle(Vehicle vehicle) {
    return vehicleRepository.save(vehicle);
  }

  /**
   * A method to get a specific vehicle from vehicle id.
   *
   * @param vehicleId Vehicle Id
   * @see Vehicle
   */
  public Vehicle getVehicle(int vehicleId) {
    return vehicleRepository.findById(vehicleId).orElse(null);
  }

  /**
   * A method to get all the vehicles saved in the database.
   *
   * @see Vehicle
   */
  public List<Vehicle> getAllVehicles() {
    return vehicleRepository.findAll();
  }

  /**
   * A method to update a vehicle in the database.
   *
   * @param vehicle Vehicle object
   * @see Vehicle
   */
  public Vehicle updateVehicle(Vehicle vehicle) {
    return vehicleRepository.save(vehicle);
  }

  /**
   * A method to save all the vehicles to the database.
   *
   * @param vehicle Vehicle object
   * @see Vehicle
   */
  public void deleteVehicle(Vehicle vehicle) {
    vehicleRepository.delete(vehicle);
  }

}
