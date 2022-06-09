package ru.yandex.practicum.scooter.api;

import static io.restassured.RestAssured.given;

public class MetroClient extends BaseApiClient{

    public String searchMetro(String metroStation) {
        return given()
                .spec(getRequestSpecification())
                .when()
                .get("/v1/stations/search?s=" + metroStation)
                .body().jsonPath().getString("number");
    }
}
