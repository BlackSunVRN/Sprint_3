package ru.yandex.practicum.scooter.api;

import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseApiClient {

    public Response createCourier(Courier courier) {

        return given()
                .spec(getRequestSpecification())
                .body(courier)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response login(CourierCredentials courierCredentials) {
        return given()
                .spec(getRequestSpecification())
                .body(courierCredentials)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    public Response deleteCourier(int courierId) {
        return given()
                .spec(getRequestSpecification())
                .when()
                .delete(BASE_URL + "/api/v1/courier/" + courierId);
    }
    public Response deleteCourierNull() {
        return given()
                .spec(getRequestSpecification())
                .when()
                .delete(BASE_URL + "/api/v1/courier" + null);
    }

    public int getCourierId(CourierCredentials courierCredentials) {
        return given()
                .spec(getRequestSpecification())
                .body(courierCredentials)
                .when()
                .post(BASE_URL + "/api/v1/courier/login")
                .jsonPath()
                .getInt("id");
    }
}
