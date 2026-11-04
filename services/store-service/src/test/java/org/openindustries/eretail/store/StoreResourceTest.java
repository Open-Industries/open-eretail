package org.openindustries.eretail.store;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class StoreResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Store Centro\",\"format\":\"STANDARD\",\"regionCode\":\"MX-CDMX\"}")
                .when().post("/api/v1/stores")
                .then().statusCode(201)
                .body("status", is("ACTIVE"))
                .body("format", is("STANDARD"))
                .extract().path("id");

        given().when().get("/api/v1/stores/" + id)
                .then().statusCode(200).body("status", is("ACTIVE"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"name\":\"Store Centro\",\"format\":\"STANDARD\",\"regionCode\":\"MX-CDMX\"}")
                .when().post("/api/v1/stores")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/stores/" + id + "/suspend")
                .then().statusCode(200).body("status", is("SUSPENDED"));
    }
}
