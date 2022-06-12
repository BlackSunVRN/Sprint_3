package ru.yandex.practicum;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.Courier;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

public class CourierCreatedErrorTest {
    int courierId;
    Courier courier;

    CourierClient courierClient;


    @Before
    public void init() {
        courierClient = new CourierClient();
        courier = getRandomCourier();
    }

    @After
    public void clear() {
        courierId = courierClient.getCourierId(courier.getLogin(), courier.getPassword());
        courierClient.deleteCourier(courierId);
    }

    @Test
    public void courierLoginWithoutPassword() {
        Response responseCreate = courierClient.createCourier(courier);
        assertEquals(SC_CREATED, responseCreate.statusCode());

        Response responseLogin = courierClient.login(courier.getLogin(), "");
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        String errorText = responseLogin.body().jsonPath().getString("message");
        assertEquals("Недостаточно данных для входа", errorText);
    }

    @Test
    public void courierLoginWithIncorrectPassword(){
        Response responseCreate = courierClient.createCourier(courier);
        assertEquals(SC_CREATED, responseCreate.statusCode());

        Response responseLogin = courierClient.login(courier.getLogin(), RandomStringUtils.randomAlphabetic(10));
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        String errorText = responseLogin.body().jsonPath().getString("message");
        assertEquals("Учетная запись не найдена", errorText);
    }
}
