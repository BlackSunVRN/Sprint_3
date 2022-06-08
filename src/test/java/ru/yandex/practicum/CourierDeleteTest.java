package ru.yandex.practicum;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.DeleteCourierResponse;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

public class CourierDeleteTest {
    int courierId;
    Courier courier;

    CourierClient courierClient;
    CourierCredentials courierCredentials;

    @Before
    public void init() {
        courier = getRandomCourier();
        courierClient = new CourierClient();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @Test
    public void courierDelete() {
        Response responseCreate = courierClient.createCourier(courier);
        assertEquals(SC_CREATED, responseCreate.statusCode());

        courierId = courierClient.getCourierId(courierCredentials);

        Response responseDelete = courierClient.deleteCourier(courierId);
        DeleteCourierResponse deleteCourierResponse = responseDelete.as(DeleteCourierResponse.class);
        assertTrue(deleteCourierResponse.ok);
    }
}
