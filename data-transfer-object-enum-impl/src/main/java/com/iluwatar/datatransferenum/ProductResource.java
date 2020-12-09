package com.iluwatar.datatransferenum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The resource class which serves product information. This class act as server in the demo. Which
 * has all product details.
 */
public class ProductResource {
  private final List<Product> products;

  /**
   * Initialise resource with existing products.
   *
   * @param products initialize resource with existing products. Act as database.
   */
  public ProductResource(final List<Product> products) {
    this.products = products;
  }

  /**
   * Get all products.
   *
   * @return : all products in list but in the scheme of private dto.
   */
  public List<ProductDto.Response.Private> getAllProductsForAdmin() {
    return products
        .stream()
        .map(p -> new ProductDto.Response.Private().setId(p.getId()).setName(p.getName())
            .setCost(p.getCost())
            .setPrice(p.getPrice()))
        .collect(Collectors.toList());
  }

  /**
   * Get all products.
   *
   * @return : all products in list but in the scheme of public dto.
   */
  public List<ProductDto.Response.Public> getAllProductsForCustomer() {
    return products
        .stream()
        .map(p -> new ProductDto.Response.Public().setId(p.getId()).setName(p.getName())
            .setPrice(p.getPrice()))
        .collect(Collectors.toList());
  }

  /**
   * Save new product.
   *
   * @param createProductDto save new product to list.
   */
  public void save(ProductDto.Request.Create createProductDto) {
    products.add(new Product()
        .setId((long) (products.size() + 1))
        .setName(createProductDto.getName())
        .setSupplier(createProductDto.getSupplier())
        .setPrice(createProductDto.getPrice())
        .setCost(createProductDto.getCost()));
  }

  /**
   * List of all products in an entity representation.
   *
   * @return : all the products entity that stored in the products list
   */
  public List<Product> getProducts() {
    return products;
  }
}
