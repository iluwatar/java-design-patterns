package com.iluwatar.viewhelper;

/**
 * Controller delegates a {@link Product} to {@link ProductViewHelper} and then to {@link ConsoleProductView}.
 */
public class ProductController {

  private final ViewHelper<Product, ProductViewModel> viewHelper;
  private final View<ProductViewModel> view;

  public ProductController(ViewHelper<Product, ProductViewModel> viewHelper,
                           View<ProductViewModel> view) {
    this.viewHelper = viewHelper;
    this.view = view;
  }

  /** Passes the product to the helper for formatting and then forwards formatted product to the view. */
  public void handle(Product product) {
    view.render(viewHelper.prepare(product));
  }
}
