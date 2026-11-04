package org.openindustries.eretail.functions.shrinkage;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"storeId\":\"s-1\",\"shrinkageIndex\":4,\"discrepancyIndex\":3,\"incidentIndex\":1}")
                .when().post("/shrinkageRiskScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"storeId\":\"s-2\",\"shrinkageIndex\":82,\"discrepancyIndex\":74,\"incidentIndex\":69}")
                .when().post("/shrinkageRiskScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
