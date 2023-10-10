package com.iluwatar.repository;

import com.iluwatar.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository that is extending the JPA Repository
 * to provide the default Spring DATA JPA methods for the Vehicle class.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

}
