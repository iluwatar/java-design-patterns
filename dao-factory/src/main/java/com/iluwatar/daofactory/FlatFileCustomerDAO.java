package com.iluwatar.daofactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:25
 * Filename  : FlatFileCustomerDAO
 */
@Slf4j
public class FlatFileCustomerDAO implements CustomerDAO<Long> {
  Path filePath = Paths.get(System.getProperty("user.home"), "Desktop", "customer.json");
  Gson gson = new GsonBuilder()
      .setPrettyPrinting()
      .serializeNulls()
      .create();
  Type customerListType = new TypeToken<List<Customer<Long>>>() {
  }.getType();

  @Override
  public void save(Customer<Long> customer) {
    List<Customer<Long>> customers = new LinkedList<>();
    if (filePath.toFile().exists() && filePath.toFile().length() > 0) {
      try (Reader reader = new FileReader(filePath.toFile())) {
        customers = gson.fromJson(reader, customerListType);
      } catch (IOException ex) {
        LOGGER.error("Error while reading JSON: {}", ex.getMessage(), ex);
      }
    }
    customers.add(customer);
    try (Writer writer = new FileWriter(filePath.toFile())) {
      gson.toJson(customers, writer);
    } catch (IOException ex) {
      LOGGER.error("Error writing JSON: {}", ex.getMessage(), ex);
    }
  }

  @Override
  public void update(Customer<Long> customer) {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    List<Customer<Long>> customers = new LinkedList<>();
    try (Reader reader = new FileReader(filePath.toFile())) {
      customers = gson.fromJson(reader, customerListType);
    } catch (IOException ex) {
      LOGGER.error("Error while reading JSON: {}", ex.getMessage(), ex);
    }
    customers.stream()
        .filter(c -> c.getId()
            .equals(customer.getId()))
        .findFirst()
        .ifPresentOrElse(
            c -> c.setName(customer.getName()),
            () -> {
              throw new RuntimeException("Customer not found with id: " + customer.getId());
            }
        );
  }

  @Override
  public void delete(Long id) {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    List<Customer<Long>> customers = new LinkedList<>();
    try (Reader reader = new FileReader(filePath.toFile())) {
      customers = gson.fromJson(reader, customerListType);
    } catch (IOException ex) {
      LOGGER.error("Error while reading JSON: {}", ex.getMessage(), ex);
    }
    Customer<Long> customerToRemove = customers.stream().filter(c -> c.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    customers.remove(customerToRemove);
  }

  @Override
  public List<Customer<Long>> findAll() {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    List<Customer<Long>> customers = null;
    try (Reader reader = new FileReader(filePath.toFile())) {
      customers = gson.fromJson(reader, customerListType);

    } catch (IOException ex) {
      LOGGER.error("Error while reading JSON: {}", ex.getMessage(), ex);
    }
    return customers;
  }

  @Override
  public Optional<Customer<Long>> findById(Long id) {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    List<Customer<Long>> customers = null;
    try (Reader reader = new FileReader(filePath.toFile())) {
      customers = gson.fromJson(reader, customerListType);

    } catch (IOException ex) {
      LOGGER.error("Error while reading JSON: {}", ex.getMessage(), ex);
    }
    return customers.stream()
        .filter(c -> c.getId()
            .equals(id))
        .findFirst();
  }

  @Override
  public void deleteSchema() {
    if (filePath.toFile().exists()) {
      filePath.toFile().delete();
    }
  }
}
