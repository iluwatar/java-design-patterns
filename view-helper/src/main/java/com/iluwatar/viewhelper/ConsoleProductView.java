package com.iluwatar.viewhelper;

import lombok.extern.slf4j.Slf4j;

/**
 * Renders {@link ProductViewModel} to the console.
 */
@Slf4j
public class ConsoleProductView implements View<ProductViewModel> {
  @Override
  public void render(ProductViewModel productViewModel) {
    LOGGER.info(productViewModel.toString());
  }
}
