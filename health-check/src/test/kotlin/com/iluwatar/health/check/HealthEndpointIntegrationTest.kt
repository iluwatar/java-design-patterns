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
// ABOUTME: Integration tests for the health endpoint using REST-assured.
// ABOUTME: Verifies health, liveness, and custom indicator endpoints in a running Spring context.
package com.iluwatar.health.check

import io.github.oshai.kotlinlogging.KotlinLogging
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

private val logger = KotlinLogging.logger {}

/**
 * Integration tests for the health endpoint.
 *
 * Log statement for the test case response in case of "DOWN" status with high CPU load
 * during pipeline execution. Note: During pipeline execution, if the health check shows "DOWN"
 * status with high CPU load, it is expected behavior. The service checks CPU usage, and if it's not
 * under 90%, it returns this error.
 */
@SpringBootTest(
    classes = [App::class],
    webEnvironment = WebEnvironment.RANDOM_PORT
)
class HealthEndpointIntegrationTest {

    /** Autowired TestRestTemplate instance for making HTTP requests. */
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    // Create a RequestSpecification that logs the request details
    private val requestSpec: RequestSpecification =
        RequestSpecBuilder().log(LogDetail.ALL).build()

    private fun getEndpointBasePath(): String {
        return restTemplate.rootUri + "/actuator/health"
    }

    // Common method to log response details
    private fun logResponseDetails(response: Response) {
        logger.info { "Request URI: ${response.detailedCookies}" }
        logger.info { "Response Time: ${response.time}ms" }
        logger.info { "Response Status: ${response.statusCode}" }
        logger.info { "Response: ${response.body.asString()}" }
    }

    /** Test that the health endpoint returns the UP status. */
    @Test
    fun healthEndpointReturnsUpStatus() {
        val response = given(requestSpec).get(getEndpointBasePath()).andReturn()
        logResponseDetails(response)

        if (response.statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            logger.warn {
                "Health endpoint returned 503 Service Unavailable. This may be due to pipeline " +
                    "configuration. Please check the pipeline logs."
            }
            response.then().assertThat().statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
            return
        }

        if (response.statusCode != HttpStatus.OK.value() || "UP" != response.path("status")) {
            logger.error { "Health endpoint response: ${response.body.asString()}" }
            logger.error { "Health endpoint status: ${response.statusCode}" }
        }

        response.then().assertThat().statusCode(HttpStatus.OK.value()).body("status", equalTo("UP"))
    }

    /**
     * Test that the health endpoint returns complete details about the application's health. If the
     * status is 503, the test passes without further checks. If the status is 200, additional checks
     * are performed on various components. In case of a "DOWN" status, the test logs the entire
     * response for visibility.
     */
    @Test
    fun healthEndpointReturnsCompleteDetails() {
        // Make the HTTP request to the health endpoint
        val response = given(requestSpec).get(getEndpointBasePath()).andReturn()

        // Log the response details
        logResponseDetails(response)

        // Check if the status is 503 (SERVICE_UNAVAILABLE)
        if (response.statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            logger.warn {
                "Health endpoint returned 503 Service Unavailable. This may be due to CI pipeline " +
                    "configuration. Please check the CI pipeline logs."
            }
            response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .log()
                .all() // Log the entire response for visibility
            return
        }

        // If status is 200, proceed with additional checks
        response
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value()) // Check that the status is UP
            .body("status", equalTo("UP")) // Verify the status body is UP
            .body("components.cpu.status", equalTo("UP")) // Check CPU status
            .body("components.db.status", equalTo("UP")) // Check DB status
            .body("components.diskSpace.status", equalTo("UP")) // Check disk space status
            .body("components.ping.status", equalTo("UP")) // Check ping status
            .body("components.custom.status", equalTo("UP")) // Check custom component status

        // Check for "DOWN" status and high CPU load
        if ("DOWN" == response.path("status")) {
            logger.error { "Health endpoint response: ${response.body.asString()}" }
            logger.error { "Health endpoint status: ${response.path<String>("status")}" }
            logger.error {
                "High CPU load detected: ${response.path<String>("components.cpu.details.processCpuLoad")}"
            }
        }
    }

    /**
     * Test that the liveness endpoint returns the UP status.
     *
     * The liveness endpoint is used to indicate whether the application is still running and
     * responsive.
     */
    @Test
    fun livenessEndpointShouldReturnUpStatus() {
        // Make the HTTP request to the liveness endpoint
        val response = given(requestSpec).get(getEndpointBasePath() + "/liveness").andReturn()

        // Log the response details
        logResponseDetails(response)

        // Check if the status is 503 (SERVICE_UNAVAILABLE)
        if (response.statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            logger.warn {
                "Liveness endpoint returned 503 Service Unavailable. This may be due to CI pipeline " +
                    "configuration. Please check the CI pipeline logs."
            }
            // If status is 503, the test passes without further checks
            response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .log()
                .all() // Log the entire response for visibility
            return
        }

        // If status is 200, proceed with additional checks
        response.then().assertThat().statusCode(HttpStatus.OK.value()).body("status", equalTo("UP"))

        // Check for "DOWN" status and high CPU load
        if ("DOWN" == response.path("status")) {
            logger.error { "Liveness endpoint response: ${response.body.asString()}" }
            logger.error { "Liveness endpoint status: ${response.path<String>("status")}" }
            logger.error {
                "High CPU load detected: ${response.path<String>("components.cpu.details.processCpuLoad")}"
            }
        }
    }

    /**
     * Test that the custom health indicator returns the UP status and additional details.
     *
     * The custom health indicator is used to provide more specific information about the health of
     * a particular component or aspect of the application.
     */
    @Test
    fun customHealthIndicatorShouldReturnUpStatusAndDetails() {
        // Make the HTTP request to the health endpoint
        val response = given(requestSpec).get(getEndpointBasePath()).andReturn()

        // Log the response details
        logResponseDetails(response)

        // Check if the status is 503 (SERVICE_UNAVAILABLE)
        if (response.statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            logger.warn {
                "Custom health indicator returned 503 Service Unavailable. This may be due to CI pipeline " +
                    "configuration. Please check the CI pipeline logs."
            }
            // If status is 503, the test passes without further checks
            response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .log()
                .all() // Log the entire response for visibility
            return
        }

        // If status is 200, proceed with additional checks
        response
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value()) // Check that the status is UP
            .body("components.custom.status", equalTo("UP")) // Verify the custom component status
            .body("components.custom.details.database", equalTo("reachable")) // Verify custom details

        // Check for "DOWN" status and high CPU load
        if ("DOWN" == response.path("status")) {
            logger.error { "Custom health indicator response: ${response.body.asString()}" }
            logger.error { "Custom health indicator status: ${response.path<String>("status")}" }
            logger.error {
                "High CPU load detected: ${response.path<String>("components.cpu.details.processCpuLoad")}"
            }
        }
    }
}
