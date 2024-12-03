package com.iluwatar.monolithic.repository;

import com.iluwatar.monolithic.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders, Long> {
}
