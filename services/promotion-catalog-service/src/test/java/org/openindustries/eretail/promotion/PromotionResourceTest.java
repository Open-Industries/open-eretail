package org.openindustries.eretail.promotion;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class PromotionResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Summer Bundle\",\"promoType\":\"BUNDLE\",\"discountPct\":15}")
                .when().post("/api/v1/promotions")
                .then().statusCode(201)
                .body("status", is("DRAFT"))
                .body("promoType", is("BUNDLE"))
                .extract().path("id");

        given().when().post("/api/v1/promotions/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));
    }
}
