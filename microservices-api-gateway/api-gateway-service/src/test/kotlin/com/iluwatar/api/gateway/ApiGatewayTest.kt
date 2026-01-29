/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
// ABOUTME: Unit tests for the ApiGateway REST controller.
// ABOUTME: Tests desktop and mobile product endpoints using MockK for mocking.
package com.iluwatar.api.gateway

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Test API Gateway Pattern
 */
@ExtendWith(MockKExtension::class)
class ApiGatewayTest {

    @InjectMockKs
    private lateinit var apiGateway: ApiGateway

    @MockK
    private lateinit var imageClient: ImageClient

    @MockK
    private lateinit var priceClient: PriceClient

    /**
     * Tests getting the data for a desktop client
     */
    @Test
    fun testGetProductDesktop() {
        val imagePath = "/product-image.png"
        val price = "20"
        every { imageClient.getImagePath() } returns imagePath
        every { priceClient.getPrice() } returns price

        val desktopProduct = apiGateway.getProductDesktop()

        assertEquals(price, desktopProduct.price)
        assertEquals(imagePath, desktopProduct.imagePath)
    }

    /**
     * Tests getting the data for a mobile client
     */
    @Test
    fun testGetProductMobile() {
        val price = "20"
        every { priceClient.getPrice() } returns price

        val mobileProduct = apiGateway.getProductMobile()

        assertEquals(price, mobileProduct.price)
    }
}
