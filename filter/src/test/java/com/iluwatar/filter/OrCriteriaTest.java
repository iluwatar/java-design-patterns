package com.iluwatar.filter;

import com.iluwatar.filter.criteria.InStockCriteria;
import com.iluwatar.filter.criteria.CategoryCriteria;
import com.iluwatar.filter.criteria.OrCriteria;
import com.iluwatar.filter.product.Product;
import com.iluwatar.filter.product.ProductCategory;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class that tests the CriteriaOr.
 */
public class OrCriteriaTest {

  /**
   * Tests that the filter works correctly when some products meet the criteira.
   * Asserts that some of the products (the correct ones) meet the criteria.
   */
  @Test
  void testMeetCriteria() {
    // Criterias to apply
    InStockCriteria inStockCriteria = new InStockCriteria();
    CategoryCriteria categoryClothingCriteria = new CategoryCriteria(ProductCategory.CLOTHING);

    // Union of criterias
    OrCriteria<Product> criteria = new OrCriteria<Product>(inStockCriteria, categoryClothingCriteria);

    // Items to filter
    List<Product> items = new ArrayList<>(Arrays.asList(
        new Product("IPhone 14", 12000, ProductCategory.ELECTRONICS, 2),
        new Product("IPhone 13", 8000, ProductCategory.ELECTRONICS, 10),
        new Product("IPhone 5", 300, ProductCategory.ELECTRONICS, 0),
        new Product("Nike Dunk Low Panda", 1300, ProductCategory.CLOTHING, 2),
        new Product("Jordan 11 Retro Win Like 96", 1300, ProductCategory.CLOTHING, 0)));

    // Filter
    List<Product> filteredItems = criteria.meetCriteria(items);

    // Expected result
    List<Product> expectedItems = new ArrayList<>();
    expectedItems.add(items.get(0));
    expectedItems.add(items.get(1));
    expectedItems.add(items.get(3));
    expectedItems.add(items.get(4));

    Collections.sort(filteredItems);
    Collections.sort(expectedItems);

    assertEquals(filteredItems, expectedItems);
  }

  /**
   * Tests that the filter works correctly when no products meet the criteria.
   * Asserts that all products are filtered out.
   */
  @Test
  void testNoneMeetCriteria() {
    // Criterias to apply
    InStockCriteria inStockCriteria = new InStockCriteria();
    CategoryCriteria categoryClothingCriteria = new CategoryCriteria(ProductCategory.CLOTHING);

    // Union of criterias
    OrCriteria<Product> criteria = new OrCriteria<Product>(inStockCriteria, categoryClothingCriteria);

    // Items to filter
    List<Product> items = new ArrayList<>(Arrays.asList(
        new Product("IPhone 14", 12000, ProductCategory.ELECTRONICS, 0),
        new Product("IPhone 13", 8000, ProductCategory.ELECTRONICS, 0),
        new Product("IPhone 5", 300, ProductCategory.ELECTRONICS, 0)));

    // Filter
    List<Product> filteredItems = criteria.meetCriteria(items);

    // Expected result
    List<Product> expectedItems = new ArrayList<>();

    assertEquals(filteredItems, expectedItems);
  }

  /**
   * Tests that the filter works correctly when all products meet the criteira.
   * Asserts that no products are filtered out.
   */
  @Test
  void testAllMeetCriteria() {
    // Criterias to apply
    InStockCriteria inStockCriteria = new InStockCriteria();
    CategoryCriteria categoryClothingCriteria = new CategoryCriteria(ProductCategory.ELECTRONICS);

    // Union of criterias
    OrCriteria<Product> criteria = new OrCriteria<Product>(inStockCriteria, categoryClothingCriteria);

    // Items to filter
    List<Product> items = new ArrayList<>(Arrays.asList(
        new Product("IPhone 14", 12000, ProductCategory.ELECTRONICS, 2),
        new Product("IPhone 13", 8000, ProductCategory.ELECTRONICS, 10),
        new Product("IPhone 5", 300, ProductCategory.ELECTRONICS, 1),
        new Product("Nike Dunk Low Panda", 1300, ProductCategory.CLOTHING, 2),
        new Product("Jordan 11 Retro Win Like 96", 1300, ProductCategory.CLOTHING, 10)));

    // Filter
    List<Product> filteredItems = criteria.meetCriteria(items);

    // Expected result
    List<Product> expectedItems = new ArrayList<>();
    expectedItems.add(items.get(0));
    expectedItems.add(items.get(1));
    expectedItems.add(items.get(2));
    expectedItems.add(items.get(3));
    expectedItems.add(items.get(4));

    Collections.sort(filteredItems);
    Collections.sort(expectedItems);

    assertEquals(filteredItems, expectedItems);
  }

  /**
   * Tests that the filter works correctly when no items are entered to filter.
   * Asserts that the result of the filtering is empty.
   */
  @Test
  void testEmptyFiltering() {
    // Criterias to apply
    InStockCriteria inStockCriteria = new InStockCriteria();
    CategoryCriteria categoryClothingCriteria = new CategoryCriteria(ProductCategory.CLOTHING);

    // Union of criterias
    OrCriteria<Product> criteria = new OrCriteria<Product>(inStockCriteria, categoryClothingCriteria);

    // Items to filter
    List<Product> items = new ArrayList<>();

    // Filter
    List<Product> filteredItems = criteria.meetCriteria(items);

    // Expected result
    List<Product> expectedItems = new ArrayList<>();

    assertEquals(filteredItems, expectedItems);
  }
}