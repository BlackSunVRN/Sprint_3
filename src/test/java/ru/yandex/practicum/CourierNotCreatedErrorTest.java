package ru.yandex.practicum;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierNotCreatedErrorTest {
    CourierClient courierClient;

    @Before
    public void init() {
        courierClient = new CourierClient();
    }
    @Test
    public void courierCreateWithoutPassword() {
        //Создание курьера без указания пароля и проверка ответа
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), "", "Test Name");
        Response responseCreate = courierClient.createCourier(courier);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        String errorText = responseCreate.body().jsonPath().getString("message");
        assertEquals("Недостаточно данных для создания учетной записи", errorText);
    }

    @Test
    public void courierLoginUnregistered() {
        CourierCredentials courierCredentials = new CourierCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        Response responseLogin = courierClient.login(courierCredentials);
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        String errorText = responseLogin.body().jsonPath().getString("message");
        assertEquals("Учетная запись не найдена", errorText);
    }
}
