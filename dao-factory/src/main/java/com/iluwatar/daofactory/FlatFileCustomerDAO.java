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
package com.iluwatar.daofactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
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
    try {
      Files.delete(filePath);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to delete customer data");
    }
  }
}
