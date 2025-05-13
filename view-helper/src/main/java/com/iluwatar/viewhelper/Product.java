package com.iluwatar.viewhelper;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Definition of product.
 */

public record Product(String name, BigDecimal price, LocalDate releaseDate, boolean discounted) {
}
