package ru.yandex.practicum.scooter.api;

import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.Courier;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseApiClient {

    public Response createCourier(Courier courier) {

        return given()
                .spec(getRequestSpecification())
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    public Response login(String login, String password) {
        Courier courier = new Courier(login, password);
        return given()
                .spec(getRequestSpecification())
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    public Response deleteCourier(int courierId) {
        return given()
                .spec(getRequestSpecification())
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
    public Response deleteCourierNull() {
        return given()
                .spec(getRequestSpecification())
                .when()
                .delete("/api/v1/courier/");
    }

    public int getCourierId(String login, String password) {
        Courier courier = new Courier(login, password);
        return given()
                .spec(getRequestSpecification())
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .jsonPath()
                .getInt("id");
    }
}
