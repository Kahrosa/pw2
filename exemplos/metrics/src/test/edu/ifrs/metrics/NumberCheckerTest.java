package edu.ifrs.metrics;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;

@QuarkusTest
public class NumberCheckerTest {

    @Test
    public void checkCounter() {
        get("/31");
        assertMetricValue("ifrs.metrics.service.NumberChecker.performedChecks", 1);
        get("/33");
        assertMetricValue("ifrs.metrics.service.NumberChecker.performedChecks", 2);
        get("/887");
        assertMetricValue("ifrs.metrics.service.NumberChecker.highestPar", 1);
        get("/2");
        assertMetricValue("ifrs.metrics.service.NumberChecker.highestPar", 2);
    }

    private void assertMetricValue(String metric, Object value) {
        given().header(new Header("Accept", "application/json"))
                .get("/q/metrics/application").then()
                .statusCode(200)
                .body("'" + metric + "'", is(value));
    }

}
