package org.openindustries.eretail.storeinv;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class StoreinvResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"sku\":\"SKU-200\",\"availableUnits\":150}")
                .when().post("/api/v1/store-inventory")
                .then().statusCode(201).body("availableUnits", is(150))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"channel\":\"POS\",\"units\":20}")
                .when().post("/api/v1/store-inventory/" + id + "/reservations")
                .then().statusCode(200)
                .body("remainingAfter", is(130));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"sku\":\"SKU-200\",\"availableUnits\":150}")
                .when().post("/api/v1/store-inventory")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"channel\":\"POS\",\"units\":400}")
                .when().post("/api/v1/store-inventory/" + id + "/reservations")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/store-inventory/" + id)
                .then().statusCode(200).body("availableUnits", is(150));
    }
}
