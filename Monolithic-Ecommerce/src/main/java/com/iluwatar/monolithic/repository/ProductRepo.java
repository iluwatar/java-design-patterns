package com.iluwatar.monolithic.repository;

import com.iluwatar.monolithic.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Products, Long> {
}
