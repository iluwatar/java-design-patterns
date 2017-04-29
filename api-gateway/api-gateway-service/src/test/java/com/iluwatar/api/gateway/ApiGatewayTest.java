/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.api.gateway;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test API Gateway Pattern
 */
public class ApiGatewayTest {

  @InjectMocks
  private ApiGateway apiGateway;

  @Mock
  private ImageClient imageClient;

  @Mock
  private PriceClient priceClient;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Tests getting the data for a desktop client
   */
  @Test
  public void testGetProductDesktop() {
    String imagePath = "/product-image.png";
    String price = "20";
    when(imageClient.getImagePath()).thenReturn(imagePath);
    when(priceClient.getPrice()).thenReturn(price);

    DesktopProduct desktopProduct = apiGateway.getProductDesktop();

    assertEquals(price, desktopProduct.getPrice());
    assertEquals(imagePath, desktopProduct.getImagePath());
  }

  /**
   * Tests getting the data for a mobile client
   */
  @Test
  public void testGetProductMobile() {
    String price = "20";
    when(priceClient.getPrice()).thenReturn(price);

    MobileProduct mobileProduct = apiGateway.getProductMobile();

    assertEquals(price, mobileProduct.getPrice());
  }
}
