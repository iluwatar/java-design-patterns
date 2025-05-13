package com.iluwatar.viewhelper;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.Locale.US;

import java.text.NumberFormat;

/**
 * Formats a {@link Product} into a {@link ProductViewModel}.
 */
public class ProductViewHelper implements ViewHelper<Product, ProductViewModel> {

  private static final String DISCOUNT_TAG = " ON SALE";

  @Override
  public ProductViewModel prepare(Product product) {
    var displayName = product.name() + (product.discounted() ? DISCOUNT_TAG : "");
    var priceWithCurrency = NumberFormat.getCurrencyInstance(US).format(product.price());
    var formattedDate = product.releaseDate().format(ISO_DATE);

    return new ProductViewModel(displayName, priceWithCurrency, formattedDate);
  }
}
