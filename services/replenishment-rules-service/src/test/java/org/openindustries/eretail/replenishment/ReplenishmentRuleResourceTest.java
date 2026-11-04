package org.openindustries.eretail.replenishment;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ReplenishmentRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"grocery-standard\",\"category\":\"GROCERY\",\"safetyStockUnits\":300,\"maxShrinkagePct\":2,\"priority\":1}")
                .when().post("/api/v1/replenishment-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/replenishment-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"category\":\"GROCERY\",\"requiredMaxShrinkagePct\":3,\"requiredSafetyStockUnits\":200}")
                .when().post("/api/v1/replenishment-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"category\":\"PHARMACY\",\"requiredMaxShrinkagePct\":3,\"requiredSafetyStockUnits\":200}")
                .when().post("/api/v1/replenishment-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
