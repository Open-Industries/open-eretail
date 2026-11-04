package org.openindustries.eretail.functions.storesla;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"storeId\":\"s-1\",\"measuredQueueMinutes\":4,\"targetQueueMinutes\":8,\"measuredOnShelfAvailabilityPct\":98.5,\"targetOnShelfAvailabilityPct\":97.0}")
                .when().post("/storeSlaEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"storeId\":\"s-2\",\"measuredQueueMinutes\":21,\"targetQueueMinutes\":8,\"measuredOnShelfAvailabilityPct\":98.5,\"targetOnShelfAvailabilityPct\":97.0}")
                .when().post("/storeSlaEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
