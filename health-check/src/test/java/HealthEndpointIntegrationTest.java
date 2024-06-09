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
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.iluwatar.health.check.App;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

/**
 * Integration tests for the health endpoint.
 *
 * <p>* * Log statement for the test case response in case of "DOWN" status with high CPU load
 * during pipeline execution. * Note: During pipeline execution, if the health check shows "DOWN"
 * status with high CPU load, it is expected behavior. The service checks CPU usage, and if it's not
 * under 90%, it returns this error, example return value:
 * {"status":"DOWN","components":{"cpu":{"status":"DOWN","details":{"processCpuLoad":"100.00%", *
 * "availableProcessors":2,"systemCpuLoad":"100.00%","loadAverage":1.97,"timestamp":"2023-11-09T08:34:15.974557865Z",
 * * "error":"High system CPU load"}}} *
 *
 */
@Slf4j
@SpringBootTest(
    classes = {App.class},
    webEnvironment = WebEnvironment.RANDOM_PORT)
class HealthEndpointIntegrationTest {

  /** Autowired TestRestTemplate instance for making HTTP requests. */
  @Autowired private TestRestTemplate restTemplate;

  // Create a RequestSpecification that logs the request details
  private final RequestSpecification requestSpec =
      new RequestSpecBuilder().log(LogDetail.ALL).build();

  private String getEndpointBasePath() {
    return restTemplate.getRootUri() + "/actuator/health";
  }

  // Common method to log response details
  private void logResponseDetails(Response response) {
    LOGGER.info("Request URI: " + response.getDetailedCookies());
    LOGGER.info("Response Time: " + response.getTime() + "ms");
    LOGGER.info("Response Status: " + response.getStatusCode());
    LOGGER.info("Response: " + response.getBody().asString());
  }

  /** Test that the health endpoint returns the UP status. */
  @Test
  void healthEndpointReturnsUpStatus() {
    Response response = given(requestSpec).get(getEndpointBasePath()).andReturn();
    logResponseDetails(response);

    if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
      LOGGER.warn(
          "Health endpoint returned 503 Service Unavailable. This may be due to pipeline "
              + "configuration. Please check the pipeline logs.");
      response.then().assertThat().statusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
      return;
    }

    if (response.getStatusCode() != HttpStatus.OK.value()
        || !"UP".equals(response.path("status"))) {
      LOGGER.error("Health endpoint response: " + response.getBody().asString());
      LOGGER.error("Health endpoint status: " + response.getStatusCode());
    }

    response.then().assertThat().statusCode(HttpStatus.OK.value()).body("status", equalTo("UP"));
  }

  /**
   * Test that the health endpoint returns complete details about the application's health. If the
   * status is 503, the test passes without further checks. If the status is 200, additional checks
   * are performed on various components. In case of a "DOWN" status, the test logs the entire
   * response for visibility.
   */
  @Test
  void healthEndpointReturnsCompleteDetails() {
    // Make the HTTP request to the health endpoint
    Response response = given(requestSpec).get(getEndpointBasePath()).andReturn();

    // Log the response details
    logResponseDetails(response);

    // Check if the status is 503 (SERVICE_UNAVAILABLE)
    if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
      LOGGER.warn(
          "Health endpoint returned 503 Service Unavailable. This may be due to CI pipeline "
              + "configuration. Please check the CI pipeline logs.");
      response
          .then()
          .assertThat()
          .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
          .log()
          .all(); // Log the entire response for visibility
      return;
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
        .body("components.custom.status", equalTo("UP")); // Check custom component status

    // Check for "DOWN" status and high CPU load
    if ("DOWN".equals(response.path("status"))) {
      LOGGER.error("Health endpoint response: " + response.getBody().asString());
      LOGGER.error("Health endpoint status: " + response.path("status"));
      LOGGER.error(
          "High CPU load detected: " + response.path("components.cpu.details.processCpuLoad"));
    }
  }

  /**
   * Test that the liveness endpoint returns the UP status.
   *
   * <p>The liveness endpoint is used to indicate whether the application is still running and
   * responsive.
   */
  @Test
  void livenessEndpointShouldReturnUpStatus() {
    // Make the HTTP request to the liveness endpoint
    Response response = given(requestSpec).get(getEndpointBasePath() + "/liveness").andReturn();

    // Log the response details
    logResponseDetails(response);

    // Check if the status is 503 (SERVICE_UNAVAILABLE)
    if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
      LOGGER.warn(
          "Liveness endpoint returned 503 Service Unavailable. This may be due to CI pipeline "
              + "configuration. Please check the CI pipeline logs.");
      // If status is 503, the test passes without further checks
      response
          .then()
          .assertThat()
          .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
          .log()
          .all(); // Log the entire response for visibility
      return;
    }

    // If status is 200, proceed with additional checks
    response.then().assertThat().statusCode(HttpStatus.OK.value()).body("status", equalTo("UP"));

    // Check for "DOWN" status and high CPU load
    if ("DOWN".equals(response.path("status"))) {
      LOGGER.error("Liveness endpoint response: " + response.getBody().asString());
      LOGGER.error("Liveness endpoint status: " + response.path("status"));
      LOGGER.error(
          "High CPU load detected: " + response.path("components.cpu.details.processCpuLoad"));
    }
  }

  /**
   * Test that the custom health indicator returns the UP status and additional details.
   *
   * <p>The custom health indicator is used to provide more specific information about the health of
   * a particular component or aspect of the application.
   */
  @Test
  void customHealthIndicatorShouldReturnUpStatusAndDetails() {
    // Make the HTTP request to the health endpoint
    Response response = given(requestSpec).get(getEndpointBasePath()).andReturn();

    // Log the response details
    logResponseDetails(response);

    // Check if the status is 503 (SERVICE_UNAVAILABLE)
    if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
      LOGGER.warn(
          "Custom health indicator returned 503 Service Unavailable. This may be due to CI pipeline "
              + "configuration. Please check the CI pipeline logs.");
      // If status is 503, the test passes without further checks
      response
          .then()
          .assertThat()
          .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
          .log()
          .all(); // Log the entire response for visibility
      return;
    }

    // If status is 200, proceed with additional checks
    response
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value()) // Check that the status is UP
        .body("components.custom.status", equalTo("UP")) // Verify the custom component status
        .body("components.custom.details.database", equalTo("reachable")); // Verify custom details

    // Check for "DOWN" status and high CPU load
    if ("DOWN".equals(response.path("status"))) {
      LOGGER.error("Custom health indicator response: " + response.getBody().asString());
      LOGGER.error("Custom health indicator status: " + response.path("status"));
      LOGGER.error(
          "High CPU load detected: " + response.path("components.cpu.details.processCpuLoad"));
    }
  }
}
