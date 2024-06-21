package com.iluwatar.verticalslicearchitecture.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

class ProductViewTest {

  @Mock
  ProductService productService;

  ProductView productView;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    productView = new ProductView(productService);
  }

  @Test
  void testRender() {

    List<Product> productList = new ArrayList<>();
    productList.add(Product.builder().id(1).price(100.0).name("Sample Product 1").build());
    productList.add(Product.builder().id(2).price(50.0).name("Sample Product 2").build());

    Mockito.when(productService.getAllProducts()).thenReturn(productList);

    productView.render();

    Mockito.verify(productService, times(1)).getAllProducts();
  }
}
