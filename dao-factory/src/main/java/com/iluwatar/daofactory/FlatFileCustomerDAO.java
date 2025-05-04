package com.iluwatar.daofactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * A Flat File implementation of {@link CustomerDAO}, which store the customer data in a JSON file
 * at path {@code ~/Desktop/customer.json}.
 */
@Slf4j
@RequiredArgsConstructor
public class FlatFileCustomerDAO implements CustomerDAO<Long> {
  private final Path filePath;
  private final Gson gson;
  Type customerListType = new TypeToken<List<Customer<Long>>>() {}.getType();

  protected Reader createReader(Path filePath) throws IOException {
    return new FileReader(filePath.toFile());
  }

  protected Writer createWriter(Path filePath) throws IOException {
    return new FileWriter(filePath.toFile());
  }

  /** {@inheritDoc} */
  @Override
  public void save(Customer<Long> customer) {
    List<Customer<Long>> customers = new LinkedList<>();
    if (filePath.toFile().exists()) {
      try (Reader reader = createReader(filePath)) {
        customers = gson.fromJson(reader, customerListType);
      } catch (IOException ex) {
        throw new RuntimeException("Failed to read customer data", ex);
      }
    }
    customers.add(customer);
    try (Writer writer = createWriter(filePath)) {
      gson.toJson(customers, writer);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to write customer data", ex);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void update(Customer<Long> customer) {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    List<Customer<Long>> customers;
    try (Reader reader = createReader(filePath)) {
      customers = gson.fromJson(reader, customerListType);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to read customer data", ex);
    }
    customers.stream()
        .filter(c -> c.getId().equals(customer.getId()))
        .findFirst()
        .ifPresentOrElse(
            c -> c.setName(customer.getName()),
            () -> {
              throw new RuntimeException("Customer not found with id: " + customer.getId());
            });
    try (Writer writer = createWriter(filePath)) {
      gson.toJson(customers, writer);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to write customer data", ex);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void delete(Long id) {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    List<Customer<Long>> customers;
    try (Reader reader = createReader(filePath)) {
      customers = gson.fromJson(reader, customerListType);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to read customer data", ex);
    }
    Customer<Long> customerToRemove =
        customers.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    customers.remove(customerToRemove);
    try (Writer writer = createWriter(filePath)) {
      gson.toJson(customers, writer);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to write customer data", ex);
    }
  }

  /** {@inheritDoc} */
  @Override
  public List<Customer<Long>> findAll() {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    List<Customer<Long>> customers;
    try (Reader reader = createReader(filePath)) {
      customers = gson.fromJson(reader, customerListType);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to read customer data", ex);
    }
    return customers;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Customer<Long>> findById(Long id) {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    List<Customer<Long>> customers = null;
    try (Reader reader = createReader(filePath)) {
      customers = gson.fromJson(reader, customerListType);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to read customer data", ex);
    }
    return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
  }

  /** {@inheritDoc} */
  @Override
  public void deleteSchema() {
    if (!filePath.toFile().exists()) {
      throw new RuntimeException("File not found");
    }
    filePath.toFile().delete();
  }
}
