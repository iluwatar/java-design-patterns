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
package com.iluwatar.order.microservice

// ABOUTME: Service that handles order processing logic and coordinates with other microservices.
// ABOUTME: Validates products and processes payments by calling external microservices.

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException

private val logger = KotlinLogging.logger {}

/** Service to handle order processing logic. */
@Service
class OrderService(private val restTemplateBuilder: RestTemplateBuilder) {
    /**
     * Processes an order by calling [validateProduct] and [processPayment].
     *
     * @return A string indicating whether the order was processed successfully or failed.
     */
    fun processOrder(): String =
        if (validateProduct() == true && processPayment() == true) {
            "Order processed successfully"
        } else {
            "Order processing failed"
        }

    /**
     * Validates the product by calling the respective microservice.
     *
     * @return true if the product is valid, false otherwise.
     */
    internal fun validateProduct(): Boolean? =
        try {
            val productValidationResult =
                restTemplateBuilder
                    .build()
                    .postForEntity(
                        "http://localhost:30302/product/validate",
                        "validating product",
                        Boolean::class.java,
                    )
            logger.info { "Product validation result: ${productValidationResult.body}" }
            productValidationResult.body
        } catch (e: ResourceAccessException) {
            logger.error { "Error communicating with product service: ${e.message}" }
            false
        } catch (e: HttpClientErrorException) {
            logger.error { "Error communicating with product service: ${e.message}" }
            false
        }

    /**
     * Processes the payment by calling the respective microservice.
     *
     * @return true if the payment is processed, false otherwise.
     */
    internal fun processPayment(): Boolean? =
        try {
            val paymentProcessResult =
                restTemplateBuilder
                    .build()
                    .postForEntity(
                        "http://localhost:30301/payment/process",
                        "processing payment",
                        Boolean::class.java,
                    )
            logger.info { "Payment processing result: ${paymentProcessResult.body}" }
            paymentProcessResult.body
        } catch (e: ResourceAccessException) {
            logger.error { "Error communicating with payment service: ${e.message}" }
            false
        } catch (e: HttpClientErrorException) {
            logger.error { "Error communicating with payment service: ${e.message}" }
            false
        }
}
