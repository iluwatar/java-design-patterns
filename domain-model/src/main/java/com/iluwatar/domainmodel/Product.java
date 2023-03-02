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
package com.iluwatar.domainmodel;

import static org.joda.money.CurrencyUnit.USD;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;

/**
 * This class organizes domain logic of product.
 * A single instance of this class
 * contains both the data and behavior of a single product.
 */
@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Product {

  private static final int DAYS_UNTIL_EXPIRATION_WHEN_DISCOUNT_ACTIVE = 4;
  private static final double DISCOUNT_RATE = 0.2;

  @NonNull private final ProductDao productDao;
  @NonNull private String name;
  @NonNull private Money price;
  @NonNull private LocalDate expirationDate;

  /**
   * Save product or update if product already exist.
   */
  public void save() {
    try {
      Optional<Product> product = productDao.findByName(name);
      if (product.isPresent()) {
        productDao.update(this);
      } else {
        productDao.save(this);
      }
    } catch (SQLException ex) {
      LOGGER.error(ex.getMessage());
    }
  }

  /**
   * Calculate sale price of product with discount.
   */
  public Money getSalePrice() {
    return price.minus(calculateDiscount());
  }

  private Money calculateDiscount() {
    if (ChronoUnit.DAYS.between(LocalDate.now(), expirationDate)
            < DAYS_UNTIL_EXPIRATION_WHEN_DISCOUNT_ACTIVE) {

      return price.multipliedBy(DISCOUNT_RATE, RoundingMode.DOWN);
    }

    return Money.zero(USD);
  }
}
