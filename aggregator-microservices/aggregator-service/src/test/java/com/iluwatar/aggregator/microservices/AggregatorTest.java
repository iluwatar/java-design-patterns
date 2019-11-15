/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.aggregator.microservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test Aggregation of domain objects
 */
public class AggregatorTest {

  @InjectMocks
  private Aggregator aggregator;

  @Mock
  private ProductInformationClient informationClient;

  @Mock
  private ProductInventoryClient inventoryClient;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Tests getting the data for a desktop client
   */
  @Test
  public void testGetProduct() {
    var title = "The Product Title.";
    var inventories = 5;

    when(informationClient.getProductTitle()).thenReturn(title);
    when(inventoryClient.getProductInventories()).thenReturn(inventories);

    var testProduct = aggregator.getProduct();

    assertEquals(title, testProduct.getTitle());
    assertEquals(inventories, testProduct.getProductInventories());
  }

}
