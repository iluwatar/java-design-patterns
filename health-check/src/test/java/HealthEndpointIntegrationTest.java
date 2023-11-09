import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import com.iluwatar.health.check.App;
import io.restassured.response.Response;
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

  private String getEndpointBasePath() {
    return restTemplate.getRootUri() + "/actuator/health";
  }

  /** Test that the health endpoint returns the UP status. */
  @Test
  public void healthEndpointReturnsUpStatus() {
    get(getEndpointBasePath())
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo("UP"));
  }

  /** Test that the health endpoint returns complete details about the application's health. */
  @Test
  public void healthEndpointReturnsCompleteDetails() {
    // Send a GET request to the health endpoint and assert that the status code is OK (200)
    // and the status body is "UP", and additionally assert that the status of each individual
    // component (cpu, db, diskSpace, ping, custom) is also "UP".
    get(getEndpointBasePath())
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
    get(getEndpointBasePath() + "/liveness")
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo("UP"));
  }

  /**
   * Test that the health endpoint can sustain multiple requests.
   *
   * <p>This test is repeated 5 times to ensure that the health endpoint can handle multiple
   * concurrent requests without any issues.
   */
  @RepeatedTest(5)
  public void healthEndpointShouldSustainMultipleRequests() {
    LOGGER.info("Testing health endpoint for UP status");
    get(getEndpointBasePath())
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo("UP"));
  }

  /**
   * Test that the custom health indicator returns the UP status and additional details.
   *
   * <p>The custom health indicator is used to provide more specific information about the health of
   * a particular component or aspect of the application.
   */
  @Test
  public void customHealthIndicatorShouldReturnUpStatusAndDetails() {
    get(getEndpointBasePath())
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

    Response response = get(getEndpointBasePath()).andReturn();
    LOGGER.info("Health endpoint status: " + response.statusCode());
    LOGGER.info("Health endpoint response: " + response.getBody().asString());

    if (response.getStatusCode() != HttpStatus.OK.value()) {
      // Log the entire response to see which part of the health check failed.
      LOGGER.error("Health endpoint response: " + response.getBody().asString());
      LOGGER.error("Health endpoint status: " + response.statusCode());
    }

    response.then().assertThat().statusCode(HttpStatus.OK.value()).body("status", equalTo("UP"));
  }
}
