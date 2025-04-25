package com.iluwatar.daofactory;

import java.util.List;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:16
 * Filename  : CustomerDAO
 */
public interface CustomerDAO<ID> {
  void save(Customer<ID> customer);

  void update(Customer<ID> customer);

  void delete(ID id);

  List<Customer<ID>> findAll();

  Optional<Customer<ID>> findById(ID id);

  void deleteSchema();
}
