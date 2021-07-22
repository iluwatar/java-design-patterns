/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.domainmodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;

/**
 * This class organizes domain logic of customer.
 * A single instance of this class
 * contains both the data and behavior of a single customer.
 */
@Slf4j
@Getter
@Setter
@Builder
public class Customer {

  @NonNull private final CustomerDao customerDao;
  @Builder.Default private List<Product> purchases = new ArrayList<>();
  @NonNull private String name;
  @NonNull private Money money;

  /**
   * Save customer or update if customer already exist.
   */
  public void save() {
    try {
      Optional<Customer> customer = customerDao.findByName(name);
      if (customer.isPresent()) {
        customerDao.update(this);
      } else {
        customerDao.save(this);
      }
    } catch (SQLException ex) {
      LOGGER.error(ex.getMessage());
    }
  }

  /**
   * Add product to purchases, save to db and withdraw money.
   *
   * @param product to buy.
   */
  public void buyProduct(Product product) {
    LOGGER.info(
        String.format(
            "%s want to buy %s($%.2f)...",
            name, product.getName(), product.getSalePrice().getAmount()));
    try {
      withdraw(product.getSalePrice());
    } catch (IllegalArgumentException ex) {
      LOGGER.error(ex.getMessage());
      return;
    }
    try {
      customerDao.addProduct(product, this);
      purchases.add(product);
      LOGGER.info(String.format("%s bought %s!", name, product.getName()));
    } catch (SQLException exception) {
      receiveMoney(product.getSalePrice());
      LOGGER.error(exception.getMessage());
    }
  }

  /**
   * Remove product from purchases, delete from db and return money.
   *
   * @param product to return.
   */
  public void returnProduct(Product product) {
    LOGGER.info(
        String.format(
            "%s want to return %s($%.2f)...",
            name, product.getName(), product.getSalePrice().getAmount()));
    if (purchases.contains(product)) {
      try {
        customerDao.deleteProduct(product, this);
        purchases.remove(product);
        receiveMoney(product.getSalePrice());
        LOGGER.info(String.format("%s returned %s!", name, product.getName()));
      } catch (SQLException ex) {
        LOGGER.error(ex.getMessage());
      }
    } else {
      LOGGER.error(String.format("%s didn't buy %s...", name, product.getName()));
    }
  }

  /**
   * Print customer's purchases.
   */
  public void showPurchases() {
    Optional<String> purchasesToShow =
        purchases.stream()
            .map(p -> p.getName() + " - $" + p.getSalePrice().getAmount())
            .reduce((p1, p2) -> p1 + ", " + p2);

    if (purchasesToShow.isPresent()) {
      LOGGER.info(name + " bought: " + purchasesToShow.get());
    } else {
      LOGGER.info(name + " didn't bought anything");
    }
  }

  /**
   * Print customer's money balance.
   */
  public void showBalance() {
    LOGGER.info(name + " balance: " + money);
  }

  private void withdraw(Money amount) throws IllegalArgumentException {
    if (money.compareTo(amount) < 0) {
      throw new IllegalArgumentException("Not enough money!");
    }
    money = money.minus(amount);
  }

  private void receiveMoney(Money amount) {
    money = money.plus(amount);
  }
}
