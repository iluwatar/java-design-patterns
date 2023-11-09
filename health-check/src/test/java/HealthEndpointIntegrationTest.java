import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.iluwatar.health.check.App;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

/**
 * Integration tests for the health endpoint.
 *
 * @author ydoksanbir
 */
@Slf4j
@SpringBootTest(
    classes = {App.class},
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class HealthEndpointIntegrationTest {

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
  public void healthEndpointReturnsUpStatus() {
    Response response = given(requestSpec).get(getEndpointBasePath()).andReturn();
    logResponseDetails(response);
    response.then().assertThat().statusCode(HttpStatus.OK.value()).body("status", equalTo("UP"));
  }

  /** Test that the health endpoint returns complete details about the application's health. */
  @Test
  public void healthEndpointReturnsCompleteDetails() {
    Response response = given(requestSpec).get(getEndpointBasePath()).andReturn();
    logResponseDetails(response);
    response
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo("UP"))
        .body("components.cpu.status", equalTo("UP"))
        .body("components.db.status", equalTo("UP"))
        .body("components.diskSpace.status", equalTo("UP"))
        .body("components.ping.status", equalTo("UP"))
        .body("components.custom.status", equalTo("UP"));
  }

  /**
   * Test that the liveness endpoint returns the UP status.
   *
   * <p>The liveness endpoint is used to indicate whether the application is still running and
   * responsive.
   */
  @Test
  public void livenessEndpointShouldReturnUpStatus() {
    Response response = given(requestSpec).get(getEndpointBasePath() + "/liveness").andReturn();
    logResponseDetails(response);
    response.then().assertThat().statusCode(HttpStatus.OK.value()).body("status", equalTo("UP"));
  }

  /**
   * Test that the custom health indicator returns the UP status and additional details.
   *
   * <p>The custom health indicator is used to provide more specific information about the health of
   * a particular component or aspect of the application.
   */
  @Test
  public void customHealthIndicatorShouldReturnUpStatusAndDetails() {
    Response response = given(requestSpec).get(getEndpointBasePath()).andReturn();
    logResponseDetails(response);
    response
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("components.custom.status", equalTo("UP"))
        .body("components.custom.details.database", equalTo("reachable"));
  }

  /**
   * Test that the health endpoint returns the UP status (CIT test).
   *
   * <p>This test is specifically designed for Continuous Integration (CI) testing. It logs the
   * health endpoint status and response, and throws an assertion error if the status code is not OK
   * (200) or the status body is not "UP".
   */
  @Test
  public void healthEndpointReturnsUpStatusCITest() {
    LOGGER.info("Testing health endpoint for UP status");
    Response response = given(requestSpec).get(getEndpointBasePath()).andReturn();
    logResponseDetails(response);

    if (response.getStatusCode() != HttpStatus.OK.value()) {
      // Log the entire response to see which part of the health check failed.
      LOGGER.error("Health endpoint response: " + response.getBody().asString());
      LOGGER.error("Health endpoint status: " + response.getStatusCode());
    }

    response.then().assertThat().statusCode(HttpStatus.OK.value()).body("status", equalTo("UP"));
  }
}
