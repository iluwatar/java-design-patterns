package com.iluwatar.verticalslicearchitecture.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductServiceTest {

  private ProductRepository productRepository;
  private ProductService productService;

  @BeforeEach
  void setUp() {
    productRepository = Mockito.mock(ProductRepository.class);
    productService = new ProductService(productRepository);
  }

  @Test
  void testCreateProduct() {
    Product product = Product.builder().id(1).name("Sample Product").price(100.0).build();

    productService.createProduct(product);

    Mockito.verify(productRepository, Mockito.times(1)).save(product);
  }

  @Test
  void testGetProductById() {
    int productId = 1;
    Product product = Product.builder().id(1).name("Sample Product").price(100.0).build();

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    Product retrievedProduct = productService.getProductById(productId);

    assertEquals(product, retrievedProduct);
  }

  @Test
  void testGetAllProducts() {
    List<Product> productList = new ArrayList<>();
    productList.add(Product.builder().id(1).name("Product 1").price(100.0).build());
    productList.add(Product.builder().id(2).name("Product 2").price(50.0).build());

    when(productRepository.findAll()).thenReturn(productList);

    List<Product> retrievedList = productService.getAllProducts();

    assertEquals(productList, retrievedList);
  }
}
