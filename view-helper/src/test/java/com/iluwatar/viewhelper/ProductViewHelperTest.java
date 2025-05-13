package com.iluwatar.viewhelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductViewHelperTest {

  private ProductViewHelper helper;

  @BeforeEach
  void setUp() {
    helper = new ProductViewHelper();
  }

  @Test
  void shouldFormatProductWithoutDiscount() {
    var product = new Product("X", new BigDecimal("10.00"), LocalDate.of(2025, 1, 1), false);
    ProductViewModel viewModel = helper.prepare(product);

    assertEquals("X", viewModel.name());
    assertEquals("$10.00", viewModel.price());
    assertEquals("2025-01-01", viewModel.releasedDate());
  }

  @Test
  void shouldFormatProductWithDiscount() {
    var product = new Product("X", new BigDecimal("10.00"), LocalDate.of(2025, 1, 1), true);
    ProductViewModel viewModel = helper.prepare(product);

    assertEquals("X ON SALE", viewModel.name());       // locale follows JVM default
  }
}