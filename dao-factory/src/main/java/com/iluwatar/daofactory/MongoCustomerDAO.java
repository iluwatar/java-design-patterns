package com.iluwatar.daofactory;

import org.dizitart.no2.collection.Document;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:26
 * Filename  : NitriteCustomerDAO
 */
public class MongoCustomerDAO implements CustomerDAO {
  @Override
  public void save(Customer customer) {

  }

  @Override
  public void update(Customer customer) {

  }

  @Override
  public void delete(Long id) {

  }

  @Override
  public Optional<Customer> findById(Long id) {
    return Optional.empty();
  }

//  @Override
//  public List<Customer> findAll() {
//    return List.of();
//  }
//
//  @Override
//  public Optional<Customer> findById(Long id) {
//    return Optional.empty();
//  }
}
