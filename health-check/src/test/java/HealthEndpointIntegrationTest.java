import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import com.iluwatar.health.check.App;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

@SpringBootTest(
    classes = {App.class},
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class HealthEndpointIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;

  private String getEndpointBasePath() {
    return restTemplate.getRootUri() + "/actuator/health";
  }

  @Test
  public void healthEndpointReturnsUpStatus() {
    get(getEndpointBasePath())
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo("UP"));
  }

  @Test
  public void healthEndpointReturnsCompleteDetails() {
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

  @Test
  public void livenessEndpointShouldReturnUpStatus() {
    get(getEndpointBasePath() + "/liveness")
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo("UP"));
  }



  @RepeatedTest(20)
  public void healthEndpointShouldSustainMultipleRequests() {
    get(getEndpointBasePath())
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo("UP"));
  }

  @Test
  public void customHealthIndicatorShouldReturnUpStatusAndDetails() {
    get(getEndpointBasePath())
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("components.custom.status", equalTo("UP"))
        .body("components.custom.details.database", equalTo("reachable"));
  }
}
