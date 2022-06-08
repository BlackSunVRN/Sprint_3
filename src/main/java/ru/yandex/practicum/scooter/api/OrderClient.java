package ru.yandex.practicum.scooter.api;

import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.CreatedOrder;
import ru.yandex.practicum.scooter.api.model.MakeOrder;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseApiClient{
    public Response createOrder(MakeOrder order) {
        return given()
                .spec(getRequestSpecification())
                .body(order)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }

    public List<CreatedOrder> getOrderList() {
        return given()
                .spec(getRequestSpecification())
                .get(BASE_URL + "/api/v1/orders")
                .then().log().all()
                .extract()
                .body().jsonPath().getList("orders",CreatedOrder.class);
    }
}
